package com.hotel.booking.exception;

public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(Long id) {
        super("Bokning med id " + id + " hittades inte.");
    }
}