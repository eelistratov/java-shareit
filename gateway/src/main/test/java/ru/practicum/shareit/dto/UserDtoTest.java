package ru.practicum.shareit.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void serialize_ShouldConvertToJson() throws Exception {
        UserDto dto = new UserDto(1L, "Иван Петров", "ivan@mail.ru");
        String json = objectMapper.writeValueAsString(dto);
        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"name\":\"Иван Петров\"");
        assertThat(json).contains("\"email\":\"ivan@mail.ru\"");
    }

    @Test
    void deserialize_ShouldConvertFromJson() throws Exception {
        String json = """
                {
                    "id": 1,
                    "name": "Иван Петров",
                    "email": "ivan@mail.ru"
                }
                """;
        UserDto dto = objectMapper.readValue(json, UserDto.class);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Иван Петров");
        assertThat(dto.getEmail()).isEqualTo("ivan@mail.ru");
    }
}