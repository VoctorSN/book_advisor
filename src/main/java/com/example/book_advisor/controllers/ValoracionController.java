package com.example.book_advisor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book_advisor.model.Libro;
import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.services.LibroService;
import com.example.book_advisor.services.ValoracionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/public/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;
    
    @Autowired
    private LibroService libroService;

    @PostMapping("/guardar")
    public String guardarValoracion(
            @RequestParam Long libroId,
            @RequestParam Integer puntuacion,
            @RequestParam(required = false) String redirect,
            HttpSession session) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/auth/login";
        }
        
        Libro libro = libroService.getLibroById(libroId);
        valoracionService.guardarValoracion(usuario, libro, puntuacion);
        
        if ("detalle".equals(redirect)) {
            return "redirect:/public/libros/detalle?id=" + libroId;
        }
        
        return "redirect:/public/libros/";
    }
}
