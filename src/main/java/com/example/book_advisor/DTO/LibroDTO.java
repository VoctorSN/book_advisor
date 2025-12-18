package com.example.book_advisor.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class LibroDTO {
    
    private Long id;
    private String titulo;
    private String autor;
    private String sinopsis;
    private String idioma;
    private String imagen;

}
