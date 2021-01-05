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
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bocal
 */
public class Cuentas {
    private int id_usu, per_usu,id_caf;
    private String nom_usu, appat_usu, apmat_usu, email_usu, pass_usu, tel_usu, dir_caf,nom_caf;
    private InputStream fot_caf;

    public int getId_usu() {
        return id_usu;
    }

    public void setId_usu(int id_usu) {
        this.id_usu = id_usu;
    }

    public int getPer_usu() {
        return per_usu;
    }

    public void setPer_usu(int per_usu) {
        this.per_usu = per_usu;
    }

    public int getId_caf() {
        return id_caf;
    }

    public void setId_caf(int id_caf) {
        this.id_caf = id_caf;
    }

    public String getNom_usu() {
        return nom_usu;
    }

    public void setNom_usu(String nom_usu) {
        this.nom_usu = nom_usu;
    }

    public String getAppat_usu() {
        return appat_usu;
    }

    public void setAppat_usu(String appat_usu) {
        this.appat_usu = appat_usu;
    }

    public String getApmat_usu() {
        return apmat_usu;
    }

    public void setApmat_usu(String apmat_usu) {
        this.apmat_usu = apmat_usu;
    }

    public String getEmail_usu() {
        return email_usu;
    }

    public void setEmail_usu(String email_usu) {
        this.email_usu = email_usu;
    }

    public String getPass_usu() {
        return pass_usu;
    }

    public void setPass_usu(String pass_usu) {
        this.pass_usu = pass_usu;
    }

    public String getTel_usu() {
        return tel_usu;
    }

    public void setTel_usu(String tel_usu) {
        this.tel_usu = tel_usu;
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
    
    
    
    public int Guardar(Cuentas c, int tipo){
        int estatus = -1;
        try{
            Connection con = conexion.getConexion();
            if (tipo==2) {
                String q1="insert into usuario (nom_usu,appat_usu,apmat_usu,email_usu,pass_usu,tel_usu,perm_usu) values (?,?,?,?,?,?)";
                PreparedStatement ps1 = con.prepareStatement(q1);
                ps1.setString(1, c.getNom_usu());
                ps1.setString(2, c.getApmat_usu());
                ps1.setString(3, c.getApmat_usu());
                ps1.setString(4, c.getEmail_usu());
                ps1.setString(5, c.getPass_usu());
                ps1.setString(6, c.getTel_usu());
                ps1.setInt(7, 2);
                estatus=ps1.executeUpdate();
            }else if(tipo==3){
                String q = "insert into cafeteria (nom_caf,dir_caf,fot_caf,id_usu,aut_caf) values (?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setString(1, c.getNom_caf());
                ps.setString(2, c.getDir_caf());
                ps.setBlob(3, c.getFot_caf());
                ps.setInt(4, c.getId_usu());
                ps.setBoolean(5, false);
                estatus=ps.executeUpdate();
            }
            
            con.close();
            
        }catch(Exception ed){
            System.out.println("Error al guardar usuario");
            System.out.println(ed.getMessage());
            System.out.println(ed.getStackTrace());
        }
        return estatus;
    }
    public int Actualizar(Cuentas c, int tipo) throws SQLException{
        int estatus = 0;
        Connection con = conexion.getConexion();
        String sql = "";
        PreparedStatement ps = null;
        try{
            if (tipo==2) {
                sql= "update usuario set nom_usu=?, appat_usu=?, apmat_usu=?, tel_usu=? where id_usu=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, c.getNom_usu());
                ps.setString(2, c.getAppat_usu());
                ps.setString(3, c.getApmat_usu());
                ps.setString(4, c.getTel_usu());
                ps.setInt(5, c.getId_usu());
                estatus += ps.executeUpdate();
            }else if(tipo==3){
                sql= "update cafeteria set nom_caf=?, dir_caf=?, fot_caf=? where id_caf=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, c.getNom_caf());
                ps.setString(2, c.getDir_caf());
                ps.setBlob(3, c.getFot_caf());
                ps.setInt(4, c.getId_caf());
                estatus += ps.executeUpdate();
            }
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
    public int Eliminar(int id, int permisos) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        String q=null;
        int estatus = 0;
        try{
            if (permisos==2) {
                con = conexion.getConexion();
                q ="delete from usuario where id_usu=?";
                ps = con.prepareStatement(q);
                ps.setInt(1, id);
                estatus += ps.executeUpdate();
            }
            
        
        }catch (Exception ed){
            System.out.println("No conecto a la tabla");
            System.out.println(ed.getMessage());
            System.out.println(ed.getStackTrace());
        }finally{
            ps.close();
            con.close();
        }
        return estatus;   
    }
    public Cuentas getUsuarioById(int id){
        Cuentas c = new Cuentas();
        try{
            Connection con = conexion.getConexion();
            String sql = "Select * from usuario,cuenta where usuario.id_cue=cuenta.id_cue and id_usu = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                c.setIdC(rs.getInt(1));
                c.setNombre(rs.getString(3));
                c.setAppat(rs.getString(4));
                c.setApmat(rs.getString(5));
                c.setTel(rs.getString(6));
                c.setCorreo(rs.getString(8));
                c.setContrasena(rs.getString(9));
                break;
            }
            
            con.close();
            
        }catch(Exception ed){
            System.out.println("error en get usuario by id");
            System.out.println(ed.getMessage());
            System.out.println(ed.getStackTrace());
        
        }
        return c;
    }
    public Cuentas getCafeteriaById(int id){
        Cuentas c = new Cuentas();
        try{
            Connection con = conexion.getConexion();
            String sql = "Select * from cafeteria,cuenta where cafeteria.id_cue=cuenta.id_cue and id_caf = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                c.setIdC(rs.getInt(1));
                c.setFoto(rs.getBinaryStream(3));
                c.setNomLocal(rs.getString(4));
                c.setCalle(rs.getString(5));
                c.setColonia(rs.getString(6));
                c.setEx(rs.getInt(7));
                c.setCorreo(rs.getString(9));
                c.setContrasena(rs.getString(10));
                break;
            }
            
            con.close();
            
        }catch(Exception ed){
            System.out.println("error en get cafeteria by id");
            System.out.println(ed.getMessage());
            System.out.println(ed.getStackTrace());
        
        }
        return c;
    }
    public Cuentas encontrarUsuario(String correo, String contr) throws SQLException{
        Cuentas c = new Cuentas ();
            Connection con = conexion.getConexion();
            String sql="select * from cuenta where email_usu = '"+correo+"' and pass_usu = '"+contr+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                c.setIdC(rs.getInt(1));
                c.setCorreo(rs.getString(2));
                c.setContrasena(rs.getString(3));
                c.setPermisos(rs.getInt(4));
                break;
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
            String q="SELECT img_caf FROM cafeteria where id_caf="+idP+"";
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
}
