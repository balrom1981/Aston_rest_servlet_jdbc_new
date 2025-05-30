package ru.balrom.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.balrom.dto.PersonDto;
import ru.balrom.mapper.PersonMapper;
import ru.balrom.model.Person;
import ru.balrom.repository.PersonRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    private final static int ID = 1;
    private final static String NAME = "Ivan";
    private final static String SURNAME = "Smirnov";
    private final static int AGE = 18;

    @Mock
    private PersonRepository repository;
    @Mock
    private PersonMapper mapper;
    private PersonService personService;
    private Person expectedPerson;
    private PersonDto expectedPersonDto;


    @BeforeEach
    void set() {
        personService = new PersonService(repository,mapper);
        expectedPerson = Person.builder().id(ID).name(NAME).surname(SURNAME).age(AGE).build();
        expectedPersonDto = PersonDto.builder().id(ID).name(NAME).surname(SURNAME).age(AGE).build();
    }

    @Test
    void getByIdTest(){
        when(repository.get(ID)).thenReturn(expectedPerson);
        when(personService.get(ID)).thenReturn(expectedPersonDto);

        PersonDto actualPersonDto = personService.get(ID);

        Assertions.assertEquals(expectedPersonDto, actualPersonDto);
    }

    @Test
    void saveTest(){
        when(mapper.fromDto(any(PersonDto.class))).thenReturn(expectedPerson);

        personService.save(expectedPersonDto);

        verify(mapper).fromDto(expectedPersonDto);
        verify(repository).save(expectedPerson);
    }

    @Test
    void updateTest(){
        when(mapper.fromDto(any(PersonDto.class))).thenReturn(expectedPerson);

        personService.update(expectedPersonDto);

        verify(mapper).fromDto(expectedPersonDto);
        verify(repository).update(expectedPerson);
    }

    @Test
    void deleteTest(){
        personService.delete(ID);
        verify(repository).delete(ID);
    }
}