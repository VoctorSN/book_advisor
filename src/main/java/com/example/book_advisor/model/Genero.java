package com.example.book_advisor.model;

public enum Genero {
    ACCION("Acción"),
    COMEDIA("Comedia"),
    DRAMA("Drama"),
    AVENTURA("Aventura"),
    CIENCIA_FICCION("Ciencia Ficción"),
    TERROR("Terror"),
    FANTASIA("Fantasía"),
    THRILLER("Thriller"),
    MISTERIO("Misterio"),
    ROMANCE("Romance");

    private final String nombre;

    Genero(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
