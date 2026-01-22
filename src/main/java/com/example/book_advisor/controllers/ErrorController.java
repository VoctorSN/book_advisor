package com.example.book_advisor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/accessError")
    public String accessDenied(Model model) {
        model.addAttribute("errorMessage", "No tienes permisos para acceder a este recurso");
        return "accessError";
    }
}
