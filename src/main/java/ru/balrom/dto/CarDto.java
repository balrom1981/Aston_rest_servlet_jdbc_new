package ru.balrom.dto;

import lombok.*;

import java.util.Objects;

/**
 * Класс CarDto предназначен для хранения и передачи данных о классе Car
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CarDto {
    private  int id;
    private String brand;
    private String colour;
    private int personId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto carDto = (CarDto) o;
        return id == carDto.id && personId == carDto.personId && Objects.equals(brand, carDto.brand) && Objects.equals(colour, carDto.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, colour, personId);
    }
}
