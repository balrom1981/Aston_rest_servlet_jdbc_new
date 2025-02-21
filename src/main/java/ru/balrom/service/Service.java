package ru.balrom.service;

import java.util.List;

/**
 * interface Service<T> - это параметризированный интерфейс, предназначенный для выполнения CRUD операций над объектами
 * @param <T> -параметризированный объект
 */
public interface Service<T> {

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
