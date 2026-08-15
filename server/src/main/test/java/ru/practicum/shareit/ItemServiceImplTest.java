package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void create_ShouldReturnCreatedItem() {
        Item item = new Item(null, "Дрель Салют", "Мощность 600 вт", true, 1L, null);
        Item savedItem = new Item(1L, "Дрель Салют", "Мощность 600 вт", true, 1L, null);
        ItemDto itemDto = new ItemDto(null, "Дрель Салют", "Мощность 600 вт", true, 1L, null);

        when(userRepository.existsById(1L)).thenReturn(true);
        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

        ItemDto result = itemService.create(1L, itemDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Дрель Салют", result.getName());
        assertTrue(result.getAvailable());
        assertEquals(1L, result.getOwner());

        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void create_UserNotFound_ShouldThrowException() {
        ItemDto itemDto = new ItemDto(null, "Дрель Салют", "Мощность 600 вт", true, 1L, null);

        when(userRepository.existsById(999L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> itemService.create(999L, itemDto));

        assertEquals("Пользователь с id 999 не найден", exception.getMessage());
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void update_ShouldReturnUpdatedItem() {
        Item existingItem = new Item(1L, "Дрель Салют", "Мощность 600 вт", true, 1L, null);
        ItemDto updateDto = new ItemDto(1L, "Дрель Профи", "Мощность 800 вт", false, 1L, null);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(any(Item.class))).thenReturn(existingItem);

        ItemDto result = itemService.update(1L, 1L, updateDto);

        assertNotNull(result);
        assertEquals("Дрель Профи", result.getName());
        assertFalse(result.getAvailable());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void update_ItemNotFound_ShouldThrowException() {
        ItemDto updateDto = new ItemDto(1L, "Дрель Профи", "Мощность 800 вт", false, 1L, null);

        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.update(1L, 999L, updateDto));
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void update_NotOwner_ShouldThrowException() {
        Item existingItem = new Item(1L, "Дрель Салют", "Мощность 600 вт", true, 1L, null);
        ItemDto updateDto = new ItemDto(1L, "Дрель Профи", "Мощность 800 вт", false, 2L, null);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));

        assertThrows(RuntimeException.class, () -> itemService.update(2L, 1L, updateDto));
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void getById_ShouldReturnItem() {
        Item item = new Item(1L, "Дрель Салют", "Мощность 600 вт", true, 1L, null);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ItemDto result = itemService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Дрель Салют", result.getName());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void getById_ItemNotFound_ShouldThrowException() {
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.getById(999L));
    }

    @Test
    void search_ShouldReturnItems() {
        Item item1 = new Item(1L, "Дрель Салют", "Мощная дрель", true, 1L, null);

        when(itemRepository.search("дрель")).thenReturn(List.of(item1));

        List<ItemDto> result = itemService.search("дрель");

        assertEquals(1, result.size());
        verify(itemRepository, times(1)).search("дрель");
    }

    @Test
    void search_WithEmptyText_ShouldReturnEmptyList() {
        List<ItemDto> result = itemService.search("");

        assertEquals(0, result.size());
        verify(itemRepository, never()).search(anyString());
    }

    @Test
    void addComment_ShouldReturnCreatedComment() {
        User user = new User(1L, "Иван", "ivan@mail.ru");
        Item item = new Item(1L, "Дрель", "Мощная", true, 2L, null);
        CommentRequestDto request = new CommentRequestDto("Отличная вещь!");
        Comment savedComment = new Comment(1L, "Отличная вещь!", item, user, LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(bookingRepository.existsByItemIdAndBookerIdAndEndBeforeAndStatusApproved(
                anyLong(), anyLong(), any(LocalDateTime.class))).thenReturn(true);
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        CommentResponseDto result = itemService.addComment(1L, 1L, request);

        assertNotNull(result);
        assertEquals("Отличная вещь!", result.getText());
        assertEquals("Иван", result.getAuthorName());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void addComment_UserNotFound_ShouldThrowException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        CommentRequestDto request = new CommentRequestDto("Отличная вещь!");

        assertThrows(NotFoundException.class, () -> itemService.addComment(999L, 1L, request));
        verify(commentRepository, never()).save(any(Comment.class));
    }

}