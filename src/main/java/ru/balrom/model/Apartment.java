package ru.balrom.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Apartment {
    private int id;
    private String city;
    private int roomAmount;
    private int personId;
}
