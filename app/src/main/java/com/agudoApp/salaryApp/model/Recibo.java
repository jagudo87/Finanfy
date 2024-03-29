package com.agudoApp.salaryApp.model;

public class Recibo implements Comparable<Object> {
    String id;
    String descripcion = "";
    String cantidad = "";
    String idCategoria = "";
    String idSubcategoria = "";
    String descCategoria = "";
    String descSubcategoria = "";
    String fechaIni = "";
    String fechaFin = "";
    boolean tarjeta = false;
    String idTarjeta = "";
    int idIcon;
    int tipo;
    int nVeces;

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(String idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(boolean tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getnVeces() {
        return nVeces;
    }

    public void setnVeces(int nVeces) {
        this.nVeces = nVeces;
    }

    public String getDescCategoria() {
        return descCategoria;
    }

    public void setDescCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
    }

    public String getDescSubcategoria() {
        return descSubcategoria;
    }

    public void setDescSubcategoria(String descSubcategoria) {
        this.descSubcategoria = descSubcategoria;
    }

    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }

    @Override
    public int compareTo(Object o) {
        Recibo rec = (Recibo) o;
        return this.descripcion.compareToIgnoreCase(rec.toString());
    }
}
