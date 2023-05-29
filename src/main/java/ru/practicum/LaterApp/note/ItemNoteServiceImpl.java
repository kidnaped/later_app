package ru.practicum.LaterApp.note;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.LaterApp.item.dao.ItemRepository;
import ru.practicum.LaterApp.item.model.Item;
import ru.practicum.LaterApp.user.dao.UserRepository;
import ru.practicum.LaterApp.user.model.User;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemNoteServiceImpl implements ItemNoteService {
    private final ItemNoteRepository itemNoteRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ItemNoteDto addNewItemNote(long userId, ItemNoteDto itemNoteDto) {
        User user = getUserOrThrow(userId);
        Item item = itemRepository.findById(itemNoteDto.getId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        ItemNote note = ItemNoteMapper.fromDto(itemNoteDto, item);
        return ItemNoteMapper.toDto(itemNoteRepository.save(note));
    }

    @Override
    public List<ItemNoteDto> searchNotesByUrl(long userId, String url) {
        User user = getUserOrThrow(userId);
        List<ItemNote> itemNotes = itemNoteRepository.findAllByItemUrlContainingAndItemUserId(url, userId);
        return ItemNoteMapper.toDto(itemNotes);
    }

    @Override
    public List<ItemNoteDto> searchNotesByTag(long userId, String tag) {
        User user = getUserOrThrow(userId);
        List<ItemNote> itemNotes = itemNoteRepository.findByTag(userId, tag);
        return ItemNoteMapper.toDto(itemNotes);
    }

    @Override
    public List<ItemNoteDto> getAllItemsWithNotes(long userId, int from, int size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return itemNoteRepository.findAllByItemUserId(userId, page)
                .map(ItemNoteMapper::toDto)
                .getContent();
    }

    private User getUserOrThrow(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
