package com.example.book_advisor.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario registrarUsuario(String nombre) {
        if (existeUsuario(nombre)) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        Usuario usuario = new Usuario(null, nombre);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    @Override
    public boolean existeUsuario(String nombre) {
        return usuarioRepository.existsByNombre(nombre);
    }
}
