package com.hotel.booking.exception;

public class RoomFullyBookedException extends RuntimeException {

    private final String roomType;

    public RoomFullyBookedException(String roomType) {
        super("Inga lediga " + roomType + " finns tillgängliga just nu.");
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }
}