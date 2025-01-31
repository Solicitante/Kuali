package Clases;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.crypto.*;
import java.util.*;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import java.util.ArrayList;

/**
 *
 * @author manua
 */
public class cifrar {
    
    public Usuario AESCifrar(String nombre, String apellidoPat, String apellidoMat, String tel,
            String correo, String pass){
    
        Usuario usuario = new Usuario();
        
        String llaveS = "AxolosoftwareWin";
        
        SecretKeySpec key = new SecretKeySpec(llaveS.getBytes(), "AES");
        
        System.out.println("Llave: "+key);
        
        Cipher cifrado;
        
        try{
        
            System.out.println("Verificamos el llenado de datos");
            System.out.println(nombre);
            System.out.println(apellidoPat);
            System.out.println(apellidoMat);
            System.out.println(tel);
            System.out.println("***********************************");
            
            cifrado = Cipher.getInstance("AES");
            
            cifrado.init(Cipher.ENCRYPT_MODE,key);
            
            /*Declaracion de los bytes para cada campo*/
            byte campoNombre[] = cifrado.doFinal(nombre.getBytes());
            byte campoApellidoPat[] = cifrado.doFinal(apellidoPat.getBytes());
            byte campoApellidoMat[] = cifrado.doFinal(apellidoMat.getBytes());
            byte campoTel[] = cifrado.doFinal(tel.getBytes());
            byte campoCorreo[] = cifrado.doFinal(correo.getBytes());
            byte campoPass[] = cifrado.doFinal(pass.getBytes());
            
            
            /* Recuperacion mediante BASE64 para la legibilidad */
            String nombreB64 = new String(encodeBase64(campoNombre));
            String apellidoPatB64 = new String(encodeBase64(campoApellidoPat));
            String apellidoMatB64 = new String(encodeBase64(campoApellidoMat));
            String telB64 = new String(encodeBase64(campoTel));
            String correoB64 = new String(encodeBase64(campoCorreo));
            String passB64 = new String(encodeBase64(campoPass));
            
            
            /*Comprobación de los datos cifrados*/
            System.out.println("Esto pertenece a la clase cifrado");
            System.out.println("Nombre: "+nombreB64);
            System.out.println("Apellido Paterno: "+apellidoPatB64);
            System.out.println("Apellido Materno: " + apellidoMatB64);
            System.out.println("Telefono: "+ telB64);
            
            usuario.setNom_usu(nombreB64);
            usuario.setAppat_usu(apellidoPatB64);
            usuario.setApmat_usu(apellidoMatB64);
            usuario.setTel_usu(telB64);
            usuario.setEmail_usu(correoB64);
            usuario.setPass_usu(passB64);
            
            
        }catch(Exception e){
        
            System.out.println("Error, murió la cosa esta UnU");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            
        }
        
        
        return usuario;
    }
    
    public Usuario AESDescifrar(String nombre, String apellidoPat,
            String apellidoMat, String tel, String correo, String pass){
    
        Usuario u = new Usuario();
        String llaveS = "AxolosoftwareWin";
        
        SecretKeySpec key = new SecretKeySpec(llaveS.getBytes(), "AES");
        
        Cipher cifrado;
        
        try{
            
            cifrado = Cipher.getInstance("AES");
            System.out.println("Creado cifrado con AES");
            cifrado.init(Cipher.DECRYPT_MODE, key);
            System.out.println("Iniciamos cifrado en modo decrypt");
            
            /* Revisamos el llenado de datos */
            System.out.println(nombre);
            System.out.println(apellidoPat);
            System.out.println(apellidoMat);
            System.out.println(tel);
            
            /*Recuperamos primero con BASE64*/
            byte nomB[] = decodeBase64(nombre);
            byte appatB[] = decodeBase64(apellidoPat);
            byte apmatB[] = decodeBase64(apellidoMat);
            byte telB[] = decodeBase64(tel);
            byte corB[] = decodeBase64(correo);
            byte pasB[] = decodeBase64(pass);
            
            byte nombreD[] = cifrado.doFinal(nomB);
            byte apellidoPatD[] = cifrado.doFinal(appatB);
            byte apellidoMatD[] = cifrado.doFinal(apmatB);
            byte telD[] = cifrado.doFinal(telB);
            byte corD[] = cifrado.doFinal(corB);
            byte pasD[] = cifrado.doFinal(pasB);
            
            String nombreRec = new String(nombreD);
            String apellidoPatRec = new String(apellidoPatD);
            String apellidoMatRec = new String(apellidoMatD);
            String telRec = new String(telD);
            String corRec = new String(corD);
            String pasRec = new String(pasD);
            
            u.setNom_usu(nombreRec);
            u.setAppat_usu(apellidoPatRec);
            u.setApmat_usu(apellidoMatRec);
            u.setTel_usu(telRec);
            u.setEmail_usu(corRec);
            u.setPass_usu(pasRec);
            
        }catch(Exception e){
        
            System.out.println("Fallo en el descifrado");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        
        }
        
        
        return u;
    }
    
    public ArrayList verificarUsuario(String correo, String password){
        
        ArrayList al = new ArrayList();
        String llaveS = "AxolosoftwareWin";
        
        SecretKeySpec key = new SecretKeySpec(llaveS.getBytes(), "AES");
        
        Cipher cifrado;
        
        try {
            
            cifrado = Cipher.getInstance("AES");
            System.out.println("Creado cifrado con AES");
            cifrado.init(Cipher.DECRYPT_MODE, key);
            System.out.println("Iniciamos cifrado en modo decrypt");
            
            /* Comenzamos el cifrado */
            byte[] cifradoC = cifrado.doFinal(correo.getBytes());
            byte[] cifradoP = cifrado.doFinal(password.getBytes());
            
            String cBase = new String(encodeBase64(cifradoC));
            String pBase = new String(encodeBase64(cifradoP));
            
            al.add(cBase);
            al.add(pBase);
            
            
        } catch (Exception e) {
        }
        
        
        return al;
    }
    
    
    
}
