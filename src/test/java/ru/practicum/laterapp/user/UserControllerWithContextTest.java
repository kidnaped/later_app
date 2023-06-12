package ru.practicum.laterapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.laterapp.user.dto.UserDto;
import ru.practicum.laterapp.user.model.UserState;
import ru.practicum.laterapp.user.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerWithContextTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mvc;

    private final UserDto dto = new UserDto(
            1L,
            "johnny@gmail.com",
            "John",
            "Doe",
            "2023.06.11 17:55:00",
            UserState.ACTIVE);

    @Test
    void saveNewUser() throws Exception {
        when(userService.saveUser(any()))
                .thenReturn(dto);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())));
    }
}
