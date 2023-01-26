package controller;

import conection.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.MdlProductos;

/**
 *
 * @author scorpion
 */
public class CtrProductos {
    public MdlProductos consultaProducto(int codigo){
        MdlProductos producto = new MdlProductos();
        Conexion conectar = new Conexion();
        String sql = "SELECT * FROM productos WHERE idproducto = "+codigo;
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            if(rs.next()){
                producto.setIdproducto(rs.getInt("idproducto"));
                producto.setProducto(rs.getString("producto"));
            }   
        } catch (SQLException error) {
            System.out.println("Error en consulta de productos: "+error);
        }        
        return producto;
    }
    
    public ArrayList<MdlProductos> consulta(){
        ArrayList<MdlProductos> listaproductos = new ArrayList();        
        Conexion conectar = new Conexion();
        String sql = "SELECT * FROM productos";
        ResultSet rs;
        try {
            rs = conectar.consultar(sql);
            while(rs.next()){
                MdlProductos producto = new MdlProductos();
                producto.setIdproducto(rs.getInt("idproducto"));
                producto.setProducto(rs.getString("producto"));
                listaproductos.add(producto);
            }   
        } catch (SQLException error) {
            System.out.println("Error en consulta de productos: "+error);
        }        
        return listaproductos;
    }
}
