package com.saberpro.app.config;

import com.saberpro.app.model.Usuario;
import com.saberpro.app.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String encoded = passwordEncoder.encode("admin123");

        usuarioRepository.deleteAll(); // Limpia para evitar duplicados

        // ADMIN y COORDINADOR
        Usuario admin = createUser("1000000000", "Administrador Principal", "admin@uts.edu.co", "ADMIN");
        Usuario coord = createUser("2000000000", "María López", "coordinador@uts.edu.co", "COORDINADOR");
        usuarioRepository.save(admin);
        usuarioRepository.save(coord);

        // 10 ESTUDIANTES DEL EXCEL
        Usuario[] estudiantes = {
            createEstudiante("BARBOSA", "barbosa@uts.edu.co", 200.0),
            createEstudiante("QUINTERO", "quintero@uts.edu.co", 165.0),
            createEstudiante("PARRA", "parra@uts.edu.co", 164.0),
            createEstudiante("ANAYA", "anaya@uts.edu.co", 160.0),
            createEstudiante("FLOR", "flor@uts.edu.co", 160.0),
            createEstudiante("GARCIA", "garcia1@uts.edu.co", 157.0),
            createEstudiante("MANOSALVA", "manosalva@uts.edu.co", 153.0),
            createEstudiante("MENDOZA", "mendoza@uts.edu.co", 151.0),
            createEstudiante("BELTRAN", "beltran@uts.edu.co", 150.0),
            createEstudiante("SANTAMARIA", "santamaria@uts.edu.co", 150.0)
        };

        for (Usuario est : estudiantes) {
            if (usuarioRepository.findByEmail(est.getEmail()).isEmpty()) {
                usuarioRepository.save(est);
            }
        }

        System.out.println("✅ 10 estudiantes del Excel + Admin y Coordinador creados");
    }

    private Usuario createUser(String doc, String nombre, String email, String rol) {
        Usuario u = new Usuario();
        u.setDocumento(doc);
        u.setNombreCompleto(nombre);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode("admin123"));
        u.setRol(rol);
        return u;
    }

    private Usuario createEstudiante(String nombre, String email, double puntaje) {
        Usuario est = createUser("1" + (int)(Math.random()*99999999), nombre, email, "ESTUDIANTE");
        est.setPrograma("Ingeniería de Sistemas");
        est.setSemestre("10");
        est.setPuntajeGlobal(puntaje);
        return est;
    }
}