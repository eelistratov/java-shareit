package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.client.BaseClient;

@Service
public class BookingClient extends BaseClient {
    private final String serverUrl;

    public BookingClient(RestTemplate rest, @Value("${shareit-server.url}") String serverUrl) {
        super(rest);
        this.serverUrl = serverUrl;
    }

    public ResponseEntity<String> create(Long userId, Object bookingDto) {
        return post(serverUrl + "/bookings", userId, bookingDto);
    }

    public ResponseEntity<String> approve(Long userId, Long bookingId, Boolean approved) {
        return patch(serverUrl + "/bookings/" + bookingId + "?approved=" + approved, userId, null);
    }

    public ResponseEntity<String> getById(Long userId, Long bookingId) {
        return get(serverUrl + "/bookings/" + bookingId, userId);
    }

    public ResponseEntity<String> getAllByBooker(Long userId, String state) {
        return get(serverUrl + "/bookings?state=" + (state != null ? state : "ALL"), userId);
    }

    public ResponseEntity<String> getAllByOwner(Long userId, String state) {
        return get(serverUrl + "/bookings/owner?state=" + (state != null ? state : "ALL"), userId);
    }
}