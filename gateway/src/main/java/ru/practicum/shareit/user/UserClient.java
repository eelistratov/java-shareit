package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;

@Service
public class UserClient extends BaseClient {
    private final String serverUrl;

    public UserClient(RestTemplate rest, @Value("${shareit-server.url}") String serverUrl) {
        super(rest);
        this.serverUrl = serverUrl;
    }

    public ResponseEntity<String> create(Object userDto) {
        return post(serverUrl + "/users", userDto);
    }

    public ResponseEntity<String> update(Long userId, Object userDto) {
        return patch(serverUrl + "/users/" + userId, null, userDto);
    }

    public ResponseEntity<String> getById(Long userId) {
        return get(serverUrl + "/users/" + userId);
    }

    public ResponseEntity<String> getAll() {
        return get(serverUrl + "/users");
    }

    public ResponseEntity<String> delete(Long userId) {
        return delete(serverUrl + "/users/" + userId, userId);
    }
}