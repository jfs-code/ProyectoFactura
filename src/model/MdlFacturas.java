/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author scorpion
 */
public class MdlFacturas {
    private int idfactura;
    private String numerofactura;    
    private String fecha;    
    private String tipodepago;    
    private String documentocliente;    
    private String nombrecliente;
    private int subtotal;
    private int descuento;
    private int iva;
    private double totaldescuento;
    private double totalimpuesto;
    private double total;
    private ArrayList<MdlDetalles> detallefactura;

    public MdlFacturas() {
    }

    public MdlFacturas(int idfactura, String numerofactura, String fecha, String tipodepago, String documentocliente, String nombrecliente, int subtotal, int descuento, int iva, int totaldescuento, int totalimpuesto, int total) {
        this.idfactura = idfactura;
        this.numerofactura = numerofactura;
        this.fecha = fecha;
        this.tipodepago = tipodepago;
        this.documentocliente = documentocliente;
        this.nombrecliente = nombrecliente;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.totaldescuento = totaldescuento;
        this.totalimpuesto = totalimpuesto;
        this.total = total;
    }

    public int getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(int idfactura) {
        this.idfactura = idfactura;
    }

    public String getNumerofactura() {
        return numerofactura;
    }

    public void setNumerofactura(String numerofactura) {
        this.numerofactura = numerofactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipodepago() {
        return tipodepago;
    }

    public void setTipodepago(String tipodepago) {
        this.tipodepago = tipodepago;
    }

    public String getDocumentocliente() {
        return documentocliente;
    }

    public void setDocumentocliente(String documentocliente) {
        this.documentocliente = documentocliente;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public double getTotaldescuento() {
        return totaldescuento;
    }

    public void setTotaldescuento(double totaldescuento) {
        this.totaldescuento = totaldescuento;
    }

    public double getTotalimpuesto() {
        return totalimpuesto;
    }

    public void setTotalimpuesto(double totalimpuesto) {
        this.totalimpuesto = totalimpuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<MdlDetalles> getDetallefactura() {
        return detallefactura;
    }

    public void setDetallefactura(ArrayList<MdlDetalles> detallefactura) {
        this.detallefactura = detallefactura;
    }
    
    
}
