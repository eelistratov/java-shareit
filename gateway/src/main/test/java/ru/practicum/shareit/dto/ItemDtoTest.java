package ru.practicum.shareit.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.item.dto.ItemDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serialize_ShouldConvertToJson() throws Exception {
        ItemDto dto = new ItemDto(1L, "Дрель", "Мощная дрель", true, 1L, 5L);

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"name\":\"Дрель\"");
        assertThat(json).contains("\"description\":\"Мощная дрель\"");
        assertThat(json).contains("\"available\":true");
        assertThat(json).contains("\"owner\":1");
        assertThat(json).contains("\"request\":5");
    }

    @Test
    void deserialize_ShouldConvertFromJson() throws Exception {
        String json = """
                {
                    "id": 1,
                    "name": "Дрель",
                    "description": "Мощная дрель",
                    "available": true,
                    "owner": 1,
                    "request": 5
                }
                """;

        ItemDto dto = objectMapper.readValue(json, ItemDto.class);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Дрель");
        assertThat(dto.getDescription()).isEqualTo("Мощная дрель");
        assertThat(dto.getAvailable()).isTrue();
        assertThat(dto.getOwner()).isEqualTo(1L);
        assertThat(dto.getRequest()).isEqualTo(5L);
    }

    @Test
    void deserialize_WithNullAvailable_ShouldSetNull() throws Exception {
        String json = """
                {
                    "id": 1,
                    "name": "Дрель",
                    "description": "Мощная дрель",
                    "available": null
                }
                """;

        ItemDto dto = objectMapper.readValue(json, ItemDto.class);

        assertThat(dto.getAvailable()).isNull();
    }
}