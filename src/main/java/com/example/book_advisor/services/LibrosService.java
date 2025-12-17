package com.example.book_advisor.services;

import java.util.List;
import java.util.Optional;

import com.example.book_advisor.model.Genero;
import com.example.book_advisor.model.Libro;

public interface LibrosService {

    public List<Libro> getLibros();

    public List<Libro> getLibrosOrdenados();

    public Libro getLibroById(int id);

    public void eliminarLibro(int id);

    public void actualizarLibro(Libro libro);

    public void agregarLibro(Libro libro);

    public void setFiltroGenero(Optional<Genero> filtro);

    public Optional<Genero> getFiltroGenero();

    public void setFiltroTitulo(Optional<String> filtro);

    public Optional<String> getFiltroTitulo();
}