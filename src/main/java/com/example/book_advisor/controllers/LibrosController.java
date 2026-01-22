package com.example.book_advisor.controllers; 

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.book_advisor.model.Genero;
import com.example.book_advisor.model.Idioma;
import com.example.book_advisor.model.Libro;
import com.example.book_advisor.services.LibroService;
import com.example.book_advisor.services.UsuarioService;
import com.example.book_advisor.services.ValoracionService;

@Controller
@RequestMapping("/public/libros")
public class LibrosController {

    @Autowired
    private LibroService service;
    
    @Autowired
    private ValoracionService valoracionService;
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping({"", "/"})
    public String listarLibros(
            @RequestParam(required = false) Long filtroGenero,
            @RequestParam(required = false) String filtroTitulo,
            Model model) {
        
        // Establecer el filtro de temática en el servicio
        if (filtroGenero != null) {
            Genero genero = service.getGeneros().stream()
                    .filter(g -> g.getId().equals(filtroGenero))
                    .findFirst()
                    .orElse(null);
            service.setFiltroGenero(Optional.ofNullable(genero));
        } else {
            service.setFiltroGenero(Optional.empty());
        }
        
        // Establecer el filtro de nombre en el servicio
        if (filtroTitulo != null && !filtroTitulo.trim().isEmpty()) {
            service.setFiltroTitulo(Optional.of(filtroTitulo));
        } else {
            service.setFiltroTitulo(Optional.empty());
        }
        
        model.addAttribute("libros", service.convertirLibrosADTO(service.getLibrosOrdenados()));
        model.addAttribute("tematicas", service.getGeneros());
        model.addAttribute("filtroActual", service.getFiltroGenero().orElse(null));
        model.addAttribute("filtroNombreActual", service.getFiltroTitulo().orElse(""));
        return "listLibros";
    }
    
    @GetMapping("/eliminar")
    public String eliminarLibro(@RequestParam Long id) {
        service.eliminarLibro(id);
        return "redirect:/public/libros/";
    }

    @GetMapping("/detalle")
    public String verDetalle(@RequestParam Long id, Model model) {
        Libro libro = service.getLibroById(id);
        
        model.addAttribute("libro", libro);
        model.addAttribute("mediaValoracion", valoracionService.calcularMediaLibro(libro));
        model.addAttribute("numeroVotos", valoracionService.contarValoraciones(libro));
        
        usuarioService.getUsuarioConectado().ifPresent(usuario -> {
            var valoracionUsuario = valoracionService.obtenerValoracion(usuario, libro);
            model.addAttribute("valoracionUsuario", valoracionUsuario.orElse(null));
        });
        
        return "detalleLibro";
    }

    @GetMapping("/modificar")
    public String mostrarFormularioModificar(@RequestParam Long id, Model model) {
        Libro libro = service.getLibroById(id);
        model.addAttribute("libro", libro);
        model.addAttribute("tematicas", service.getGeneros());
        model.addAttribute("idiomas", Idioma.values());
        return "formView";
    }

    @PostMapping("/actualizar")
    public String guardarLibro(Libro libro, @RequestParam("imagenFile") MultipartFile imagenFile) {
        // Si es una actualización, preservar la imagen existente si no se sube una nueva
        if(libro.getId() != 0 && imagenFile.isEmpty()) {
            Libro libroExistente = service.getLibroById(libro.getId());
            if(libroExistente != null && libroExistente.getImagen() != null) {
                libro.setImagen(libroExistente.getImagen());
            }
        }
        
        // Procesar la imagen si se subió un archivo
        if (!imagenFile.isEmpty()) {
            try {
                // Generar nombre único para la imagen
                String nombreOriginal = imagenFile.getOriginalFilename();
                String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
                String nombreUnico = UUID.randomUUID().toString() + extension;
                
                // Obtener los bytes del archivo una sola vez
                byte[] bytes = imagenFile.getBytes();
                
                // Ruta donde se guardará la imagen (en target para que se vea inmediatamente)
                Path directorioImagenes = Paths.get("target/classes/static/imgs");
                if (!Files.exists(directorioImagenes)) {
                    Files.createDirectories(directorioImagenes);
                }
                
                // También guardar en src para persistencia
                Path directorioImagenesSrc = Paths.get("src/main/resources/static/imgs");
                if (!Files.exists(directorioImagenesSrc)) {
                    Files.createDirectories(directorioImagenesSrc);
                }
                
                // Guardar el archivo en ambas ubicaciones
                Path rutaArchivo = directorioImagenes.resolve(nombreUnico);
                Path rutaArchivoSrc = directorioImagenesSrc.resolve(nombreUnico);
                Files.write(rutaArchivo, bytes);
                Files.write(rutaArchivoSrc, bytes);
                
                // Asignar el nombre del archivo al libro
                libro.setImagen(nombreUnico);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Si el ID es 0 o null, es un libro nuevo
        if(libro.getId() == 0){
            service.agregarLibro(libro);
        } else {
            // Si tiene ID, es una actualización
            service.actualizarLibro(libro);
        }
        return "redirect:/public/libros/";
    }

    @GetMapping("/nuevo")
    public String nuevoLibro(Model model) {
        // Crear un libro vacío con ID 0 para indicar que es nuevo
        Libro libroNuevo = new Libro(null, "", "", null, "", "", 0, null, null);
        model.addAttribute("libro", libroNuevo);
        model.addAttribute("tematicas", service.getGeneros());
        model.addAttribute("idiomas", Idioma.values());
        return "formView";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/valoraciones")
    public String verValoraciones(@RequestParam Long id, Model model) {
        Libro libro = service.getLibroById(id);
        model.addAttribute("libro", libro);
        model.addAttribute("valoraciones", valoracionService.obtenerValoracionesLibro(libro));
        return "valoracionesLibro";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/valoraciones/eliminar")
    public String eliminarValoracion(@RequestParam Long valoracionId, @RequestParam Long libroId) {
        valoracionService.eliminarValoracion(valoracionId);
        return "redirect:/public/libros/valoraciones?id=" + libroId;
    }
}
