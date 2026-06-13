package com.hotel.booking.repository;

import com.hotel.booking.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookingRepository {

    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    public Optional<Booking> findById(Long id) {
        return bookings.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    public Booking save(Booking booking) {
        booking.setId(idCounter.getAndIncrement());
        bookings.add(booking);
        return booking;
    }

    public boolean deleteById(Long id) {
        return bookings.removeIf(b -> b.getId().equals(id));
    }

    public long countByRoomType(String roomType) {
        return bookings.stream()
                .filter(b -> b.getRoomType().equals(roomType))
                .count();
    }
}