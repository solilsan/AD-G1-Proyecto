package Clases;

import java.util.ArrayList;
import java.util.List;

public class Visita {

    private Integer id;
    private String nombre;
    private Integer nmaxCli;
    private String puntoPartida;
    private String cursoAcademico;
    private String tematica;
    private Float coste;
    private String estado;
    private String fecha_hora;

    //Una visita puede tener varios clientes o ninguno
    private final List<Cliente> clientes = new ArrayList<>();

    //Una visita tiene un empleado
    private Empleado empleado;

    public Visita(Integer id, String nombre, Integer nmaxCli, String puntoPartida, String cursoAcademico, String tematica, Float coste, String estado,String fecha_hora, Empleado empleado) {
        this.id = id;
        this.nombre = nombre;
        this.nmaxCli = nmaxCli;
        this.puntoPartida = puntoPartida;
        this.cursoAcademico = cursoAcademico;
        this.tematica = tematica;
        this.coste = coste;
        this.estado = estado;
        this.fecha_hora = fecha_hora;
        this.empleado = empleado;
    }

    public Visita() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNmaxCli() {
        return nmaxCli;
    }

    public void setNmaxCli(int nmaxCli) {
        this.nmaxCli = nmaxCli;
    }

    public String getPuntoPartida() {
        return puntoPartida;
    }

    public void setPuntoPartida(String puntoPartida) {
        this.puntoPartida = puntoPartida;
    }

    public String getCursoAcademico() {
        return cursoAcademico;
    }

    public void setCursoAcademico(String cursoAcademico) {
        this.cursoAcademico = cursoAcademico;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public float getCoste() {
        return coste;
    }

    public void setCoste(float coste) {
        this.coste = coste;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
