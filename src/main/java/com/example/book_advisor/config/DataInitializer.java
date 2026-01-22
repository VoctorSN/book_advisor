package com.example.book_advisor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.book_advisor.model.Rol;
import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario ADMIN si no existe
        if (!usuarioRepository.existsByNombre("admin")) {
            Usuario admin = new Usuario();
            admin.setNombre("admin");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);
            System.out.println("Usuario ADMIN 'admin' creado exitosamente");
        }
        // Crear usuario USER si no existe
        if (!usuarioRepository.existsByNombre("user")) {
            Usuario user = new Usuario();
            user.setNombre("user");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRol(Rol.ADMIN);
            usuarioRepository.save(user);
            System.out.println("Usuario USER 'user' creado exitosamente");
        }
    }
}
