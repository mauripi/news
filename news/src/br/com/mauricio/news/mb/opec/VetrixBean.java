package br.com.mauricio.news.mb.opec;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.ln.opec.VetrixLN;
import br.com.mauricio.news.model.opec.Vetrix;
import br.com.mauricio.news.util.SaveFile;


/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="vetrixMB")
@ViewScoped
public class VetrixBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String arquivo;
	private Date data;
	private InputStream is;
	private VetrixLN ln;
	private List<Vetrix> lista = new ArrayList<Vetrix>();
	private static final String CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO = "C:\\Windows\\Temp\\opec\\"; 

	public void gravar(){
		ln= new VetrixLN();
		ln.gerarTxt(lista);
		mensagens("Nada para gravar por enquanto");
	}
	
	public void handleFileUpload(FileUploadEvent event) {
        arquivo =  event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
			SaveFile.recebeArquivoUpload(is,arquivo, CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO);
			ln= new VetrixLN();
			lista = ln.leArquivo(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO+arquivo);
		} catch (IOException e) {
			mensagens("Ocorreu erro ao receber o arquivo.");
			System.out.println("VetrixBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    }
	
	public void mensagens(String msg){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
//=========================================================================================//
	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public List<Vetrix> getLista() {
		return lista;
	}

	public void setLista(List<Vetrix> lista) {
		this.lista = lista;
	}


	
}
