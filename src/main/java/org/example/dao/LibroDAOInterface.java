package org.example.dao;

import org.example.entidades.Libro;

import java.util.List;

public interface LibroDAOInterface {

    List<Libro> devolverTodos();

    Libro buscarPorId(long id);

    Libro create(Libro libro);

    Libro actualizar(Libro libro);

    boolean deleteById(long id);
}
