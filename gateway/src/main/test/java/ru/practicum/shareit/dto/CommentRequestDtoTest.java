package ru.practicum.shareit.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.item.dto.CommentRequestDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CommentRequestDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serialize_ShouldConvertToJson() throws Exception {
        CommentRequestDto dto = new CommentRequestDto("Отличная вещь!");
        String json = objectMapper.writeValueAsString(dto);
        assertThat(json).contains("\"text\":\"Отличная вещь!\"");
    }

    @Test
    void deserialize_ShouldConvertFromJson() throws Exception {
        String json = """
                {
                    "text": "Отличная вещь!"
                }
                """;
        CommentRequestDto dto = objectMapper.readValue(json, CommentRequestDto.class);
        assertThat(dto.getText()).isEqualTo("Отличная вещь!");
    }
}