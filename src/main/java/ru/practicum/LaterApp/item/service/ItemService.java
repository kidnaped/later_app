package ru.practicum.LaterApp.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.LaterApp.item.dto.ItemDto;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
public interface ItemService {
    List<ItemDto> getItems(long userId);

    @Transactional(readOnly = true)
    List<ItemDto> getItems(long userId, Set<String> tags);

    @Transactional(readOnly = true)
    List<ItemDto> getUserItems(String lastName);

    @Transactional
    ItemDto addNewItem(Long userId, ItemDto dto);

    void deleteItem(long userId, long itemId);
}
