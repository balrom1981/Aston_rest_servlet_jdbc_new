package ru.balrom.mapper;

public interface Mapper<E,T> {
    E fromDto(T t);
    T toDto(E e);
}
