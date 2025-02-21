package ru.balrom.repository;

import java.util.List;

/**
 * interface Repository<T> - это параметризированный интерфейс, предназначенный для получения обекта по id,
 * получения всех объектов, сохрания объекта, изменения обекта и удаления объекта
 * @param <T> - параметризированный объект
 */

public interface Repository<T> {
    /**
     * Получает объект Т по id
     */
    T get(int id);

    /**
     * получает весь список объектов типа Т
     */
    List<T> getAll();

    /**
     * сохраняет объект типа Т
     */
    void save(T t);

    /**
     * обновляет объект типа Т
     */
    void update(T t);

    /**
     * удаляет объект типа Т
     */
    void delete(int id);
}
