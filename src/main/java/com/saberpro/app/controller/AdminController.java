package com.saberpro.app.controller;

import com.saberpro.app.model.Usuario;
import com.saberpro.app.model.Facultad;
import com.saberpro.app.repository.UsuarioRepository;
import com.saberpro.app.repository.FacultadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final FacultadRepository facultadRepository;

    public AdminController(UsuarioRepository usuarioRepository, FacultadRepository facultadRepository) {
        this.usuarioRepository = usuarioRepository;
        this.facultadRepository = facultadRepository;
    }

    @GetMapping("/panel")
    public String panelAdmin(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/panel";
    }

    // ==================== DOCENTES ====================
    @GetMapping("/docentes")
    public String listarDocentes(Model model) {
        var docentes = usuarioRepository.findAll().stream()
                .filter(u -> "DOCENTE".equals(u.getRol()))
                .toList();
        model.addAttribute("docentes", docentes);
        return "admin/docentes";
    }

    @GetMapping("/nuevo-docente")
    public String nuevoDocente(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/nuevo-docente";
    }
    
    @GetMapping("/editar-docente/{id}")
    public String editarDocente(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id).orElseThrow());
        return "admin/editar-docente";
    }

    @PostMapping("/guardar-docente")
    public String guardarDocente(@ModelAttribute Usuario usuario) {
        usuario.setRol("DOCENTE");
        usuarioRepository.save(usuario);
        return "redirect:/admin/docentes";
    }

    // ==================== FACULTADES ====================
    @GetMapping("/facultades")
    public String listarFacultades(Model model) {
        model.addAttribute("facultades", facultadRepository.findAll());
        return "admin/facultades";
    }

    @GetMapping("/nueva-facultad")
    public String nuevaFacultad(Model model) {
        model.addAttribute("facultad", new Facultad());
        return "admin/nueva-facultad";
    }

    @PostMapping("/guardar-facultad")
    public String guardarFacultad(@ModelAttribute Facultad facultad) {
        facultadRepository.save(facultad);
        return "redirect:/admin/facultades";
    }

    @GetMapping("/editar-facultad/{id}")
    public String editarFacultad(@PathVariable Long id, Model model) {
        model.addAttribute("facultad", facultadRepository.findById(id).orElseThrow());
        return "admin/editar-facultad";
    }

    @GetMapping("/eliminar-facultad/{id}")
    public String eliminarFacultad(@PathVariable Long id) {
        facultadRepository.deleteById(id);
        return "redirect:/admin/facultades";
    }
    
    @GetMapping("/estudiantes")
    public String listarTodosEstudiantes(Model model) {
        var estudiantes = usuarioRepository.findAll().stream()
                .filter(u -> "ESTUDIANTE".equals(u.getRol()))
                .toList();
        model.addAttribute("estudiantes", estudiantes);
        return "admin/estudiantes";
    }
}