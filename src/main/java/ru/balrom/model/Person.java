package ru.balrom.model;

import lombok.*;

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