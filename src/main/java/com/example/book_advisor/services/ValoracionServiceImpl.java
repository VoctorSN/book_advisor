package com.example.book_advisor.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.book_advisor.model.Libro;
import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.model.Valoracion;
import com.example.book_advisor.repository.ValoracionRepository;

@Service
public class ValoracionServiceImpl implements ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Override
    public void guardarValoracion(Usuario usuario, Libro libro, Integer puntuacion) {
        Optional<Valoracion> existente = valoracionRepository.findByUsuarioAndLibro(usuario, libro);
        
        if (existente.isPresent()) {
            // Actualizar valoración existente
            Valoracion valoracion = existente.get();
            valoracion.setPuntuacion(puntuacion);
            valoracionRepository.save(valoracion);
        } else {
            // Crear nueva valoración
            Valoracion valoracion = new Valoracion(null, usuario, libro, puntuacion);
            valoracionRepository.save(valoracion);
        }
    }

    @Override
    public Optional<Valoracion> obtenerValoracion(Usuario usuario, Libro libro) {
        return valoracionRepository.findByUsuarioAndLibro(usuario, libro);
    }

    @Override
    public Double calcularMediaLibro(Libro libro) {
        Double media = valoracionRepository.calcularMediaPorLibro(libro);
        return media != null ? media : 0.0;
    }

    @Override
    public Long contarValoraciones(Libro libro) {
        return valoracionRepository.countByLibro(libro);
    }

    @Override
    public void eliminarValoracion(Usuario usuario, Libro libro) {
        Optional<Valoracion> valoracion = valoracionRepository.findByUsuarioAndLibro(usuario, libro);
        valoracion.ifPresent(v -> valoracionRepository.delete(v));
    }

    @Override
    public List<Valoracion> obtenerValoracionesLibro(Libro libro) {
        return valoracionRepository.findByLibro(libro);
    }

    @Override
    public void eliminarValoracion(Long id) {
        valoracionRepository.deleteById(id);
    }
}
