package com.example.book_advisor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.book_advisor.model.Genero;
import com.example.book_advisor.model.Libro;

@Repository
public interface LibroRepository extends  JpaRepository<Libro, Long>{
    
    @Query("SELECT l FROM Libro l WHERE (:genero IS NULL OR l.genero = :genero) " +
           "AND (:titulo IS NULL OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))")
    List<Libro> findByFiltros(@Param("genero") Genero genero, @Param("titulo") String titulo);
}
