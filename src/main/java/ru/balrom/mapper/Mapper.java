package ru.balrom.Aston_rest_servlet_jdbc.mapper;

public interface Mapper<E,T> {
    E fromDto(T t);
    T toDto(E e);
}
