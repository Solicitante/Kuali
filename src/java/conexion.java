/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;

/**
 *
 * @author manua
 */
public class conexion {
    
    public static Connection getConexion(){
    
        Connection con = null;
        String url, username, password;
        url = "jdbc:mysql://localhost:3306/pruebacifrado";
        username = "root";
        password = "n0m3lo";
        
        try{
        
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
            System.out.println("Se conectó uwu");
            
        }catch(Exception e){
            System.out.println("No se conectó unu");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        
        
        return con;
    }
    
    
    
}
