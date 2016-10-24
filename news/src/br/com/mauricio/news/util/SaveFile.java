package br.com.mauricio.news.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFile {

    public static void criaArquivo(File caminhoorigem, String novocaminho) throws IOException {
        FileOutputStream os = null;
        FileInputStream is = null;
        try {
        	is = new FileInputStream(caminhoorigem);
            os = new FileOutputStream(novocaminho);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(is!=null)
				is.close();
			if(os!=null)
				os.close();
        }
    }     
	
    public static void criaArquivo(byte[] bytes, String caminho) {
         FileOutputStream fos; 
         try {
             fos = new FileOutputStream(caminho);
             fos.write(bytes);
  
             fos.flush();
             fos.close();
         } catch (FileNotFoundException ex) {
             ex.printStackTrace();
         } catch (IOException ex) {
             ex.printStackTrace();
         }
     }     
    
}
