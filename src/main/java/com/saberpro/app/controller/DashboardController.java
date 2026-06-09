package com.saberpro.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String rol = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        model.addAttribute("rol", rol);
        model.addAttribute("nombre", auth.getName());
        return "dashboard";
    }
}