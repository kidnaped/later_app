package ru.practicum.LaterApp.item.dao;

import ru.practicum.LaterApp.item.model.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findByUserId(long userId);
    Item save(Item item);
    void deleteByUserIdAndItemId(long userId, long itemId);
}
