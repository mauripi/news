package br.com.mauricio.news.ln.contabil;

import java.io.File;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import br.com.mauricio.news.dao.contabil.SigDao;
import br.com.mauricio.news.util.AppZip;
import br.com.mauricio.news.util.SaveFile;

public class SigLN {
	
	private String msg;

	public String gerarArquivosSig(int ano, int mes){
		SigDao dao = new SigDao();
		msg="";
		try {
			dao.gerar(ano, mes);
		} catch (IOException e) {
			msg = "Ocorreu erro ao gerar arquivo.";
			e.printStackTrace();
		}
		AppZip zip = new AppZip();
		try {
			zip.ziparArquivosSIG();
		} catch (IOException e) {
			msg = "Ocorreu erro ao gerar arquivo.";
			e.printStackTrace();
		}
		return msg;
	}

	public void retornarArquivo() throws IOException{
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();	
		File folder = new File(sContext.getRealPath("/resource/sig"));
        if (!folder.exists())
            folder.mkdirs();
		String destino= sContext.getRealPath("/resource/sig") + File.separator + "sig.zip";
        File origem = new File("C:\\Windows\\Temp\\SIGZIP\\SIG.zip");
        SaveFile.criaArquivo(origem, destino);
	}
	
}
