package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.error.ErrorHandler;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ErrorHandler errorHandler;

    @PostMapping
    public UserDto create(@RequestBody @Valid User user, BindingResult bindingResult) {
        errorHandler.throwValidationException(bindingResult);
        return UserDto.toUserDto(userService.create(user));
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody User user, @PathVariable Integer userId) {
        return UserDto.toUserDto(userService.update(user, userId));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Integer userId) {
        userService.delete(userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Integer userId) {
        return UserDto.toUserDto(userService.getUserById(userId));
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserDto::toUserDto)
                .collect(Collectors.toList());
    }
}
