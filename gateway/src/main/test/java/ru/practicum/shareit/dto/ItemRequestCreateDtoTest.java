package ru.practicum.shareit.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemRequestCreateDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serialize_ShouldConvertToJson() throws Exception {
        ItemRequestCreateDto dto = new ItemRequestCreateDto("Нужна дрель");
        String json = objectMapper.writeValueAsString(dto);
        assertThat(json).contains("\"description\":\"Нужна дрель\"");
    }

    @Test
    void deserialize_ShouldConvertFromJson() throws Exception {
        String json = """
                {
                    "description": "Нужна дрель"
                }
                """;
        ItemRequestCreateDto dto = objectMapper.readValue(json, ItemRequestCreateDto.class);
        assertThat(dto.getDescription()).isEqualTo("Нужна дрель");
    }
}