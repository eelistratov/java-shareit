package ru.practicum.shareit.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.dto.BookingUserDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingResponseDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serialize_ShouldConvertToJson() throws Exception {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(1L);
        dto.setStart(LocalDateTime.now().plusDays(1));
        dto.setEnd(LocalDateTime.now().plusDays(3));
        dto.setStatus(BookingStatus.WAITING);

        BookingUserDto booker = new BookingUserDto();
        booker.setId(1L);
        booker.setName("Иван");
        dto.setBooker(booker);

        BookingItemDto item = new BookingItemDto();
        item.setId(1L);
        item.setName("Дрель");
        dto.setItem(item);

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"status\":\"WAITING\"");
        assertThat(json).contains("\"booker\":{\"id\":1,\"name\":\"Иван\"}");
        assertThat(json).contains("\"item\":{\"id\":1,\"name\":\"Дрель\"}");
    }

    @Test
    void deserialize_ShouldConvertFromJson() throws Exception {
        // Given
        String json = """
                {
                    "id": 1,
                    "status": "WAITING",
                    "booker": {
                        "id": 1,
                        "name": "Иван"
                    },
                    "item": {
                        "id": 1,
                        "name": "Дрель"
                    }
                }
                """;

        BookingResponseDto dto = objectMapper.readValue(json, BookingResponseDto.class);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getStatus()).isEqualTo(BookingStatus.WAITING);
        assertThat(dto.getBooker().getId()).isEqualTo(1L);
        assertThat(dto.getItem().getName()).isEqualTo("Дрель");
    }
}