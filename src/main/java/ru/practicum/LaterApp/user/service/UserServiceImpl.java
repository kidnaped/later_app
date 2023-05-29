package ru.practicum.LaterApp.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.LaterApp.user.dao.UserRepository;
import ru.practicum.LaterApp.user.dto.UserDto;
import ru.practicum.LaterApp.user.mapper.UserMapper;
import ru.practicum.LaterApp.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(UserDto dto) {
        User user = repository.save(UserMapper.mapToUser(dto));
        return UserMapper.mapToUserDto(user);
    }
}
