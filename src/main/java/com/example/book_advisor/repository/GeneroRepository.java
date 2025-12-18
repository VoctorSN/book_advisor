package com.example.book_advisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book_advisor.model.Genero;

@Repository
public interface GeneroRepository extends  JpaRepository<Genero, Long>{
    
}
