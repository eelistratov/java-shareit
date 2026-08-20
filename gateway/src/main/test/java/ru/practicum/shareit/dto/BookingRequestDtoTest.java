package ru.practicum.shareit.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingRequestDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serialize_ShouldConvertToJson() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(3);
        BookingRequestDto dto = new BookingRequestDto(1L, start, end);

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"itemId\":1");
        assertThat(json).contains("\"start\":\"" + start.toString() + "\"");
        assertThat(json).contains("\"end\":\"" + end.toString() + "\"");
    }

    @Test
    void deserialize_ShouldConvertFromJson() throws Exception {
        String json = """
                {
                    "itemId": 1,
                    "start": "2025-12-01T10:00:00",
                    "end": "2025-12-03T18:00:00"
                }
                """;

        BookingRequestDto dto = objectMapper.readValue(json, BookingRequestDto.class);

        assertThat(dto.getItemId()).isEqualTo(1L);
        assertThat(dto.getStart()).isNotNull();
        assertThat(dto.getEnd()).isNotNull();
    }
}