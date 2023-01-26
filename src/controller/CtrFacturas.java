package controller;

import conection.Conexion;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.MdlFacturas;

/**
 *
 * @author scorpion
 */
public class CtrFacturas {

    public ArrayList<MdlFacturas> consulta() {
        ArrayList<MdlFacturas> listafacturas = new ArrayList();
        Conexion conectar = new Conexion();
        String sql = "SELECT * FROM facturas";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while (rs.next()) {
                MdlFacturas factura = new MdlFacturas();
                factura.setDescuento(rs.getInt("descuento"));
                factura.setSubtotal(rs.getInt("subtotal"));
                factura.setIva(rs.getInt("iva"));
                factura.setTotaldescuento(rs.getDouble("totaldescuento"));
                factura.setTotalimpuesto(rs.getDouble("totalimpuesto"));
                factura.setTotal(rs.getDouble("total"));
                factura.setIdfactura(rs.getInt("idfactura"));
                factura.setNumerofactura(rs.getString("numerofactura"));
                factura.setFecha(rs.getString("fecha"));
                factura.setTipodepago(rs.getString("tipodepago"));
                factura.setDocumentocliente(rs.getString("documentocliente"));
                factura.setNombrecliente(rs.getString("nombrecliente"));
                listafacturas.add(factura);
            }
        } catch (SQLException error) {
            System.out.println("Error en consulta de factura: " + error);
        }
        return listafacturas;
    }

    public String codigoFactura() {
        Conexion conectar = new Conexion();
        String sql = "SELECT MAX(CAST(numerofactura AS INTEGER)) AS codigo FROM facturas";
        String codfactura = "1001";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            if (rs.next()) {
                codfactura = String.valueOf(Integer.parseInt(rs.getString("codigo")) + 1);
            }
        } catch (SQLException error) {
            System.out.println("Error en codigo factura: " + error);
        }
        return codfactura;
    }

    public void guardar(MdlFacturas factura) {
        Conexion conectar = new Conexion();
        String sql;
        sql = "INSERT INTO facturas(numerofactura, fecha, tipodepago, "
                + "documentocliente, nombrecliente, subtotal, descuento, iva, "
                + "totaldescuento, totalimpuesto, total) VALUES ('" + factura.getNumerofactura()
                + "','" + factura.getFecha() + "','" + factura.getTipodepago()
                + "','" + factura.getDocumentocliente() + "','" + factura.getNombrecliente()
                + "'," + factura.getSubtotal() + "," + factura.getDescuento()
                + "," + factura.getIva() + "," + factura.getTotaldescuento()
                + "," + factura.getTotalimpuesto() + "," + factura.getTotal() + ")";
        try {
            if (conectar.ejecutar(sql)) {
                CtrDetalles cdetalle = new CtrDetalles();
                factura.setIdfactura(consultaIdFactura());
                if (cdetalle.guardar(factura)) {
                    JOptionPane.showMessageDialog(null, "Sus datos fueron guardados satisfactoriamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Contactar al administrador del sistema");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Problemas al guardar la factura");
            }
        } catch (Exception error) {
            System.out.println("Error en codigo factura: " + error);
        }
    }

    int consultaIdFactura() {
        Conexion conectar = new Conexion();
        String sql = "SELECT MAX(idfactura) AS id FROM facturas";
        int id = 0;
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException error) {
            System.out.println("Error en consulta idfactura: " + error);
        }
        return id;
    }

    public void eliminar(MdlFacturas factura) {
        Conexion conectar = new Conexion();
        CtrDetalles cdetalle = new CtrDetalles();
        if (cdetalle.eliminar(factura)) {
            String sql = "DELETE FROM facturas WHERE idfactura = " + factura.getIdfactura();
            try {
                if (conectar.ejecutar(sql)) {
                    JOptionPane.showMessageDialog(null, "Su registro fue eliminado satisfatoriamente");
                }
            } catch (Exception error) {
                System.out.println("Error en eliminar factura: " + error);
            }
        }
    }

    public void modificar(MdlFacturas factura) {
        Conexion conectar = new Conexion();
        CtrDetalles cdetalle = new CtrDetalles();
        String sql;
        sql = "UPDATE facturas SET tipodepago='" + factura.getTipodepago() + "',"
                + "documentocliente='" + factura.getDocumentocliente()
                + "',nombrecliente='" + factura.getNombrecliente() + "',subtotal="
                + factura.getSubtotal() + ",descuento=" + factura.getDescuento()
                + ",iva=" + factura.getIva() + ",totaldescuento=" + factura.getTotaldescuento()
                + ",totalimpuesto=" + factura.getTotalimpuesto() + ",total="
                + factura.getTotal() + " WHERE idfactura =" + factura.getIdfactura();
        try {
            if (conectar.ejecutar(sql)) {
                if(cdetalle.modificar(factura)){
                    JOptionPane.showMessageDialog(null, "Su registro fue modificado satisfatoriamente");
                }                
            }
        } catch (HeadlessException error) {
            System.out.println("Error en eliminar factura: " + error);
        }
    }
}
