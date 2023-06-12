package ru.practicum.laterapp.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.laterapp.item.dto.GetItemRequest;
import ru.practicum.laterapp.item.dto.ItemCreationDto;
import ru.practicum.laterapp.item.dto.ItemDto;
import ru.practicum.laterapp.item.dto.ModifyItemRequest;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {
    List<ItemDto> getItems(long userId);

    @Transactional(readOnly = true)
    List<ItemDto> getItems(GetItemRequest request);

    @Transactional(readOnly = true)
    List<ItemDto> getUserItems(String lastName);

    @Transactional
    ItemDto addNewItem(Long userId, ItemCreationDto dto);

    @Transactional
    void deleteItem(long userId, long itemId);

    ItemDto changeItem(long userId, ModifyItemRequest request);
}
