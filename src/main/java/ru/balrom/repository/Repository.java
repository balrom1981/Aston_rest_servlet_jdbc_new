package ru.balrom.repository;

import java.util.List;

public interface Repository<T> {
    T get(int id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(int id);
}
