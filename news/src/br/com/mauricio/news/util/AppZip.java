package br.com.mauricio.news.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AppZip{
    List<String> fileList;
    private static final String OUTPUT_ZIP_FILE = "C:\\Windows\\Temp\\SIGZIP\\SIG.zip";
    private static final String SOURCE_FOLDER = "C:\\Windows\\Temp\\SIG\\";
	
    public AppZip(){
    	fileList = new ArrayList<String>();
    }
    
    public void ziparArquivosSIG() throws IOException{
    	AppZip appZip = new AppZip();
    	appZip.generateFileList(new File(SOURCE_FOLDER));
    	appZip.zipIt(OUTPUT_ZIP_FILE);    	
    }

    public void zipIt(String zipFile) throws IOException{
    	File zfile = new File(zipFile);
		if(zfile.exists())
			zfile.delete();
	    byte[] buffer = new byte[1024];	    	
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
			
		for(String file : this.fileList){			
			ZipEntry ze= new ZipEntry(file);
	    	zos.putNextEntry(ze);       
	    	FileInputStream in = new FileInputStream(SOURCE_FOLDER + file);  	   
	    	int len;
	    	while ((len = in.read(buffer)) > 0) 
	    		zos.write(buffer, 0, len);   
	    	in.close();
		}		
		zos.closeEntry();
		zos.close();       
   }

    public void generateFileList(File node){   	
		if(node.isFile())
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));		

		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote)
				generateFileList(new File(node, filename));		
		}
    }


    private String generateZipEntry(String file){
    	return file.substring(SOURCE_FOLDER.length(), file.length());
    }
}