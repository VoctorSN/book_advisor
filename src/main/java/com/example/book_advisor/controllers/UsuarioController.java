package com.example.book_advisor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String nombre, HttpSession session, Model model) {
        var usuarioOpt = usuarioService.buscarPorNombre(nombre);
        
        if (usuarioOpt.isPresent()) {
            session.setAttribute("usuario", usuarioOpt.get());
            return "redirect:/public/libros/";
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "login";
        }
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String nombre, HttpSession session, Model model) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(nombre);
            session.setAttribute("usuario", usuario);
            return "redirect:/public/libros/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
