package ru.practicum.LaterApp.user.dto;

import lombok.*;
import ru.practicum.LaterApp.user.model.UserState;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firsName;
    private String lastName;
    private String email;
    private String registrationDate;
    private UserState state;
}
