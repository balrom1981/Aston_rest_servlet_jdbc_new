package ru.balrom.service;

import ru.balrom.dto.ApartmentDto;
import ru.balrom.mapper.ApartmentMapper;
import ru.balrom.repository.ApartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * class ApartmentService, предназначен для CRUD операций с объектом типа ApartmentDto
 */
public class ApartmentService implements Service<ApartmentDto>{
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper mapper;

    /**
     * Конструктор, создающий объекты типа ApartmentRepository и ApartmentMapper
     */
    public ApartmentService() {
        apartmentRepository = new ApartmentRepository();
        mapper = new ApartmentMapper();
    }

    /**
     * получает объект типа Apartment по  id
     * @param id - входной параметр id
     * @return - возвращает объект типа ApartmentDto
     */
    @Override
    public ApartmentDto get(int id) {
        return mapper.toDto(apartmentRepository.get(id));
    }

    /**
     * получает List<ApartmentDto>
     * @return возвращает List<Apartment>
     */
    @Override
    public List<ApartmentDto> getAll() {
        return apartmentRepository.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * сохраняет объект типа ApartmentDto преобразуя его в объект типа Apartment перед сохраннием
     */
    @Override
    public void save(ApartmentDto apartmentDto) {
        apartmentRepository.save(mapper.fromDto(apartmentDto));
    }

    /**
     * изменяет объект типа ApartmentDto преобразуя его в объект типа Apartment перед изменением
     */
    @Override
    public void update(ApartmentDto apartmentDto) {
        apartmentRepository.update(mapper.fromDto(apartmentDto));
    }

    /**
     * удаляет объект по id
     */
    @Override
    public void delete(int id) {
        apartmentRepository.delete(id);
    }
}
