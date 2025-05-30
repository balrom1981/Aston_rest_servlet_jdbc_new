package ru.balrom.model;

import lombok.*;

/**
 * Класс Car хранит в себе информацию об производителе, цвете и владельце автомобиля
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Car {
    private  int id;
    private String brand;
    private String colour;
    private int personId;
}
