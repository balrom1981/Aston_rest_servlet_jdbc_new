package ru.balrom.dto;

import lombok.*;

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
}