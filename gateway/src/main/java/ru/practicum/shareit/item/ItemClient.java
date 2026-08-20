package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;

@Service
public class ItemClient extends BaseClient {
    private final String serverUrl;

    public ItemClient(RestTemplate rest, @Value("${shareit-server.url}") String serverUrl) {
        super(rest);
        this.serverUrl = serverUrl;
    }

    public ResponseEntity<String> create(Long userId, Object itemDto) {
        return post(serverUrl + "/items", userId, itemDto);
    }

    public ResponseEntity<String> update(Long userId, Long itemId, Object itemDto) {
        return patch(serverUrl + "/items/" + itemId, userId, itemDto);
    }

    public ResponseEntity<String> getById(Long itemId) {
        return get(serverUrl + "/items/" + itemId);
    }

    public ResponseEntity<String> getAllByOwner(Long userId) {
        return get(serverUrl + "/items", userId);
    }

    public ResponseEntity<String> search(String text) {
        return get(serverUrl + "/items/search?text=" + (text != null ? text : ""));
    }

    public ResponseEntity<String> addComment(Long userId, Long itemId, Object commentDto) {
        return post(serverUrl + "/items/" + itemId + "/comment", userId, commentDto);
    }
}