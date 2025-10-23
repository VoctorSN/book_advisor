package com.example.book_advisor.services;

import com.example.book_advisor.model.FormContacto;

public interface ContactaService {
    public boolean sendEmailWithFormInfo(FormContacto formContacto);
}
