package com.example.book_advisor.services;

import java.util.List;

import com.example.book_advisor.model.Genero;

public interface GeneroService {
    
    List<Genero> obtenerTodos();
    
    Genero obtenerPorId(Long id);
    
    Genero guardar(Genero genero);
    
    void eliminar(Long id);
}
