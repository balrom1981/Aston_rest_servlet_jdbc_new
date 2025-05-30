package ru.balrom.dto;

import lombok.*;

import java.util.Objects;

/**
 * Класс PersonDto предназначен для хранения и передачи данных о классе Person
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class PersonDto {
    private int id;
    private String name;
    private String surname;
    private int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDto personDto = (PersonDto) o;
        return id == personDto.id && age == personDto.age && Objects.equals(name, personDto.name) && Objects.equals(surname, personDto.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age);
    }
}