package ru.practicum.LaterApp.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.LaterApp.item.model.Item;
import ru.practicum.LaterApp.item.dao.ItemRepository;
import ru.practicum.LaterApp.user.model.User;
import ru.practicum.LaterApp.user.dao.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<Item> getItems(long userId) {
        if (isUserValid(userId)) {
            return itemRepository.findByUserId(userId);
        } else {
            throw new RuntimeException("User ID not found");
        }
    }

    @Override
    public Item addNewItem(Long userId, Item item) {
        if (isUserValid(userId)) {
            item.setUserId(userId);
            return itemRepository.save(item);
        } else {
            throw new RuntimeException("User Id not found");
        }
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        if (isUserValid(userId)) {
            itemRepository.deleteByUserIdAndItemId(userId, itemId);
        }
    }

    private boolean isUserValid(long userId) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (userId == user.getId()) {
                return true;
            }
        }
        return false;
    }
}
