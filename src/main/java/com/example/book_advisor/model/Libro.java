package com.example.book_advisor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    private String autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private String sinopsis;
    private String fechaAlta;
    private int ano;
    @ManyToOne
    @JoinColumn(name="GENERO_ID", foreignKey = @ForeignKey(name="GENERO_ID_FK"))
    private Genero genero;
    private String imagen; // Nombre del archivo de imagen
}
