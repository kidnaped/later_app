package ru.practicum.LaterApp.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.LaterApp.user.model.User;
import ru.practicum.LaterApp.user.model.UserShort;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmailContainingIgnoreCase(String emailSearch);
    List<UserShort> findAllByEmailContainingIgnoreCase(String emailSearch);
}
