package ru.practicum.laterapp.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.laterapp.user.dto.UserDto;
import ru.practicum.laterapp.user.model.User;
import ru.practicum.laterapp.user.model.UserState;
import ru.practicum.laterapp.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {
    private final EntityManager em;
    private final UserService service;

    @Test
    void getAllUsers() {
        UserDto userDto = makeUserDto("somemore@email.com", "Petro", "Johnson");
        UserDto userDto1 = makeUserDto("somemore2@email.com", "Pe2", "Joh2");
        UserDto userDto2 = makeUserDto("2somemore2@email.com", "2P2", "2J2");
        userDto = service.saveUser(userDto);
        userDto1 = service.saveUser(userDto1);
        userDto2 = service.saveUser(userDto2);

        List<UserDto> dtos = service.getAllUsers();

        assertThat(dtos, hasItem(userDto));
        assertThat(dtos, hasItem(userDto1));
        assertThat(dtos, hasItem(userDto2));
    }

    @Test
    void saveUser() {
        UserDto userDto = makeUserDto("some@email.com", "Petro", "Johnson");
        service.saveUser(userDto);

        TypedQuery<User> query = em.createQuery("select u from  User as u where u.email = :email", User.class);
        User user = query.setParameter("email", userDto.getEmail()).getSingleResult();

        assertThat(user.getId(), notNullValue());
        assertThat(user.getFirstName(), equalTo(userDto.getFirstName()));
        assertThat(user.getLastName(), equalTo(userDto.getLastName()));
        assertThat(user.getEmail(), equalTo(userDto.getEmail()));
        assertThat(user.getState(), equalTo(userDto.getState()));
        assertThat(user.getRegistrationDate(), notNullValue());
    }

    private UserDto makeUserDto(String email, String firstName, String lastName) {
        return UserDto.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .state(UserState.ACTIVE)
                .build();
    }
}