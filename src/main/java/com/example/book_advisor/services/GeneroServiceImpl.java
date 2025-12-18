package com.example.book_advisor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.book_advisor.model.Genero;
import com.example.book_advisor.repository.GeneroRepository;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public List<Genero> obtenerTodos() {
        return generoRepository.findAll();
    }

    @Override
    public Genero obtenerPorId(Long id) {
        return generoRepository.findById(id).orElse(null);
    }

    @Override
    public Genero guardar(Genero genero) {
        return generoRepository.save(genero);
    }

    @Override
    public void eliminar(Long id) {
        generoRepository.deleteById(id);
    }
}
