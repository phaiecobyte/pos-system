package com.phaiecobyte.possystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseService <T,REQ>{
    List<T> getAll();
    Page<T> getAll(Pageable pageable);
    Optional<T> getById(long id);
    T create(REQ req);
    T update(T req);
    void remove(long id);
    long count();
}
