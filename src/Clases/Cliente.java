package Clases;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String dni;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private String profesion;
    private String estado;

    private final List<Visita> visitas = new ArrayList<>();

    public Cliente(String dni, String nombre, String apellidos, String fechaNacimiento, String profesion, String estado, Visita visita) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.profesion = profesion;
        this.estado = estado;
        this.visitas.add(visita);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Visita> getVisitas() {
        return visitas;
    }
}
