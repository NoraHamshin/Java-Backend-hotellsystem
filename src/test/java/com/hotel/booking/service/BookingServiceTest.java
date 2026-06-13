package com.hotel.booking.service;

import com.hotel.booking.exception.GuestCapacityException;
import com.hotel.booking.exception.RoomFullyBookedException;
import com.hotel.booking.model.Booking;
import com.hotel.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking validBooking;

    @BeforeEach
    void setUp() {
        validBooking = new Booking();
        validBooking.setGuestName("Anna Svensson");
        validBooking.setRoomType("Enkelrum");
        validBooking.setNumberOfGuests(1);
    }

    @Test
    void createBooking_ShouldSetCorrectPrice_ForEnkelrum() {
        when(bookingRepository.countByRoomType("Enkelrum")).thenReturn(0L);
        when(bookingRepository.save(any())).thenAnswer(inv -> {
            Booking b = inv.getArgument(0);
            b.setId(1L);
            return b;
        });

        Booking result = bookingService.createBooking(validBooking);

        assertEquals(500.0, result.getTotalPrice());
        verify(bookingRepository).save(validBooking);
    }

    @Test
    void createBooking_ShouldThrowGuestCapacityException_WhenTooManyGuests() {
        validBooking.setNumberOfGuests(2);

        GuestCapacityException ex = assertThrows(
                GuestCapacityException.class,
                () -> bookingService.createBooking(validBooking)
        );

        assertTrue(ex.getMessage().contains("Enkelrum"));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void createBooking_ShouldThrowRoomFullyBookedException_WhenNoRoomsLeft() {
        when(bookingRepository.countByRoomType("Enkelrum")).thenReturn(10L);

        RoomFullyBookedException ex = assertThrows(
                RoomFullyBookedException.class,
                () -> bookingService.createBooking(validBooking)
        );

        assertTrue(ex.getMessage().contains("Enkelrum"));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void createBooking_ShouldSetCorrectPrice_ForSvit() {
        Booking svitBooking = new Booking();
        svitBooking.setGuestName("Erik Lindgren");
        svitBooking.setRoomType("Svit");
        svitBooking.setNumberOfGuests(3);

        when(bookingRepository.countByRoomType("Svit")).thenReturn(0L);
        when(bookingRepository.save(any())).thenAnswer(inv -> {
            Booking b = inv.getArgument(0);
            b.setId(2L);
            return b;
        });

        Booking result = bookingService.createBooking(svitBooking);

        assertEquals(2000.0, result.getTotalPrice());
    }
}