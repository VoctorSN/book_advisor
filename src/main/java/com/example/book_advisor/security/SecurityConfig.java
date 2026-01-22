package com.example.book_advisor.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // Rutas para usuarios con rol USER
    private static final String[] USER_ROUTES = {
        "/public/libros",
        "/public/libros/",
        "/public/libros/detalle",
        "/public/generos",
        "/public/generos/",
        "/public/valoraciones/guardar",
        "/public/contacta",
        "/public/home"
    };

    // Rutas para usuarios con rol MANAGER (CRUD de libros, géneros y valoraciones)
    private static final String[] MANAGER_ROUTES = {
        "/public/libros/nuevo",
        "/public/libros/modificar",
        "/public/libros/actualizar",
        "/public/libros/eliminar",
        "/public/generos/nuevo",
        "/public/generos/modificar",
        "/public/generos/guardar",
        "/public/generos/eliminar"
    };

    // Rutas para usuarios con rol ADMIN (CRUD de usuarios)
    private static final String[] ADMIN_ROUTES = {
        "/admin/usuarios",
        "/admin/usuarios/",
        "/admin/usuarios/nuevo",
        "/admin/usuarios/guardar",
        "/admin/usuarios/modificar",
        "/admin/usuarios/actualizar",
        "/admin/usuarios/eliminar",
        "/public/libros/valoraciones/**",
        "/editar/**",
        "/borrar/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http.headers(
                headersConfigurer -> headersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/", "/public/**").permitAll()
                        // Rutas de autenticación accesibles para todos
                        .requestMatchers("/auth/login", "/auth/registro").permitAll()
                        // Rutas para ADMIN
                        .requestMatchers(ADMIN_ROUTES).hasRole("ADMIN")
                        // Rutas para MANAGER o ADMIN
                        .requestMatchers(MANAGER_ROUTES).hasAnyRole("MANAGER", "ADMIN")
                        // Rutas para USER, MANAGER o ADMIN
                        .requestMatchers(USER_ROUTES).hasAnyRole("USER", "MANAGER", "ADMIN")
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll() // para rutas: /css, /js /images
                        // Consola H2 accesible sin autenticación
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/**").permitAll() // por id, findByName...
                        .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/auth/login") // mapping para mostrar form. de login
                        .loginProcessingUrl("/auth/login") // ruta post del formulario
                        .failureUrl("/auth/login?error") // vuelve a login con mensaje error
                        .defaultSuccessUrl("/public/libros", true).permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/logout") // URL para cerrar sesión (POST)
                        .logoutSuccessUrl("/auth/login?logout").permitAll()) // u otra url
                // .csrf(csrf -> csrf.disable())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**"))
                .httpBasic(Customizer.withDefaults());
        http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/accessError"));
        return http.build();
    }
}
