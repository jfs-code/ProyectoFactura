package controller;

import conection.Conexion;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.MdlDetalles;
import model.MdlFacturas;

/**
 *
 * @author scorpion
 */
public class CtrDetalles {

    public ArrayList<MdlDetalles> consulta(MdlFacturas factura) {
        ArrayList<MdlDetalles> listadetalles = new ArrayList();
        Conexion conectar = new Conexion();
        CtrProductos cproducto = new CtrProductos();
        String sql = "SELECT * FROM detalles WHERE idfactura = " + factura.getIdfactura();
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while (rs.next()) {
                MdlDetalles detalle = new MdlDetalles();
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPreciounitario(rs.getInt("preciounitario"));
                detalle.setIddetalle(rs.getInt("iddetalle"));
                detalle.setIdfactura(rs.getInt("idfactura"));
                detalle.setProducto(cproducto.consultaProducto(rs.getInt("idproducto")));
                listadetalles.add(detalle);
            }
        } catch (SQLException error) {
            System.out.println("Error en consulta de detalles: " + error);
        }
        return listadetalles;
    }

    public boolean guardar(MdlFacturas factura) {
        Conexion conectar = new Conexion();
        boolean bandera = false;
        for (int i = 0; i < factura.getDetallefactura().size(); i++) {
            String sql;
            sql = "INSERT INTO detalles(idfactura, idproducto, cantidad, preciounitario) "
                    + "VALUES (" + factura.getIdfactura() + "," + factura.getDetallefactura().get(i).getProducto().getIdproducto()
                    + "," + factura.getDetallefactura().get(i).getCantidad() + ","
                    + factura.getDetallefactura().get(i).getPreciounitario() + ")";
            try {
                if (conectar.ejecutar(sql)) {
                    bandera = true;
                }
            } catch (Exception error) {
                System.out.println("Error en codigo factura: " + error);
            }
        }
        return bandera;
    }

    public boolean eliminar(MdlFacturas factura) {
        boolean bandera = false;
        Conexion conectar = new Conexion();
        String sql = "DELETE FROM detalles WHERE idfactura = " + factura.getIdfactura();
        try {
            if (conectar.ejecutar(sql)) {
                bandera = true;
            }
        } catch (Exception error) {
            System.out.println("Error en eliminar detalle: " + error);
        }
        return bandera;
    }

    public boolean modificar(MdlFacturas factura) {
        Conexion conectar = new Conexion();
        boolean bandera = false;
        for (int i = 0; i < factura.getDetallefactura().size(); i++) {
            String sql;
            sql = "UPDATE detalles SET cantidad=" + factura.getDetallefactura().get(i).getCantidad()
                + ",preciounitario= "+factura.getDetallefactura().get(i).getPreciounitario()
                + " WHERE iddetalle ="+factura.getDetallefactura().get(i).getIddetalle();
            try {
                if (!conectar.ejecutar(sql)) {
                    JOptionPane.showMessageDialog(null, "Su detalle no fue modificado");
                }
            } catch (HeadlessException error) {
                System.out.println("Error en eliminar factura: " + error);
            }
        }
        return bandera;
    }
}
