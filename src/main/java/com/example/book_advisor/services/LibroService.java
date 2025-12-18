package com.example.book_advisor.services;

import java.util.List;
import java.util.Optional;

import com.example.book_advisor.DTO.LibroDTO;
import com.example.book_advisor.model.Genero;
import com.example.book_advisor.model.Libro;

public interface LibroService {

    public List<Libro> getLibros();

    public List<Libro> getLibrosOrdenados();

    public Libro getLibroById(Long id);

    public void eliminarLibro(Long id);

    public void actualizarLibro(Libro libro);

    public void agregarLibro(Libro libro);

    public void setFiltroGenero(Optional<Genero> filtro);

    public Optional<Genero> getFiltroGenero();

    public void setFiltroTitulo(Optional<String> filtro);

    public Optional<String> getFiltroTitulo();

    public List<Genero> getGeneros();

    public List<LibroDTO> convertirLibrosADTO(List<Libro> librosOrdenados);
}