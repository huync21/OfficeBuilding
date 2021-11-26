package com.example.officebuilding.service;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Optional<T> findById(Integer id);

    T save(T t);

    void remove(Integer id);
}
