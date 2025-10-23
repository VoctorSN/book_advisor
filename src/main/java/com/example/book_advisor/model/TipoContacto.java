package com.example.book_advisor.model;

public enum TipoContacto {
    QUEJA("Queja"), 
    CONSULTA("Consulta"), 
    OTROS("Otros");

    private final String displayName;

    TipoContacto(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
