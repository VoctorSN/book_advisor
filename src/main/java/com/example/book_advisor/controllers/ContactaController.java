package com.example.book_advisor.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.book_advisor.model.FormContacto;
import com.example.book_advisor.services.ContactaServiceImpl;

import jakarta.validation.Valid;

@Controller
public class ContactaController {

    @Autowired
    private ContactaServiceImpl service;
    Optional<String> message = Optional.empty();

    @GetMapping("/public/contacta")
    public String getForm(Model model) {
        model.addAttribute("formContacto", new FormContacto());
        model.addAttribute("message", message);
        message = Optional.empty();
        return "contacta";
    }

    @PostMapping("/public/contacta")
    public String postForm(@Valid FormContacto formContacto, BindingResult bg) {
        if (bg.hasErrors()) {
            return "contacta";
        }
        if (service.sendEmailWithFormInfo(formContacto)) {
            message = Optional.of("Se envió correctamente");
        }else {
            message = Optional.of("Ha ocurrido un error enviando el email");
        }
        return "redirect:/public/contacta";
    }

}
