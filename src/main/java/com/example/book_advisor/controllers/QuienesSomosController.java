package com.example.book_advisor.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class QuienesSomosController {

    @GetMapping("/")
    public String getMethodName() {
        return "redirect:/public/home";
    }
    
    
    @GetMapping("/public/home")
    public String getMethodName(Model model) {
        model.addAttribute("anoActual",LocalDate.now().getYear());
        return "quienes_somos";
    }
    
}
