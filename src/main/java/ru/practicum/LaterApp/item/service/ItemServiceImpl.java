package ru.practicum.LaterApp.item.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.LaterApp.item.dto.ItemDto;
import ru.practicum.LaterApp.item.dao.ItemRepository;
import ru.practicum.LaterApp.item.mapper.ItemMapper;
import ru.practicum.LaterApp.item.model.Item;
import ru.practicum.LaterApp.item.model.QItem;
import ru.practicum.LaterApp.user.model.User;
import ru.practicum.LaterApp.user.dao.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> getItems(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Item> items = itemRepository.findByUserId(userId);
        return ItemMapper.toDto(items);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getItems(long userId, Set<String> tags) {
        BooleanExpression byUserId = QItem.item.userId.eq(userId);
        BooleanExpression byAnyTag = QItem.item.tags.any().in(tags);
        Iterable<Item> foundItems = itemRepository.findAll(byUserId.and(byAnyTag));
        return ItemMapper.toDto(foundItems);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getUserItems(String lastName) {
        List<Item> foundItems = itemRepository.findItemsByLastNamePrefix(lastName);
        return ItemMapper.toDto(foundItems);
    }

    @Transactional
    @Override
    public ItemDto addNewItem(Long userId, ItemDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Item item = ItemMapper.fromDto(dto, user);
        item.setUser(user);
        return ItemMapper.toDto(itemRepository.save(item));
    }

    @Transactional
    @Override
    public void deleteItem(long userId, long itemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        itemRepository.deleteByUserIdAndId(userId, itemId);
    }
}
