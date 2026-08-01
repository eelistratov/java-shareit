package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceImplTest {

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ItemService itemService;

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
}