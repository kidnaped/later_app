package ru.practicum.LaterApp.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.LaterApp.user.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return users.stream()
                .sorted(Comparator.comparingLong(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public User save(User user) {
        user.setId(getId());
        users.add(user);
        return user;
    }

    private long getId() {
        long lastId = users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
