package ru.practicum.LaterApp.user.service;

import ru.practicum.LaterApp.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
}
