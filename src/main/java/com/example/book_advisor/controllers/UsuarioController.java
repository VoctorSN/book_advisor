package com.example.book_advisor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book_advisor.model.Rol;
import com.example.book_advisor.services.UsuarioService;

@Controller
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(
            @RequestParam String nombre,
            @RequestParam String password,
            Model model) {
        try {
            usuarioService.registrarUsuario(nombre, password, Rol.USER);
            return "redirect:/auth/login?registro";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}
