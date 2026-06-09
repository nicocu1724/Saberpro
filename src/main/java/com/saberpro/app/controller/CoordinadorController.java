package com.saberpro.app.controller;

import com.saberpro.app.model.Usuario;
import com.saberpro.app.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.saberpro.app.util.ExcelImporter;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorController {

    private final UsuarioRepository usuarioRepository;

    public CoordinadorController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ==================== LISTAR ====================
    @GetMapping("/gestion-alumnos")
    public String gestionAlumnos(Model model) {
        // Filtrar solo estudiantes
        List<Usuario> estudiantes = usuarioRepository.findAll().stream()
            .filter(u -> "ESTUDIANTE".equals(u.getRol()))
            .toList();
        
        model.addAttribute("estudiantes", estudiantes);
        return "coordinador/gestion-alumnos";
    }

    // ==================== NUEVO ====================
    @GetMapping("/nuevo-estudiante")
    public String nuevoEstudianteForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "coordinador/nuevo-estudiante";
    }

    @PostMapping("/guardar-estudiante")
    public String guardarEstudiante(@ModelAttribute Usuario usuario) {
        usuario.setRol("ESTUDIANTE");
        usuarioRepository.save(usuario);
        return "redirect:/coordinador/gestion-alumnos";
    }

    // ==================== EDITAR ====================
    @GetMapping("/editar/{id}")
    public String editarEstudiante(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "coordinador/editar-estudiante";
    }

    @PostMapping("/actualizar-estudiante")
    public String actualizarEstudiante(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/coordinador/gestion-alumnos";
    }

    // ==================== ELIMINAR ====================
    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/coordinador/gestion-alumnos";
    }
    
    @GetMapping("/informe-general")
    public String informeGeneral(Model model) {
        // Solo estudiantes
        var estudiantes = usuarioRepository.findAll().stream()
                .filter(u -> "ESTUDIANTE".equals(u.getRol()))
                .toList();
        model.addAttribute("estudiantes", estudiantes);
        return "coordinador/informe-general";
    }

    @GetMapping("/informe-detallado")
    public String informeDetallado(Model model) {
        // Solo estudiantes
        var estudiantes = usuarioRepository.findAll().stream()
                .filter(u -> "ESTUDIANTE".equals(u.getRol()))
                .toList();
        model.addAttribute("estudiantes", estudiantes);
        return "coordinador/informe-detallado";
    }
    
    @PostMapping("/importar-excel")
    public String importarExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Seleccione un archivo Excel");
                return "redirect:/coordinador/gestion-alumnos";
            }

            List<Usuario> nuevos = ExcelImporter.importarEstudiantes(file);

            int count = 0;
            for (Usuario est : nuevos) {
                if (usuarioRepository.findByEmail(est.getEmail()).isEmpty()) {
                    usuarioRepository.save(est);
                    count++;
                }
            }

            redirectAttributes.addFlashAttribute("mensaje", "✅ " + count + " estudiantes importados correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al importar: " + e.getMessage());
        }
        return "redirect:/coordinador/gestion-alumnos";
    }
}