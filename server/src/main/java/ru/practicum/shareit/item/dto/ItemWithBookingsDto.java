package ru.practicum.shareit.item.dto;

import lombok.Data;
import java.util.List;

@Data
public class ItemWithBookingsDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long owner;
    private Long request;
    private ItemBookingDto lastBooking;
    private ItemBookingDto nextBooking;
    private List<CommentResponseDto> comments;
}