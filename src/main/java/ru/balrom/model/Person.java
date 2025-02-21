package ru.balrom.model;

import lombok.*;

/**
 * Класс Person хранит в себе информацию о имени, фамилии и возрасте человека
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Person {
    private int id;
    private String name;
    private String surname;
    private int age;
}