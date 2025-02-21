package ru.balrom.mapper;

/**
 * interface Mapper<E,T> предназначен для преобразования объекта типа E в T и наоборот
 * @param <E> объекта типа E
 * @param <T> объекта типа Т
 */
public interface Mapper<E,T> {
    E fromDto(T t);
    T toDto(E e);
}
