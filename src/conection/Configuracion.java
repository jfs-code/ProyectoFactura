/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conection;

/**
 *
 * @author USUARIO
 */
public interface Configuracion {

    String DRIVER = "com.mysql.jdbc.Driver";
    String DATA_BASE = "globaltek";   
    String CONNECTION_URL = "jdbc:mysql://localhost:3306/"+DATA_BASE;
    String USERNAME = "root";
    String PASSWORD = "";

}

