package ru.practicum.LaterApp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.LaterApp.user.dto.UserDto;
import ru.practicum.LaterApp.user.mapper.UserMapper;
import ru.practicum.LaterApp.user.model.User;
import ru.practicum.LaterApp.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User saveNewUser(@RequestBody @Valid UserDto userDto) {
        User user = UserMapper.fromDto(userDto);
        return userService.saveUser(user);
    }
}
