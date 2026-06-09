package com.saberpro.app.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String documento;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String rol;

    private String programa;
    private String semestre;
    private Double puntajeGlobal;
    private boolean pagoCargado = false;
    private boolean aprobadoSaberPro = false;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities = new HashSet<>();

    public Usuario() {}

    public void setRol(String rol) {
        this.rol = rol;
        this.authorities.add("ROLE_" + rol);
    }

    // Getters y Setters completos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }

    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public Double getPuntajeGlobal() { return puntajeGlobal; }
    public void setPuntajeGlobal(Double puntajeGlobal) { this.puntajeGlobal = puntajeGlobal; }

    public boolean isPagoCargado() { return pagoCargado; }
    public void setPagoCargado(boolean pagoCargado) { this.pagoCargado = pagoCargado; }

    public boolean isAprobadoSaberPro() { return aprobadoSaberPro; }
    public void setAprobadoSaberPro(boolean aprobadoSaberPro) { this.aprobadoSaberPro = aprobadoSaberPro; }

    public Set<String> getAuthorities() { return authorities; }
}