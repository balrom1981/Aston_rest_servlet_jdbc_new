package ru.balrom.model;

import lombok.*;

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
