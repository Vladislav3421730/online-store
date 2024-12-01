package com.example.webapp.dao;


import java.util.List;
import java.util.Optional;

public interface DAO<K,E> {
     List<E> findAll();
     Optional<E> findById(K id);
     E update(E object);
     void delete(K id);
     void save(E object);


}
