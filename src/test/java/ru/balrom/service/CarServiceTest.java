package ru.balrom.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.balrom.dto.CarDto;
import ru.balrom.mapper.CarMapper;
import ru.balrom.model.Car;
import ru.balrom.repository.CarRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    private final static int ID = 1;
    private final static String BRAND = "LADA";
    private final static String COLOUR = "Black";
    private final static int PERSON_ID = 1;

    @Mock
    private CarRepository repository;
    @Mock
    private CarMapper mapper;
    private CarService carService;
    private Car expectedCar;
    private CarDto expectedCarDto;


    @BeforeEach
    void set() {
        carService = new CarService(repository,mapper);
        expectedCar = Car.builder().id(ID).brand(BRAND).colour(COLOUR).personId(PERSON_ID).build();
        expectedCarDto = CarDto.builder().id(ID).brand(BRAND).colour(COLOUR).personId(PERSON_ID).build();
    }

    @Test
    void getByIdTest(){
        when(repository.get(ID)).thenReturn(expectedCar);
        when(carService.get(ID)).thenReturn(expectedCarDto);

        CarDto actualCarDto = carService.get(ID);

        Assertions.assertEquals(expectedCarDto, actualCarDto);
    }

    @Test
    void saveTest(){
        when(mapper.fromDto(any(CarDto.class))).thenReturn(expectedCar);

        carService.save(expectedCarDto);

        verify(mapper).fromDto(expectedCarDto);
        verify(repository).save(expectedCar);
    }

    @Test
    void updateTest(){
        when(mapper.fromDto(any(CarDto.class))).thenReturn(expectedCar);

        carService.update(expectedCarDto);

        verify(mapper).fromDto(expectedCarDto);
        verify(repository).update(expectedCar);
    }

    @Test
    void deleteTest(){
        carService.delete(ID);
        verify(repository).delete(ID);
    }
}