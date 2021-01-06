/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bocal
 */
public class Cafeteria {
    private int id_caf, id_usu;
    private String dir_caf,nom_caf;
    private InputStream fot_caf;
    private boolean aut_caf;

    public int AñadirCafeteria(Cafeteria c){
        int estatus = -1;
        try{
            Connection con = conexion.getConexion();
                String q = "insert into cafeteria (nom_caf,dir_caf,fot_caf,id_usu,aut_caf) values (?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setString(1, c.getNom_caf());
                ps.setString(2, c.getDir_caf());
                ps.setBlob(3, c.getFot_caf());
                ps.setInt(4, c.getId_usu());
                ps.setInt(5, 1);
                estatus=ps.executeUpdate();
            con.close();
        }catch(Exception ed){
            System.out.println("Error al guardar usuario");
            System.out.println(ed.getMessage());
            System.out.println(ed.getStackTrace());
        }
        return estatus;
    }
    
    public int ActualizarCafeteria(Cafeteria c) throws SQLException{
        int estatus = 0;
        Connection con = conexion.getConexion();
        String sql = "";
        PreparedStatement ps = null;
        try{
            sql= "update cafeteria set nom_caf=?, dir_caf=?, fot_caf=? where id_caf=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNom_caf());
            ps.setString(2, c.getDir_caf());
            ps.setBlob(3, c.getFot_caf());
            ps.setInt(4, c.getId_caf());
            estatus += ps.executeUpdate();
        }catch(Exception ed){
            System.out.println("No conecto a la tabla");
            System.out.println(ed.getMessage());
            System.out.println(ed.getStackTrace());
        }finally{
            ps.close();
            con.close();
        }
        return estatus;
    }
    
    public int EliminarCafeteria(int id) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        String q=null;
        int estatus = 0;
        try{
            con = conexion.getConexion();
            q="delete from cafeteria where id_caf=?";
            ps = con.prepareStatement(q);
            ps.setInt(1, id);
            estatus =ps.executeUpdate();
        }catch (Exception ed){
            con.close();
            System.out.println("No conecto a la tabla");
            System.out.println(ed.getMessage());
            System.out.println(ed.getStackTrace());
        }finally{
            ps.close();
            con.close();
        }
        return estatus;   
    }
    
    public int comprobarAutorizacion(int id_usu) throws SQLException{
        int compro=0;
        Connection con = null;
        PreparedStatement ps = null;
        String q=null;
        try{
            con = conexion.getConexion();
            q ="select * from cafeteria where id_usu=?";
            ps = con.prepareStatement(q);
            ps.setInt(1, id_usu);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(6));
                compro=rs.getInt(6);
                break;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            ps.close();
            con.close();
        }
        return compro;
    }
    
    public boolean comprobarCafExiste(int id_usu) throws SQLException{
        boolean compro = true;
        Connection con = null;
        PreparedStatement ps = null;
        String q=null;
        try{
            con = conexion.getConexion();
            q ="select * from cafeteria where id_usu=?";
            ps = con.prepareStatement(q);
            ps.setInt(1, id_usu);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                compro=true;
            }else{
                compro=false;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            ps.close();
            con.close();
        }
        return compro;
    }
    
    public static ArrayList<Cafeteria> getCafeteriasAutorizadas(){
        ArrayList<Cafeteria> lista= new ArrayList<Cafeteria>();
        try{
            Connection con = conexion.getConexion();
            String sql = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            
            sql="select * from cafeteria where aut_caf=true";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Cafeteria c = new Cafeteria();
                    c.setId_caf(rs.getInt(1));
                    c.setNom_caf(rs.getString(2));
                    c.setDir_caf(rs.getString(3));
                    c.setId_usu(rs.getInt(4));
                lista.add(c);
            }            
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    public static ArrayList<Cafeteria> getCafeteriasPendientes(){
        ArrayList<Cafeteria> lista= new ArrayList<>();
        try{
            Connection con = conexion.getConexion();
            String sql = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            
            sql="select * from cafeteria where aut_caf=1";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Cafeteria c = new Cafeteria();
                    c.setId_caf(rs.getInt(1));
                    c.setNom_caf(rs.getString(2));
                    c.setDir_caf(rs.getString(3));
                    c.setId_usu(rs.getInt(4));
                lista.add(c);
            }            
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    public Cafeteria getCafeteriaById(int id){
        Cafeteria c = new Cafeteria();
        try{
            Connection con = conexion.getConexion();
            String sql = "Select * from cafeteria where id_usu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                c.setId_caf(rs.getInt(1));
                c.setNom_caf(rs.getString(2)); //rs.getBinaryStream(3)
                c.setDir_caf(rs.getString(3));
                c.setFot_caf(rs.getBinaryStream(5));
                c.setAut_caf(rs.getBoolean(6));
                break;
            }
            ps.close();
            con.close();
            
        }catch(Exception ed){
            System.out.println("error en get cafeteria by id");
            System.out.println(ed.getMessage());
            System.out.println(ed.getStackTrace());
        }
        return c;
    }
    
    public void listarImg(int idP, HttpServletResponse response) throws IOException{
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs= null;
        InputStream inputstream=null;
        OutputStream outputstream=null;
        BufferedInputStream bufferedinputstream=null;
        BufferedOutputStream bufferedoutputstream=null;
        response.setContentType("image/*");
        try{
            outputstream=response.getOutputStream();
            cn=conexion.getConexion();
            String q="SELECT fot_caf FROM cafeteria where id_caf="+idP+"";
            ps=cn.prepareStatement(q);
            rs=ps.executeQuery();
            if(rs.next()){
                inputstream=rs.getBinaryStream(1);
            }
            bufferedinputstream=new BufferedInputStream(inputstream);
            bufferedoutputstream=new BufferedOutputStream(outputstream);
            int i=0;
            while((i=bufferedinputstream.read())!=-1){
                bufferedoutputstream.write(i);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            System.out.println("Error en listarImg");
        }finally{
            try{
                rs.close();
                ps.close();
                cn.close();
            }catch(SQLException ex){
                ex.getStackTrace();
                System.out.println(ex.getMessage());
            }
        }
    }
    
    
    public int getId_caf() {
        return id_caf;
    }

    public void setId_caf(int id_caf) {
        this.id_caf = id_caf;
    }

    public String getDir_caf() {
        return dir_caf;
    }

    public void setDir_caf(String dir_caf) {
        this.dir_caf = dir_caf;
    }

    public String getNom_caf() {
        return nom_caf;
    }

    public void setNom_caf(String nom_caf) {
        this.nom_caf = nom_caf;
    }

    public InputStream getFot_caf() {
        return fot_caf;
    }

    public void setFot_caf(InputStream fot_caf) {
        this.fot_caf = fot_caf;
    }

    public boolean isAut_caf() {
        return aut_caf;
    }

    public void setAut_caf(boolean aut_caf) {
        this.aut_caf = aut_caf;
    }

    public int getId_usu() {
        return id_usu;
    }

    public void setId_usu(int id_usu) {
        this.id_usu = id_usu;
    }
    
    
}