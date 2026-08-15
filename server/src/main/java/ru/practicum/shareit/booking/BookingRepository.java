package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerId(Long bookerId, Sort sort);

    List<Booking> findByBookerIdAndStatus(Long bookerId, BookingStatus status, Sort sort);

    List<Booking> findByBookerIdAndEndBefore(Long bookerId, LocalDateTime end, Sort sort);

    List<Booking> findByBookerIdAndStartAfter(Long bookerId, LocalDateTime start, Sort sort);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = ?1 AND b.start <= ?2 AND b.end >= ?2")
    List<Booking> findCurrentBookingsByBooker(Long bookerId, LocalDateTime now, Sort sort);

    @Query("SELECT b FROM Booking b WHERE b.item.owner = ?1")
    List<Booking> findByItemOwnerId(Long ownerId, Sort sort);

    @Query("SELECT b FROM Booking b WHERE b.item.owner = ?1 AND b.status = ?2")
    List<Booking> findByItemOwnerIdAndStatus(Long ownerId, BookingStatus status, Sort sort);

    @Query("SELECT b FROM Booking b WHERE b.item.owner = ?1 AND b.end < ?2")
    List<Booking> findByItemOwnerIdAndEndBefore(Long ownerId, LocalDateTime end, Sort sort);

    @Query("SELECT b FROM Booking b WHERE b.item.owner = ?1 AND b.start > ?2")
    List<Booking> findByItemOwnerIdAndStartAfter(Long ownerId, LocalDateTime start, Sort sort);

    @Query("SELECT b FROM Booking b WHERE b.item.owner = ?1 AND b.start <= ?2 AND b.end >= ?2")
    List<Booking> findCurrentBookingsByOwner(Long ownerId, LocalDateTime now, Sort sort);

    List<Booking> findByItemId(Long itemId);

    List<Booking> findByItemIdOrderByStartDesc(Long itemId);

    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.item.id = ?1 AND b.booker.id = ?2 AND b.end < ?3 AND b.status = 'APPROVED'")
    boolean existsByItemIdAndBookerIdAndEndBeforeAndStatusApproved(Long itemId, Long userId, LocalDateTime now);
}