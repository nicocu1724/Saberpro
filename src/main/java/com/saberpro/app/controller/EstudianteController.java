package com.saberpro.app.controller;

import com.saberpro.app.model.Usuario;
import com.saberpro.app.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    private final UsuarioRepository usuarioRepository;

    public EstudianteController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/portal")
    public String portal(Authentication auth, Model model) {
        String email = auth.getName();
        Usuario estudiante = usuarioRepository.findByEmail(email).orElseThrow();
        
        model.addAttribute("estudiante", estudiante);
        return "estudiante/portal";
    }

  
    
 // ==================== CARGAR PAGO ====================
    @PostMapping("/cargar-pago")
    public String cargarPago(Authentication auth, RedirectAttributes redirectAttributes) {
        try {
            String email = auth.getName();
            Usuario est = usuarioRepository.findByEmail(email).orElseThrow();
            
            est.setPagoCargado(true);
            est.setAprobadoSaberPro(true);
            est.setPuntajeGlobal(250.0);
            
            usuarioRepository.save(est);
            
            redirectAttributes.addFlashAttribute("mensaje", "✅ Pago cargado correctamente. Resultados actualizados.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar el pago");
        }
        return "redirect:/estudiante/portal";
    }
}