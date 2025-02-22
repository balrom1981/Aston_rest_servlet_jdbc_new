package ru.balrom.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.balrom.dto.ApartmentDto;
import ru.balrom.mapper.ApartmentMapper;
import ru.balrom.model.Apartment;
import ru.balrom.repository.ApartmentRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest {
    private final static int ID = 1;
    private final static String CITY = "Moscow";
    private final static int ROOM_NUMBER = 1;
    private final static int PERSON_ID = 1;

    @Mock
    private ApartmentRepository repository;
    @Mock
    private ApartmentMapper mapper;
    private ApartmentService apartmentService;
    private Apartment expectedApartment;
    private ApartmentDto expectedApartmentDto;


    @BeforeEach
    void set() {
        apartmentService = new ApartmentService(repository,mapper);
        expectedApartment = Apartment.builder().id(ID).city(CITY).roomAmount(ROOM_NUMBER).personId(PERSON_ID).build();
        expectedApartmentDto = ApartmentDto.builder().id(ID).city(CITY).roomAmount(ROOM_NUMBER).personId(PERSON_ID).build();
    }

    @Test
    void getByIdTest(){
        when(repository.get(ID)).thenReturn(expectedApartment);
        when(apartmentService.get(ID)).thenReturn(expectedApartmentDto);

        ApartmentDto actualApartmentDto = apartmentService.get(ID);

        Assertions.assertEquals(expectedApartmentDto, actualApartmentDto);
    }

    @Test
    void saveTest(){
        when(mapper.fromDto(any(ApartmentDto.class))).thenReturn(expectedApartment);

        apartmentService.save(expectedApartmentDto);

        verify(mapper).fromDto(expectedApartmentDto);
        verify(repository).save(expectedApartment);
    }

    @Test
    void updateTest(){
        when(mapper.fromDto(any(ApartmentDto.class))).thenReturn(expectedApartment);

        apartmentService.update(expectedApartmentDto);

        verify(mapper).fromDto(expectedApartmentDto);
        verify(repository).update(expectedApartment);;
    }

    @Test
    void deleteTest(){
        apartmentService.delete(ID);
        verify(repository).delete(ID);
    }
}