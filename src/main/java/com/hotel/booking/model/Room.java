package com.hotel.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Room {

    private String roomType;
    private int totalRooms;
    private int availableRooms;
    private int maxGuests;
    private double pricePerNight;
}