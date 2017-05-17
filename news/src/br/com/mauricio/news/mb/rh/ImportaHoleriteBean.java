package br.com.mauricio.news.mb.rh;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.ln.rh.ImportaHoleriteLN;

@ManagedBean(name="importHoleriteMB")
@ViewScoped
public class ImportaHoleriteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mes;
	private String ano;
	private String msg;
	private Integer periodo=0;
	private String nomeArquivo;
	private InputStream is;
	
	@PostConstruct
	public void init(){
		mes="";
		ano="";
	}


    public void handleFileUpload(FileUploadEvent event) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        nomeArquivo =  fmt.format(new Date())+"_"+event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
		} catch (IOException e) {
			msg = "Ocorreu erro ao importar arquivo.";
			System.out.println("ImportaHoleriteBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    }
    
    public void importar(){
    	if(is!=null){
	    	if(mes!=""&&ano!=""&&periodo!=0){
		    	ImportaHoleriteLN ln = new ImportaHoleriteLN();    
		    	msg = ln.saveFile(is,nomeArquivo);
		    	if(msg!=""){
		    		msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
		    		mensagens();
		    	}else{
		    		msg = ln.fileValidate(mes,ano,periodo);
		        	if(msg!="")
		        		mensagens();
		    	}
	    	}else{
	    		msg="Informe todos os campos!";
	    		mensagens();
	    	}
    	}else{
    		msg="Selecione o arquivo com as bases a ser importado!";
    		mensagens();
    	}
    }
    
    

    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}   
    
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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


	public Integer getPeriodo() {
		return periodo;
	}


	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

}
