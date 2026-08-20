package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    ItemDto create(Long userId, ItemDto itemDto);

    ItemDto update(Long userId, Long itemId, ItemDto itemDto);

    ItemWithBookingsDto getByIdWithBookings(Long itemId);

    List<ItemWithBookingsDto> getAllByOwnerWithBookings(Long userId);

    ItemDto getById(Long itemId);

    List<ItemDto> getAllByOwner(Long userId);

    List<ItemDto> search(String text);

    CommentResponseDto addComment(Long userId, Long itemId, CommentRequestDto request);
}