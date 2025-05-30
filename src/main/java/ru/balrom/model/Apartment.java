package ru.balrom.model;

import lombok.*;


/**
 * Класс Apartment хранит в себе информацию об городе, количестве комнат и владельце квартиры
 */

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
