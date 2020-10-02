package Clases;

import Clases.Empleado;

public class Visitas {

    private int id;
    private String nombre;
    private int nmaxCli;
    private String puntoPartida;
    private String cursoAcademico;
    private String tematica;
    private float coste;
    private String estado;

    private Empleado empleado;

    public Visitas(int id, String nombre, int nmaxCli, String puntoPartida, String cursoAcademico, String tematica, float coste, String estado, Empleado empleado) {
        this.id = id;
        this.nombre = nombre;
        this.nmaxCli = nmaxCli;
        this.puntoPartida = puntoPartida;
        this.cursoAcademico = cursoAcademico;
        this.tematica = tematica;
        this.coste = coste;
        this.estado = estado;
        this.empleado = empleado;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
