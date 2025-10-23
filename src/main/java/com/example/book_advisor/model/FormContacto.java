package com.example.book_advisor.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormContacto {
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String comentario;
    @NotEmpty @Email
    private String email;
    @NotNull
    private TipoContacto tipoContacto;
    @AssertTrue(message="Tienes que aceptar los terminos y condiciones")
    private boolean acceptsConditions;

    @Override
    public String toString() {
        return "nombre=" + nombre + ",\n comentario=" + comentario + ",\n email=" + email + ",\n tipoContacto="
                + tipoContacto + ",\n acceptsConditions=" + acceptsConditions;
    }

    
}
