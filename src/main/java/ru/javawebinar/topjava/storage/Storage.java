package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<T> {
    T update(T obj);

    T save(T obj);

    T get(int id);

    void delete(int id);

    List<T> getAll();

}
