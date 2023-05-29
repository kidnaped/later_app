package ru.practicum.LaterApp.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.LaterApp.user.dto.UserDto;
import ru.practicum.LaterApp.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss");

    public static User mapToUser(UserDto dto) {
        User user = new User();
        if (dto.getFirsName() != null) {
            user.setFirstName(dto.getFirsName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getRegistrationDate() != null) {
            user.setRegistrationDate(LocalDateTime.parse(dto.getRegistrationDate(), formatter));
        }
        if (dto.getState() != null) {
            user.setState(dto.getState());
        }
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firsName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .state(user.getState())
                .registrationDate(user.getRegistrationDate().format(formatter))
                .build();
    }

    public static List<UserDto> mapToUserDto(Iterable<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        users.forEach(user -> dtos.add(mapToUserDto(user)));
        return dtos;
    }
}
