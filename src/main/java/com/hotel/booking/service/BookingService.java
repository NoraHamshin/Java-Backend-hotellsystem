package com.hotel.booking.service;

import com.hotel.booking.exception.BookingNotFoundException;
import com.hotel.booking.exception.GuestCapacityException;
import com.hotel.booking.exception.RoomFullyBookedException;
import com.hotel.booking.model.Booking;
import com.hotel.booking.model.Room;
import com.hotel.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    private static final Map<String, int[]> ROOM_INVENTORY = Map.of(
            "Enkelrum",  new int[]{10, 1, 500},
            "Dubbelrum", new int[]{7,  2, 1000},
            "Svit",      new int[]{3,  3, 2000}
    );

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Room> getAllRooms() {
        return ROOM_INVENTORY.entrySet().stream()
                .map(entry -> {
                    String type = entry.getKey();
                    int[] data = entry.getValue();
                    int total = data[0];
                    int maxGuests = data[1];
                    double price = data[2];
                    long booked = bookingRepository.countByRoomType(type);
                    int available = (int) (total - booked);
                    return new Room(type, total, available, maxGuests, price);
                })
                .sorted((a, b) -> a.getRoomType().compareTo(b.getRoomType()))
                .toList();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(Booking booking) {
        String roomType = booking.getRoomType();
        int[] roomData = ROOM_INVENTORY.get(roomType);

        int maxGuests = roomData[1];
        double price   = roomData[2];
        int totalRooms = roomData[0];

        if (booking.getNumberOfGuests() > maxGuests) {
            throw new GuestCapacityException(roomType, maxGuests, booking.getNumberOfGuests());
        }

        long booked = bookingRepository.countByRoomType(roomType);
        if (booked >= totalRooms) {
            throw new RoomFullyBookedException(roomType);
        }

        booking.setTotalPrice(price);

        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        boolean deleted = bookingRepository.deleteById(id);
        if (!deleted) {
            throw new BookingNotFoundException(id);
        }
    }
}