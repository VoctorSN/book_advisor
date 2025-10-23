package com.example.book_advisor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.book_advisor.model.FormContacto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ContactaServiceImpl implements ContactaService {
    @Autowired
    private JavaMailSender sender;

    @Override
    public boolean sendEmailWithFormInfo(FormContacto formContacto) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo("victorsancheznogueira@gmail.com");
            helper.setText(formContacto.toString(), true);
            helper.setSubject("Mensaje De Contacto BookAdvisor");
            sender.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
