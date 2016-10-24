package br.com.mauricio.news.mb.marketing;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.ln.marketing.ImportaMidiaMaisLN;

@ManagedBean(name="importamidiamaisMB")
@ViewScoped
public class ImportaMidiaMaisBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String msg;
	private String nomeArquivo;
	private InputStream is;
	

	public void handleFileUpload(FileUploadEvent event) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        nomeArquivo =  fmt.format(new Date())+"_"+event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
		} catch (IOException e) {
			msg = "Ocorreu erro ao importar arquivo.";
			System.out.println("ImportaMidiaMaisBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    }
    
    public void importar(){
    	if(is!=null){	    	
    		ImportaMidiaMaisLN ln = new ImportaMidiaMaisLN();    
	    	msg = ln.recebeArquivoUpload(is,nomeArquivo);
	    	if(msg!=""){
	    		msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
	    		mensagens();
	    	}else{
	    		msg = ln.importarArquivo(nomeArquivo);
	        	if(msg!="")
	        		mensagens();
	    	}
    	}else{
    		msg="Selecione o arquivo a ser importado!";
    		mensagens();
    	}
    }
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}       

}
