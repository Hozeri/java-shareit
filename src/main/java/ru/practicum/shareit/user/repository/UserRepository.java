package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User create(User user);

    User update(User user, Integer userId);

    void delete(Integer userId);

    User getUserById(Integer userId);

    List<User> getAllUsers();
}
