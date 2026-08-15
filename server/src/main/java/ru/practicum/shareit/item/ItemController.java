package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestHeader(value = "X-Sharer-User-Id", required = true) Long userId,
                          @RequestBody ItemDto itemDto) {  // ← убрали @Valid
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(value = "X-Sharer-User-Id", required = true) Long userId,
                          @PathVariable Long itemId,
                          @RequestBody ItemDto itemDto) {  // ← убрали @Valid
        return itemService.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemWithBookingsDto getById(@PathVariable Long itemId) {
        return itemService.getByIdWithBookings(itemId);
    }

    @GetMapping
    public List<ItemWithBookingsDto> getAllByOwner(@RequestHeader(value = "X-Sharer-User-Id", required = true) Long userId) {
        return itemService.getAllByOwnerWithBookings(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponseDto addComment(@RequestHeader(value = "X-Sharer-User-Id", required = true) Long userId,
                                         @PathVariable Long itemId,
                                         @RequestBody CommentRequestDto commentRequestDto) {  // ← убрали @Valid
        return itemService.addComment(userId, itemId, commentRequestDto);
    }
}