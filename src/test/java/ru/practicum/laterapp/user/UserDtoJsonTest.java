package ru.practicum.laterapp.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.laterapp.user.dto.UserDto;
import ru.practicum.laterapp.user.model.UserState;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class UserDtoJsonTest {
    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void testUserDto() throws Exception {
        UserDto userDto = new UserDto(
                1L,
                "John",
                "Doe",
                "john.doe@mail.com",
                "2022.07.03 19:55:00",
                UserState.ACTIVE);

        JsonContent<UserDto> result = json.write(userDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(userDto.getId().intValue());
        assertThat(result).extractingJsonPathStringValue("$.firstName").isEqualTo(userDto.getFirstName());
        assertThat(result).extractingJsonPathStringValue("$.lastName").isEqualTo(userDto.getLastName());
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo(userDto.getEmail());
    }
}
