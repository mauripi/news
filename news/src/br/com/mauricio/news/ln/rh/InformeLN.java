package br.com.mauricio.news.ln.rh;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.Informe;
import br.com.mauricio.news.util.SaveFile;

public class InformeLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private static String CAMINHO_PASTA_IR = "C:\\ARQUIVOS_RH\\IR\\";
	
	public List<Informe> getLista(Login usuarioLogado) {
		String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
		path = path+"\\sistema\\rh\\ir\\";
		List<Informe> informes = getRendimentos(usuarioLogado.getChapa());
		for(Informe i:informes){
			String dest = path+i.getAno().toString()+"\\"+i.getChapa()+".pdf";
			SaveFile.copiar(i.getCaminho(), dest);
		}
		return informes;
	}


    public static List<Informe> getRendimentos(String chapa) {
    	List<Informe> informes = new ArrayList<Informe>();
    	File directory = new File(CAMINHO_PASTA_IR);
        if(directory.isDirectory()) {
            String[] subDirectory = directory.list();
            if(subDirectory != null) 
                for(String dir : subDirectory)
                	if(isNumeric(dir)){
                        File file = new File(CAMINHO_PASTA_IR+dir+"\\"+chapa+".pdf");
                        if(file.exists()){
                        	Informe inf  = new Informe();
                        	inf.setAno(Integer.parseInt(dir));
                        	inf.setCaminho(file.getAbsolutePath());
                        	inf.setChapa(chapa);
                        	inf.setId(Long.parseLong(dir));
                        	informes.add(inf);
                        }
                	}
        }
		return informes;
    }
    
    public static boolean isNumeric (String s) {
        try {
            Long.parseLong (s); 
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }    
}
