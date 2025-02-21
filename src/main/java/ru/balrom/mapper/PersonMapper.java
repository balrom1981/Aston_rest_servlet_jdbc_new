package ru.balrom.Aston_rest_servlet_jdbc.mapper;

import ru.balrom.Aston_rest_servlet_jdbc.dto.PersonDto;
import ru.balrom.Aston_rest_servlet_jdbc.entity.Person;

public class PersonMapper implements Mapper<Person, PersonDto> {
    @Override
    public Person fromDto(PersonDto personDto) {
        return Person.builder().id(personDto.getId()).name(personDto.getName()).surname(personDto.getSurname())
                .idCar(personDto.getIdCar()).idApartment(personDto.getIdApartment()).build();
    }

    @Override
    public PersonDto toDto(Person person) {
        if (person == null){
            return null;
        }
        return PersonDto.builder().id(person.getId()).name(person.getName()).surname(person.getSurname())
                .idCar(person.getIdCar()).idApartment(person.getIdApartment()).build();
    }
}
