package com.example.book_advisor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private Idioma idioma;
    private String sinopsis;
    private String fechaAlta;
    private int ano;
    private Genero genero;
    private String imagen; // Nombre del archivo de imagen
}
