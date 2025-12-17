package com.example.book_advisor.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.book_advisor.model.Genero;
import com.example.book_advisor.model.Idioma;
import com.example.book_advisor.model.Libro;

@Service
public class LibrosServiceImpl implements LibrosService{
    
    private List<Libro> repositorio = new ArrayList<>();
    private Optional<Genero> filtroGenero = Optional.empty();
    private Optional<String> filtroTitulo = Optional.empty();

    public LibrosServiceImpl() {
        // Añadir algunos Libros de ejemplo
        repositorio.add(new Libro(1, "El Señor de los Anillos", "J.R.R. Tolkien", Idioma.ESPANOL, 
            "Una épica aventura sobre un anillo que puede dominar el mundo", "2024-01-15", 1954, Genero.FANTASIA, "logo.jpg"));
        repositorio.add(new Libro(2, "1984", "George Orwell", Idioma.ESPANOL, 
            "Una distopía sobre un futuro totalitario y vigilancia extrema", "2024-02-10", 1949, Genero.CIENCIA_FICCION, "logo.jpg"));
        repositorio.add(new Libro(3, "Cien Años de Soledad", "Gabriel García Márquez", Idioma.ESPANOL, 
            "La historia de la familia Buendía en el pueblo de Macondo", "2024-03-05", 1967, Genero.DRAMA, "logo.jpg"));
        repositorio.add(new Libro(4, "Harry Potter y la Piedra Filosofal", "J.K. Rowling", Idioma.ESPANOL, 
            "Un niño descubre que es mago y asiste a Hogwarts", "2024-04-20", 1997, Genero.FANTASIA, "logo.jpg"));
        repositorio.add(new Libro(5, "El Código Da Vinci", "Dan Brown", Idioma.ESPANOL, 
            "Un thriller sobre conspiraciones religiosas y misterios antiguos", "2024-05-12", 2003, Genero.THRILLER, "logo.jpg"));
        repositorio.add(new Libro(6, "Orgullo y Prejuicio", "Jane Austen", Idioma.ESPANOL, 
            "Una historia de amor y malentendidos en la Inglaterra del siglo XIX", "2024-06-08", 1813, Genero.ROMANCE, "logo.jpg"));
        repositorio.add(new Libro(7, "It", "Stephen King", Idioma.INGLES, 
            "Un grupo de niños enfrenta a una entidad malévola en su pueblo", "2024-07-15", 1986, Genero.TERROR, "logo.jpg"));
        repositorio.add(new Libro(8, "Sherlock Holmes", "Arthur Conan Doyle", Idioma.INGLES, 
            "Las aventuras del detective más famoso de la literatura", "2024-08-22", 1887, Genero.MISTERIO, "logo.jpg"));
        repositorio.add(new Libro(9, "Los Juegos del Hambre", "Suzanne Collins", Idioma.ESPANOL, 
            "Una joven lucha por sobrevivir en un reality show mortal", "2024-09-10", 2008, Genero.AVENTURA, "logo.jpg"));
        repositorio.add(new Libro(10, "Don Quijote de la Mancha", "Miguel de Cervantes", Idioma.ESPANOL, 
            "Las aventuras cómicas de un hidalgo que cree ser un caballero andante", "2024-10-05", 1605, Genero.COMEDIA, "logo.jpg"));
    }

    public List<Libro> getLibros() {
        return repositorio;
    }

    public List<Libro> getLibrosOrdenados() {
        return repositorio.stream()
            .filter(c -> filtroGenero.isEmpty() || c.getGenero() == filtroGenero.get())
            .filter(c -> filtroTitulo.isEmpty() || 
                    c.getTitulo().toLowerCase().contains(filtroTitulo.get().toLowerCase()))
            .toList();
    }

    public void setFiltroGenero(Optional<Genero> filtro) {
        this.filtroGenero = filtro;
    }

    public Optional<Genero> getFiltroGenero() {
        return filtroGenero;
    }

    public void setFiltroTitulo(Optional<String> filtro) {
        this.filtroTitulo = filtro;
    }

    public Optional<String> getFiltroTitulo() {
        return filtroTitulo;
    }

    @Override
    public Libro getLibroById(int id) {
        return repositorio.stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public void eliminarLibro(int id) {
        repositorio.removeIf(c -> c.getId() == id);
    }

    @Override
    public void actualizarLibro(Libro Libro) {
        for (int i = 0; i < repositorio.size(); i++) {
            if (repositorio.get(i).getId() == Libro.getId()) {
                repositorio.set(i, Libro);
                break;
            }
        }
    }

    @Override
    public void agregarLibro(Libro libro) {
        // Generar un nuevo ID automáticamente
        int nuevoId = repositorio.stream()
            .mapToInt(l -> l.getId())
            .max()
            .orElse(0) + 1;
        libro.setId(nuevoId);
        
        // Generar fecha de alta automáticamente
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        libro.setFechaAlta(fechaActual.format(formatter));
        
        repositorio.add(libro);
    }
}
