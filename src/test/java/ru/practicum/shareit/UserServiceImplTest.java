package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
        when(userRepository.findById(999L)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.getById(999L));

        assertEquals("Пользователь с id 999 не найден", exception.getMessage());
    }
}