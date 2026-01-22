package com.example.book_advisor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book_advisor.model.Rol;
import com.example.book_advisor.model.Usuario;
import com.example.book_advisor.services.UsuarioService;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioCrudController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping({"", "/"})
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.obtenerTodos());
        return "listUsuarios";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Rol.values());
        model.addAttribute("accion", "nuevo");
        return "formUsuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(
            @RequestParam String nombre,
            @RequestParam String password,
            @RequestParam Rol rol,
            Model model) {
        try {
            usuarioService.registrarUsuario(nombre, password, rol);
            return "redirect:/admin/usuarios/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", new Usuario());
            model.addAttribute("roles", Rol.values());
            model.addAttribute("accion", "nuevo");
            return "formUsuario";
        }
    }

    @GetMapping("/modificar")
    public String mostrarFormularioModificar(@RequestParam Long id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", Rol.values());
        model.addAttribute("accion", "modificar");
        return "formUsuario";
    }

    @PostMapping("/actualizar")
    public String actualizarUsuario(
            @RequestParam Long id,
            @RequestParam String nombre,
            @RequestParam(required = false) String password,
            @RequestParam Rol rol,
            Model model) {
        try {
            usuarioService.actualizar(id, nombre, password, rol);
            return "redirect:/admin/usuarios/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            Usuario usuario = usuarioService.obtenerPorId(id).orElse(new Usuario());
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", Rol.values());
            model.addAttribute("accion", "modificar");
            return "formUsuario";
        }
    }

    @GetMapping("/eliminar")
    public String eliminarUsuario(@RequestParam Long id) {
        usuarioService.eliminar(id);
        return "redirect:/admin/usuarios/";
    }
}
