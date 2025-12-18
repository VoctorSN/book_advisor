package com.example.book_advisor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.book_advisor.model.Libro;
import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.model.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    
    Optional<Valoracion> findByUsuarioAndLibro(Usuario usuario, Libro libro);
    
    List<Valoracion> findByLibro(Libro libro);
    
    @Query("SELECT AVG(v.puntuacion) FROM Valoracion v WHERE v.libro = :libro")
    Double calcularMediaPorLibro(@Param("libro") Libro libro);
    
    Long countByLibro(Libro libro);
}
