package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exception.EmailDuplicateException;
import ru.practicum.shareit.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        if (isEmailDuplicated(user)) {
            throw new EmailDuplicateException("Пользователь с такой почтой уже существует");
        }
        userRepository.create(user);
        return user;
    }

    @Override
    public User update(User user, Integer userId) {
        getUserById(userId);
        if (isEmailDuplicated(user, userId)) {
            throw new EmailDuplicateException("Пользователь с такой почтой уже существует");
        }
        return userRepository.update(user, userId);
    }

    @Override
    public void delete(Integer userId) {
        getUserById(userId);
        userRepository.delete(userId);
    }

    @Override
    public User getUserById(Integer userId) {
        if (userRepository.getUserById(userId) == null) {
            throw new EntityNotFoundException("Пользователя с таким id не существует");
        }
        return userRepository.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    private boolean isEmailDuplicated(User user) {
        return getAllUsers().stream().anyMatch(it -> user.getEmail().equals(it.getEmail()));
    }

    private boolean isEmailDuplicated(User user, Integer id) {
        return getAllUsers().stream().anyMatch(it -> !it.getId().equals(id) && it.getEmail().equals(user.getEmail()));
    }
}
