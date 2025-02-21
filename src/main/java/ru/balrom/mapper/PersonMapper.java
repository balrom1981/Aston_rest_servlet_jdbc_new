package ru.balrom.mapper;


import ru.balrom.dto.PersonDto;
import ru.balrom.model.Person;

/**
 * class PersonMapper предназначен для преобразования объекта типа Person в PersonDto
 * и объекта типа PersonDto в Person
 */

public class PersonMapper implements Mapper<Person, PersonDto> {
    @Override
    public Person fromDto(PersonDto personDto) {
        return Person.builder().id(personDto.getId()).name(personDto.getName()).surname(personDto.getSurname())
                .age(personDto.getAge()).build();
    }

    @Override
    public PersonDto toDto(Person person) {
        if (person == null){
            return null;
        }
        return PersonDto.builder().id(person.getId()).name(person.getName()).surname(person.getSurname())
                .age(person.getAge()).build();
    }
}
