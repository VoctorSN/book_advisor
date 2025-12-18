package com.example.book_advisor.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.book_advisor.DTO.LibroDTO;
import com.example.book_advisor.model.Genero;
import com.example.book_advisor.model.Idioma;
import com.example.book_advisor.model.Libro;
import com.example.book_advisor.repository.GeneroRepository;
import com.example.book_advisor.repository.LibroRepository;

import jakarta.annotation.PostConstruct;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Optional<Genero> filtroGenero = Optional.empty();
    private Optional<String> filtroTitulo = Optional.empty();

    @PostConstruct
    public void innit() {

        Genero fantasia = new Genero(null, "Fantasia", new ArrayList<>());
        Genero drama = new Genero(null, "Drama", new ArrayList<>());
        Genero cienciaFiccion = new Genero(null, "Ciencia Ficcion", new ArrayList<>());
        Genero thriller = new Genero(null, "Thriller", new ArrayList<>());
        Genero romance = new Genero(null, "Romance", new ArrayList<>());
        Genero terror = new Genero(null, "Terror", new ArrayList<>());
        Genero misterio = new Genero(null, "Misterio", new ArrayList<>());
        Genero aventura = new Genero(null, "Aventura", new ArrayList<>());
        Genero comedia = new Genero(null, "Comedia", new ArrayList<>());

        // Guardar géneros PRIMERO para que tengan ID
        fantasia = generoRepository.save(fantasia);
        drama = generoRepository.save(drama);
        cienciaFiccion = generoRepository.save(cienciaFiccion);
        thriller = generoRepository.save(thriller);
        romance = generoRepository.save(romance);
        terror = generoRepository.save(terror);
        misterio = generoRepository.save(misterio);
        aventura = generoRepository.save(aventura);
        comedia = generoRepository.save(comedia);

        
        // Añadir algunos Libros de ejemplo
        Libro libro1 = new Libro(null, "El Señor de los Anillos", "J.R.R. Tolkien", Idioma.ESPANOL,
        "Una épica aventura sobre un anillo que puede dominar el mundo", "2024-01-15", 1954,
        fantasia, "logo.jpg");
        fantasia.getLibros().add(libro1);
        Libro libro2 = new Libro(null, "1984", "George Orwell", Idioma.ESPANOL,
                "Una distopía sobre un futuro totalitario y vigilancia extrema", "2024-02-10", 1949,
                cienciaFiccion, "logo.jpg");
        cienciaFiccion.getLibros().add(libro2);
        Libro libro3 = new Libro(null, "Cien Años de Soledad", "Gabriel García Márquez", Idioma.ESPANOL,
        "La historia de la familia Buendía en el pueblo de Macondo", "2024-03-05", 1967, drama, "logo.jpg");
        drama.getLibros().add(libro3);
        Libro libro4 = new Libro(null, "Harry Potter y la Piedra Filosofal", "J.K. Rowling", Idioma.ESPANOL,
                "Un niño descubre que es mago y asiste a Hogwarts", "2024-04-20", 1997, fantasia, "logo.jpg");
        fantasia.getLibros().add(libro4);
        Libro libro5 = new Libro(null, "El Código Da Vinci", "Dan Brown", Idioma.ESPANOL,
        "Un thriller sobre conspiraciones religiosas y misterios antiguos", "2024-05-12", 2003,
                thriller, "logo.jpg");
                thriller.getLibros().add(libro5);
        Libro libro6 = new Libro(null, "Orgullo y Prejuicio", "Jane Austen", Idioma.ESPANOL,
                "Una historia de amor y malentendidos en la Inglaterra del siglo XIX", "2024-06-08", 1813,
                romance, "logo.jpg");
        romance.getLibros().add(libro6);
        Libro libro7 = new Libro(null, "It", "Stephen King", Idioma.INGLES,
                "Un grupo de niños enfrenta a una entidad malévola en su pueblo", "2024-07-15", 1986,
                terror, "logo.jpg");
        terror.getLibros().add(libro7);
        Libro libro8 = new Libro(null, "Sherlock Holmes", "Arthur Conan Doyle", Idioma.INGLES,
                "Las aventuras del detective más famoso de la literatura", "2024-08-22", 1887, misterio, "logo.jpg");
                misterio.getLibros().add(libro8);
        Libro libro9 = new Libro(null, "Los Juegos del Hambre", "Suzanne Collins", Idioma.ESPANOL,
                "Una joven lucha por sobrevivir en un reality show mortal", "2024-09-10", 2008, aventura, "logo.jpg");
                aventura.getLibros().add(libro9);
        Libro libro10 = new Libro(null, "Don Quijote de la Mancha", "Miguel de Cervantes", Idioma.ESPANOL,
                "Las aventuras cómicas de un hidalgo que cree ser un caballero andante", "2024-10-05", 1605,
                comedia, "logo.jpg");
        comedia.getLibros().add(libro10);

        libroRepository.save(libro1);
        libroRepository.save(libro2);
        libroRepository.save(libro3);
        libroRepository.save(libro4);
        libroRepository.save(libro5);
        libroRepository.save(libro6);
        libroRepository.save(libro7);
        libroRepository.save(libro8);
        libroRepository.save(libro9);
        libroRepository.save(libro10);
    }
    
    public List<Libro> getLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> getLibrosOrdenados() {
        return libroRepository.findByFiltros(
                filtroGenero.orElse(null), 
                filtroTitulo.orElse(null)
        );
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
    public Libro getLibroById(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }

    @Override
    public void actualizarLibro(Libro libro) {
        libroRepository.save(libro);
    }

    @Override
    public void agregarLibro(Libro libro) {

        // Generar fecha de alta automáticamente
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        libro.setFechaAlta(fechaActual.format(formatter));

        libroRepository.save(libro);
    }

    @Override
    public List<Genero> getGeneros() {
        return generoRepository.findAll();
    }

    public List<LibroDTO> convertirLibrosADTO(List<Libro> libros) {
        return libros.stream()
                .map(libro -> modelMapper.map(libro, LibroDTO.class))
                .toList();
    }
}
