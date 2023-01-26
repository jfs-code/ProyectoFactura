/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author scorpion
 */
public class MdlDetalles {
    private int iddetalle;
    private int idfactura;
    private MdlProductos producto;
    private int cantidad;
    private int preciounitario;

    public MdlDetalles() {
    }

    public MdlDetalles(int iddetalle, int idfactura, MdlProductos producto, int cantidad, int preciounitario) {
        this.iddetalle = iddetalle;
        this.idfactura = idfactura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.preciounitario = preciounitario;
    }

    public int getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(int iddetalle) {
        this.iddetalle = iddetalle;
    }

    public int getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(int idfactura) {
        this.idfactura = idfactura;
    }

    public MdlProductos getProducto() {
        return producto;
    }

    public void setProducto(MdlProductos producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(int preciounitario) {
        this.preciounitario = preciounitario;
    }
}
