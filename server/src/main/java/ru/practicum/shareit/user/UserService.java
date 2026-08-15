package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;  // ← ДОБАВИТЬ ЭТОТ ИМПОРТ!

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto getById(Long id);

    List<UserDto> getAll();

    UserDto update(Long id, UserDto userDto);

    void deleteById(Long id);
}