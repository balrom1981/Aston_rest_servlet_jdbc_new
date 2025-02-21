package ru.balrom.dto;

import lombok.*;

import java.util.Objects;

/**
 * Класс ApartmentDto предназначен для передачи данных о классе Apartment
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApartmentDto {
    private int id;
    private String city;
    private int roomAmount;
    private int personId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentDto that = (ApartmentDto) o;
        return id == that.id && roomAmount == that.roomAmount && personId == that.personId && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, roomAmount, personId);
    }
}
