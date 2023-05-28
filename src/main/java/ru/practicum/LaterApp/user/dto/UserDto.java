package ru.practicum.LaterApp.user.dto;

import lombok.*;
import ru.practicum.LaterApp.user.model.UserState;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    @NotBlank
    private String firsName;
    private String lastName;
    @Email
    @NotBlank
    private String email;
    // format "yyyy.MM.dd hh:mm:ss"
    private String registrationDate;
    private UserState state;
}
