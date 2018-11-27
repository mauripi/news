package br.com.mauricio.news.mb.ti;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.ln.ti.ConsumoEmbratelLN;
import br.com.mauricio.news.util.SaveFile;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="consumoembratelMB")
@ViewScoped
public class ConsumoEmbratelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String arquivo;
	private String fatura;
	private InputStream is;
	private ConsumoEmbratelLN ln;
	private static final String CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO = "C:\\Windows\\Temp\\ti\\";

	
	@PostConstruct
	public void init(){
		arquivo="";
		fatura="";		
	}

	public void importarFatura(){
		ln = new ConsumoEmbratelLN();
		if(validaCampos()){
			if(ln.faturaJaExiste(fatura))
				mensagens("Fatura já foi importada.");				
			else	
				mensagens(ln.carregaArquivo(arquivo, fatura));			
		}
	}
	
	public void cancelar(){
		arquivo="";
		fatura="";	
		is=null;
	}
	
	private boolean validaCampos() {
		if(arquivo.length() < 1){
			mensagens("Selecione um arquivo.");
			return false;
		}
		if(fatura.length() < 1 ){
			mensagens("Informe a fatura.");
			return false;
		}
		return true;
	}

	public void handleFileUpload(FileUploadEvent event) {
        arquivo =  event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
		} catch (IOException e) {
			mensagens("Ocorreu erro ao receber o arquivo.");
			System.out.println("ConsumoEmbratelBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    }
	
    public void importar(){
	    String msg = SaveFile.recebeArquivoUpload(is,arquivo, CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO);
	    mensagens(msg);
    }

	
	public void mensagens(String msg){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getFatura() {
		return fatura;
	}

	public void setFatura(String fatura) {
		this.fatura = fatura;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

}
