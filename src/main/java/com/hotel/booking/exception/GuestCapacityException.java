package com.hotel.booking.exception;

public class GuestCapacityException extends RuntimeException {

    public GuestCapacityException(String roomType, int maxGuests, int requested) {
        super("Rumstypen '" + roomType + "' tillåter max " + maxGuests +
                " gäst(er), men " + requested + " begärdes.");
    }
}