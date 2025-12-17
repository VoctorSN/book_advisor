package com.example.book_advisor.model;

public enum Idioma {
    ESPANOL("Español"),
    INGLES("Inglés"),
    OTROS("Otros");

    private final String nombre;

    Idioma(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
