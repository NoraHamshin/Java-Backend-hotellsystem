package com.hotel.booking.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Long id;

    @NotBlank(message = "Gästnamn får inte vara tomt")
    private String guestName;

    @NotBlank(message = "Rumstyp får inte vara tom")
    @Pattern(
            regexp = "Enkelrum|Dubbelrum|Svit",
            message = "Rumstyp måste vara 'Enkelrum', 'Dubbelrum' eller 'Svit'"
    )
    private String roomType;

    @Min(value = 1, message = "Antal gäster måste vara minst 1")
    @Max(value = 3, message = "Antal gäster får inte överskrida 3")
    private int numberOfGuests;

    private double totalPrice;
}