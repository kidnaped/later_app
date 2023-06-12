package ru.practicum.laterapp.user.service;

import ru.practicum.laterapp.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto saveUser(UserDto dto);
}
