package com.example.book_advisor.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.book_advisor.model.Rol;
import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(String nombre, String password, Rol rol) {
        if (existeUsuario(nombre)) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(rol != null ? rol : Rol.USER);
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

    @Override
    public Optional<Usuario> getUsuarioConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || authentication instanceof AnonymousAuthenticationToken || authentication.getName() == null
                ? Optional.empty()
                : usuarioRepository.findByNombre(authentication.getName());
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario actualizar(Long id, String nombre, String password, Rol rol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Verificar si el nombre ya existe en otro usuario
        if (!usuario.getNombre().equals(nombre) && existeUsuario(nombre)) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        usuario.setNombre(nombre);
        if (password != null && !password.isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }
        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
