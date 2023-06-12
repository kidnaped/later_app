package ru.practicum.laterapp.user.dto;

import lombok.*;
import ru.practicum.laterapp.user.model.UserState;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String registrationDate;
    private UserState state;
}
