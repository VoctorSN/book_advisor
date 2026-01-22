package com.example.book_advisor.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book_advisor.model.Genero;
import com.example.book_advisor.services.GeneroService;

@Controller
@RequestMapping("/public/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping({"", "/"})
    public String listarGeneros(Model model) {
        model.addAttribute("generos", generoService.obtenerTodos());
        return "listGeneros";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("genero", new Genero(null, "", new ArrayList<>()));
        return "formGenero";
    }

    @GetMapping("/modificar")
    public String mostrarFormularioModificar(@RequestParam Long id, Model model) {
        model.addAttribute("genero", generoService.obtenerPorId(id));
        return "formGenero";
    }

    @PostMapping("/guardar")
    public String guardarGenero(@RequestParam(required = false) Long id, @RequestParam String nombre) {
        Genero genero;
        if (id != null) {
            genero = generoService.obtenerPorId(id);
            if (genero == null) {
                genero = new Genero();
            }
            genero.setNombre(nombre);
        } else {
            genero = new Genero(null, nombre, new ArrayList<>());
        }
        generoService.guardar(genero);
        return "redirect:/public/generos/";
    }

    @GetMapping("/eliminar")
    public String eliminarGenero(@RequestParam Long id) {
        generoService.eliminar(id);
        return "redirect:/public/generos/";
    }
}
