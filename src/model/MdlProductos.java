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
public class MdlProductos {
    private int idproducto;
    private String producto;

    public MdlProductos() {
    }

    public MdlProductos(int idproducto, String producto) {
        this.idproducto = idproducto;
        this.producto = producto;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
