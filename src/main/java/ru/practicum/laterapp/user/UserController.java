package ru.practicum.laterapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.laterapp.user.dto.UserDto;
import ru.practicum.laterapp.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto saveNewUser(@RequestBody @Valid UserDto userDto) {
        return userService.saveUser(userDto);
    }
}
