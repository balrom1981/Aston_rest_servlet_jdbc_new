package ru.balrom.service;

import ru.balrom.dto.ApartmentDto;
import ru.balrom.mapper.ApartmentMapper;
import ru.balrom.repository.ApartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ApartmentService implements Service<ApartmentDto>{
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper mapper;

    public ApartmentService() {
        apartmentRepository = new ApartmentRepository();
        mapper = new ApartmentMapper();
    }

    @Override
    public ApartmentDto get(int id) {
        return mapper.toDto(apartmentRepository.get(id));
    }

    @Override
    public List<ApartmentDto> getAll() {
        return apartmentRepository.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void save(ApartmentDto apartmentDto) {
        apartmentRepository.save(mapper.fromDto(apartmentDto));
    }

    @Override
    public void update(ApartmentDto apartmentDto) {
        apartmentRepository.update(mapper.fromDto(apartmentDto));
    }

    @Override
    public void delete(int id) {
        apartmentRepository.delete(id);
    }
}
