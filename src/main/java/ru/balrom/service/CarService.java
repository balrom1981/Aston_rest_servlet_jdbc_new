package ru.balrom.service;

import ru.balrom.dto.CarDto;
import ru.balrom.mapper.CarMapper;
import ru.balrom.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * class CarService, предназначен для CRUD операций с объектом типа PersonDto
 */
public class CarService implements Service<CarDto> {
    private final CarRepository carRepository;
    private final CarMapper mapper;

    /**
     * Конструктор, создающий объекты типа CarRepository и CarMapper
     */
    public CarService() {
        carRepository = new CarRepository();
        mapper = new CarMapper();
    }

    /**
     * получает объект типа Car по  id
     * @param id - входной параметр id
     * @return - возвращает объект типа CarDto
     */
    @Override
    public CarDto get(int id) {
        return mapper.toDto(carRepository.get(id));
    }

    /**
     * получает List<CarDto>
     * @return возвращает List<Car>
     */
    @Override
    public List<CarDto> getAll() {
        return carRepository.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * сохраняет объект типа CarDto преобразуя его в объект типа Car перед сохраннием
     */
    @Override
    public void save(CarDto carDTO) {
        carRepository.save(mapper.fromDto(carDTO));

    }

    /**
     * изменяет объект типа CarDto преобразуя его в объект типа Car перед изменением
     */
    @Override
    public void update(CarDto carDTO) {
        carRepository.update(mapper.fromDto(carDTO));
    }

    /**
     * удаляет объект по id
     */
    @Override
    public void delete(int id) {
        carRepository.delete(id);
    }
}
