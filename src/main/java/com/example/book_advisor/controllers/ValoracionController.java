package com.example.book_advisor.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book_advisor.model.Libro;
import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.services.LibroService;
import com.example.book_advisor.services.UsuarioService;
import com.example.book_advisor.services.ValoracionService;

@Controller
@RequestMapping("/public/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;
    
    @Autowired
    private LibroService libroService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/guardar")
    public String guardarValoracion(
            @RequestParam Long libroId,
            @RequestParam Integer puntuacion,
            @RequestParam(required = false) String redirect) {
        
        Optional<Usuario> usuario = usuarioService.getUsuarioConectado();
        
        if (usuario.isEmpty()) {
            return "redirect:/auth/login";
        }
        
        Libro libro = libroService.getLibroById(libroId);
        valoracionService.guardarValoracion(usuario.get(), libro, puntuacion);
        
        if ("detalle".equals(redirect)) {
            return "redirect:/public/libros/detalle?id=" + libroId;
        }
        
        return "redirect:/public/libros/";
    }
    
    @PostMapping("/eliminar")
    public String eliminarValoracion(
            @RequestParam Long libroId,
            @RequestParam(required = false) String redirect) {
        
        Optional<Usuario> usuario = usuarioService.getUsuarioConectado();
        
        if (usuario.isEmpty()) {
            return "redirect:/auth/login";
        }
        
        Libro libro = libroService.getLibroById(libroId);
        valoracionService.eliminarValoracion(usuario.get(), libro);
        
        if ("detalle".equals(redirect)) {
            return "redirect:/public/libros/detalle?id=" + libroId;
        }
        
        return "redirect:/public/libros/";
    }
}
