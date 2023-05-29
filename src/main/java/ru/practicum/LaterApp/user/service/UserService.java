package ru.practicum.LaterApp.user.service;

import ru.practicum.LaterApp.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto saveUser(UserDto dto);
}
