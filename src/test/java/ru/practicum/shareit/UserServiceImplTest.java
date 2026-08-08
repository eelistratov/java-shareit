package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void create_ShouldReturnCreatedUser() {
        User user = new User(null, "Иван Петров", "ivan@mail.ru");
        User savedUser = new User(1L, "Иван Петров", "ivan@mail.ru");
        UserDto userDto = new UserDto(null, "Иван Петров", "ivan@mail.ru");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.create(userDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Иван Петров", result.getName());
        assertEquals("ivan@mail.ru", result.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getById_UserNotFound_ShouldThrowException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.getById(999L));

        assertEquals("Пользователь с id 999 не найден", exception.getMessage());
    }
}