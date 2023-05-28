package ru.practicum.LaterApp.user.dao;

import ru.practicum.LaterApp.user.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User save(User user);
}
