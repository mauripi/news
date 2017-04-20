package br.com.mauricio.news.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Cripto {

    public static String criptografa(String senha)   
        throws NoSuchAlgorithmException {   
        MessageDigest md = MessageDigest.getInstance("MD5");   
        md.reset();  
        byte[] array = md.digest(senha.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
       }
        return sb.toString();   
    }   
     
    public static String gerarSenha(){
    	UUID uuid = UUID.randomUUID();    	
		return uuid.toString().substring(0,8);   	
    }

}
