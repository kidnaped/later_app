package ru.practicum.laterapp.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.laterapp.user.model.User;
import ru.practicum.laterapp.user.model.UserShort;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmailContainingIgnoreCase(String emailSearch);
    List<UserShort> findAllByEmailContainingIgnoreCase(String emailSearch);
}
