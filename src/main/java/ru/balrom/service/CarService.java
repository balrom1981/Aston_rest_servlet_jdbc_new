package ru.balrom.service;

import ru.balrom.dto.CarDto;
import ru.balrom.mapper.CarMapper;
import ru.balrom.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CarService implements Service<CarDto> {
    private final CarRepository carRepository;
    private final CarMapper mapper;

    public CarService() {
        carRepository = new CarRepository();
        mapper = new CarMapper();
    }

    @Override
    public CarDto get(int id) {
        return mapper.toDto(carRepository.get(id));
    }

    @Override
    public List<CarDto> getAll() {
        return carRepository.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void save(CarDto carDTO) {
        carRepository.save(mapper.fromDto(carDTO));

    }

    @Override
    public void update(CarDto carDTO) {
        carRepository.update(mapper.fromDto(carDTO));
    }

    @Override
    public void delete(int id) {
        carRepository.delete(id);
    }
}
