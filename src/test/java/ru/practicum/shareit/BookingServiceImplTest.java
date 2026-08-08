package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookingServiceImplTest {

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private BookingService bookingService;

    @Test
    void create_ShouldThrowException_WhenStartInPast() {
        User user = new User(1L, "Иван", "ivan@mail.ru");
        Item item = new Item(1L, "Дрель", "Мощная", true, 2L, null);

        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        BookingRequestDto request = new BookingRequestDto(1L, start, end);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(BadRequestException.class, () -> bookingService.create(1L, request));
    }

    @Test
    void create_ShouldThrowException_WhenBookerIsOwner() {
        User user = new User(1L, "Иван", "ivan@mail.ru");
        Item item = new Item(1L, "Дрель", "Мощная", true, 1L, null);

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        BookingRequestDto request = new BookingRequestDto(1L, start, end);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(NotFoundException.class, () -> bookingService.create(1L, request));
    }

    @Test
    void create_ShouldThrowException_WhenItemNotAvailable() {
        User user = new User(1L, "Иван", "ivan@mail.ru");
        Item item = new Item(1L, "Дрель", "Мощная", false, 2L, null);

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        BookingRequestDto request = new BookingRequestDto(1L, start, end);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        assertThrows(RuntimeException.class, () -> bookingService.create(1L, request));
    }

    @Test
    void getById_ShouldThrowException_WhenBookingNotFound() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.getById(1L, 999L));
    }
}