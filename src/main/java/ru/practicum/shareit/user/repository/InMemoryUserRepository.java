package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();
    private Integer id = 1;

    @Override
    public User create(User user) {
        user.setId(id++);
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь {}", user);
        return user;
    }

    @Override
    public User update(User user, Integer userId) {
        User oldUser = users.get(userId);
        if (user.getEmail() != null) oldUser.setEmail(user.getEmail());
        if (user.getName() != null) oldUser.setEmail(user.getName());
        log.info("Обновлены данные пользователя с id = {}", userId);
        return oldUser;
    }

    @Override
    public void delete(Integer userId) {
        users.remove(userId);
        log.info("Удалён пользователь с id = {}", userId);
    }

    @Override
    public User getUserById(Integer userId) {
        return users.get(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
