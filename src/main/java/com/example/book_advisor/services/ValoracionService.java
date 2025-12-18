package com.example.book_advisor.services;

import java.util.Optional;

import com.example.book_advisor.model.Libro;
import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.model.Valoracion;

public interface ValoracionService {
    
    void guardarValoracion(Usuario usuario, Libro libro, Integer puntuacion);
    
    Optional<Valoracion> obtenerValoracion(Usuario usuario, Libro libro);
    
    Double calcularMediaLibro(Libro libro);
    
    Long contarValoraciones(Libro libro);
}
