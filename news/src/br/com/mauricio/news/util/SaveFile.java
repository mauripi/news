package br.com.mauricio.news.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;

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
    
    public static void copiar(String origem, String destino){
		Thread thread = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	try {    	
			        File source = new File(origem);
			        File dest = new File(destino);
			        FileUtils.copyFile(source, dest);
			        Thread.sleep(2000);
		    	} catch (Exception e) {
		    		System.out.println(e.getLocalizedMessage());
		    	}
		    }		            
		});		        
		thread.start();        
    }

    public static void criarPasta(String caminho) {
        File folder = new File(caminho);
        if (!folder.exists())
            folder.mkdirs();	
	}

	public static void criaArquivo(FileUploadEvent upload, String caminho) throws IOException {
		Thread thread = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	try {
					InputStream initialStream = upload.getFile().getInputstream();
					byte[] buffer = new byte[initialStream.available()];
					initialStream.read(buffer);
				    File targetFile = new File(caminho+"//"+upload.getFile().getFileName());
				    criarPasta(caminho);
				    OutputStream outStream = new FileOutputStream(targetFile);
					outStream.write(buffer);
					outStream.close();
					Thread.sleep(2000);
		    	} catch (Exception e) {
		    		System.out.println(e.getLocalizedMessage());
		    	}
		    }		            
		});		        
		thread.start();
	}

	public static void criaArquivo(InputStream is, String caminho) throws IOException,FileNotFoundException{
        File file = new File(caminho);        	
        FileOutputStream os = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) 
            os.write(buffer, 0, length);
        os.close();
	}
	
    public static List<String> listFilesInFolder(String caminho) {
        List<String> files = new ArrayList<String>();       
        File directory = new File(caminho);       
        if(directory.isDirectory()) {
            String[] subDirectory = directory.list();
            if(subDirectory != null) 
                for(String dir : subDirectory)
                	files.add(dir);                  
        }
        return files;
    }

	public static void criaArquivo(String string, String novocaminho) {
		
	}
	
}
