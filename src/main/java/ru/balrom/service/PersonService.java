package ru.balrom.service;

import ru.balrom.dto.PersonDto;
import ru.balrom.mapper.PersonMapper;
import ru.balrom.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * class PersonService, предназначен для CRUD операций с объектом типа PersonDto
 */
public class PersonService implements Service<PersonDto> {
    private final PersonRepository repository;
    private final PersonMapper mapper;

    /**
     * Конструктор, создающий объекты типа PersonRepository и PersonMapper
     */
    public PersonService() {
        repository = new PersonRepository();
        mapper = new PersonMapper();
    }

    /**
     * получает объект типа Person по  id
     * @param id - входной параметр id
     * @return - возвращает объект типа PersonDto
     */
    @Override
    public PersonDto get(int id) {
        return mapper.toDto(repository.get(id));
    }

    /**
     * получает List<Person>
     * @return возвращает List<PersonDto>
     */
    @Override
    public List<PersonDto> getAll() {
        return repository.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * сохраняет объект типа PersonDto преобразуя его в объект типа Person перед сохраннием
     */
    @Override
    public void save(PersonDto personDto) {
        repository.save(mapper.fromDto(personDto));
    }

    /**
     * изменяет объект типа PersonDto преобразуя его в объект типа Person перед изменением
     */
    @Override
    public void update(PersonDto personDto) {
        repository.update(mapper.fromDto(personDto));
    }

    /**
     * удаляет объект по id
     */
    @Override
    public void delete(int id) {
        repository.delete(id);
    }
}
