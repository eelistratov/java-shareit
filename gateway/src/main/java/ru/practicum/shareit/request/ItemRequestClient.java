package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;

@Service
public class ItemRequestClient extends BaseClient {
    private final String serverUrl;

    public ItemRequestClient(RestTemplate rest, @Value("${shareit-server.url}") String serverUrl) {
        super(rest);
        this.serverUrl = serverUrl;
    }

    public ResponseEntity<String> create(Long userId, Object requestDto) {
        return post(serverUrl + "/requests", userId, requestDto);
    }

    public ResponseEntity<String> getOwnRequests(Long userId) {
        return get(serverUrl + "/requests", userId);
    }

    public ResponseEntity<String> getOtherRequests(Long userId) {
        return get(serverUrl + "/requests/all", userId);
    }

    public ResponseEntity<String> getRequestById(Long userId, Long requestId) {
        return get(serverUrl + "/requests/" + requestId, userId);
    }
}