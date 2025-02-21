package ru.balrom.mapper;


import ru.balrom.dto.CarDto;
import ru.balrom.model.Car;

public class CarMapper implements Mapper<Car, CarDto>{
    @Override
    public Car fromDto(CarDto carDto) {
        return Car.builder().id(carDto.getId()).brand(carDto.getBrand()).colour(carDto.getColour())
                .personId(carDto.getPersonId()).build();
    }

    @Override
    public CarDto toDto(Car car) {
        if (car==null){
            return null;
        }
        return CarDto.builder().id(car.getId()).brand(car.getBrand()).colour(car.getColour())
                .personId(car.getPersonId()).build();
    }
}
