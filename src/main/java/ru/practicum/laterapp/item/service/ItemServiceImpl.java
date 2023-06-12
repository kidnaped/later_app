package ru.practicum.laterapp.item.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.laterapp.exception.ItemRetrieverException;
import ru.practicum.laterapp.item.UrlMetaDataRetriever;
import ru.practicum.laterapp.item.dto.GetItemRequest;
import ru.practicum.laterapp.item.dto.ItemCreationDto;
import ru.practicum.laterapp.item.dto.ItemDto;
import ru.practicum.laterapp.item.dao.ItemRepository;
import ru.practicum.laterapp.item.dto.ModifyItemRequest;
import ru.practicum.laterapp.item.mapper.ItemMapper;
import ru.practicum.laterapp.item.model.Item;
import ru.practicum.laterapp.item.model.QItem;
import ru.practicum.laterapp.user.model.User;
import ru.practicum.laterapp.user.dao.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UrlMetaDataRetriever retriever;

    @Override
    public List<ItemDto> getItems(long userId) {
        User user = getUserOrThrow(userId);
        List<Item> items = itemRepository.findByUserId(user.getId());
        return ItemMapper.toDto(items);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getItems(GetItemRequest request) {
        QItem item = QItem.item;
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(item.userId.eq(request.getUserId()));

        GetItemRequest.State state = request.getState();
        if (!state.equals(GetItemRequest.State.ALL)) {
            conditions.add(makeStateCondition(state));
        }

        GetItemRequest.ContentType contentType = request.getContentType();
        if (!contentType.equals(GetItemRequest.ContentType.ALL)) {
            conditions.add(makeContentTypeCondition(contentType));
        }

        if (request.hasTags()) {
            conditions.add(item.tags.any().in(request.getTags()));
        }

        BooleanExpression finalCondition = conditions.stream().reduce(BooleanExpression::and).get();

        Sort sort = makeOrderByClause(request.getSort());
        PageRequest pageRequest = PageRequest.of(0, request.getLimit(), sort);

        Iterable<Item> items = itemRepository.findAll(finalCondition, pageRequest);
        return ItemMapper.toDto(items);
    }



    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getUserItems(String lastName) {
        List<Item> foundItems = itemRepository.findItemsByLastNamePrefix(lastName);
        return ItemMapper.toDto(foundItems);
    }

    @Transactional
    @Override
    public ItemDto addNewItem(Long userId, ItemCreationDto dto) {
        User user = getUserOrThrow(userId);
        Item item;
        UrlMetaDataRetriever.UrlMetadata result = retriever.retrieve(dto.getUrl());

        Optional<Item> probableItem = itemRepository.findByUserAndResolvedUrl(user, result.getResolvedUrl());
        if (probableItem.isEmpty()) {
            item = itemRepository.save(ItemMapper.toItem(result, user, dto.getTags()));
        } else {
            item = probableItem.get();
            if (dto.getTags() != null && !dto.getTags().isEmpty()) {
                item.getTags().addAll(dto.getTags());
                itemRepository.save(item);
            }
        }

        return ItemMapper.toDto(item);
    }

    @Transactional
    @Override
    public void deleteItem(long userId, long itemId) {
        User user = getUserOrThrow(userId);
        itemRepository.deleteByUserIdAndId(user.getId(), itemId);
    }

    @Override
    public ItemDto changeItem(long userId, ModifyItemRequest request) {
        Optional<Item> probableItem = getAndCheckPermissions(userId, request.getItemId());
        if (probableItem.isPresent()) {
            Item item = probableItem.get();
            item.setUnread(!request.isRead());

            if (request.isReplaceTags()) {
                item.getTags().clear();
            }
            if (request.hasTags()) {
                item.getTags().addAll(request.getTags());
            }

            item = itemRepository.save(item);
            return ItemMapper.toDto(item);
        } else {
            throw new ItemRetrieverException("The item with id " + request.getItemId() + " not found");
        }
    }

    private User getUserOrThrow(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    private BooleanExpression makeStateCondition(GetItemRequest.State state) {
        if (state.equals(GetItemRequest.State.READ)) {
            return QItem.item.unread.isFalse();
        } else {
            return QItem.item.unread.isTrue();
        }
    }

    private BooleanExpression makeContentTypeCondition(GetItemRequest.ContentType contentType) {
        if (contentType.equals(GetItemRequest.ContentType.ARTICLE)) {
            return QItem.item.mimeType.eq("text");
        } else if (contentType.equals(GetItemRequest.ContentType.IMAGE)) {
            return QItem.item.mimeType.eq("image");
        } else {
            return QItem.item.mimeType.eq("video");
        }
    }

    private Sort makeOrderByClause(GetItemRequest.Sort sort) {
        switch (sort) {
            case TITLE:
                return Sort.by("title").ascending();
            case SITE:
                return Sort.by("resolvedUrl").ascending();
            case OLDEST:
                return Sort.by("dateResolved").ascending();
            default:
                return Sort.by("dateResolved").descending();
        }
    }

    private Optional<Item> getAndCheckPermissions(long userId, long itemId) {
        Optional<Item> probableItem = itemRepository.findById(itemId);
        if (probableItem.isPresent()) {
            Item item = probableItem.get();
            if(!item.getUser().getId().equals(userId)) {
                throw new IllegalArgumentException("User do not has rights to perform this operation.");
            }
        }
        return probableItem;
    }
}
