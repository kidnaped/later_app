package ru.practicum.laterapp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.laterapp.exception.ErrorHandler;
import ru.practicum.laterapp.user.dto.UserDto;
import ru.practicum.laterapp.user.model.UserState;
import ru.practicum.laterapp.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private UserDto dto;
    private UserDto dto2;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(ErrorHandler.class)
                .build();

        dto = new UserDto(
                1L,
                "johnny@gmail.com",
                "John",
                "Doe",
                "2023.06.11 17:55:00",
                UserState.ACTIVE
        );

        dto2 = new UserDto(
                2L,
                "2johnny@gmail.com",
                "2John",
                "2Doe",
                "2023.06.11 17:59:00",
                UserState.ACTIVE
        );
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserDto> dtos = new ArrayList<>();
        dtos.add(dto);
        dtos.add(dto2);

        when(userService.getAllUsers())
                .thenReturn(dtos);

        mvc.perform(get("/users")
                    .characterEncoding(StandardCharsets.UTF_8)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..id",
                        contains(dtos.get(0).getId().intValue(),
                                dtos.get(1).getId().intValue())))
                .andExpect(jsonPath("$..email",
                        contains(dtos.get(0).getEmail(),
                                dtos.get(1).getEmail())))
                .andExpect(jsonPath("$..firstName",
                        contains(dtos.get(0).getFirstName(),
                                dtos.get(1).getFirstName())));
    }

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

    @Test
    void saveNewUserWithException() throws Exception {
        when(userService.saveUser(any()))
                .thenThrow(IllegalArgumentException.class);

        mvc.perform(post("/users")
                .content(mapper.writeValueAsString(dto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(500));
    }
}