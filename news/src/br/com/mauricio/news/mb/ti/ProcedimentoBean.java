package br.com.mauricio.news.mb.ti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.util.SaveFile;

@ManagedBean(name="procedimentoMB") 
public class ProcedimentoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private InputStream is;
	private String msg;
	private List<String> arquivos;
	private String arquivo;
	
	@PostConstruct
    public void carol() {
    	carregaArquivo();
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		
		is=event.getFile().getInputstream();
    	if(is!=null){    		
    		SaveFile.criaArquivo(event, caminhoReal());				
    		try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		System.out.println();
    		if(msg!=""){
				msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";				
			}
    		carregaArquivo();
    		msg="Arquivo salvo com sucesso";
			mensagens();
		} else{
			System.out.println("is é null em handleFileUpload(FileUploadEvent event)");
		}    
    }

    public void carregaArquivo (){
    	arquivos = SaveFile.listFilesInFolder(caminhoReal());
    	for(String arquivo : arquivos){
    		try {
				SaveFile.criaArquivo(new File(caminhoReal()+arquivo), caminhoRealativo()+arquivo);
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
    	}
    }
    
    public void removeArquivo(){
    	File fileR = new File(caminhoRealativo()+arquivo);
    	File fileA = new File(caminhoReal()+arquivo);
    	
    	fileR.delete();
    	fileA.delete();
    	arquivos.remove(arquivo);
    	msg="Arquivo removido";
    	mensagens();
    }
    

	private String caminhoReal(){
		return "c:\\PDF\\";	
	}
	
	
	private String caminhoRealativo(){
		FacesContext cx = FacesContext.getCurrentInstance();
		String path = cx.getExternalContext().getRealPath("");
		SaveFile.criarPasta(path+"\\temp\\procedimento\\");
		return path+"\\temp\\procedimento\\";	
	}	

    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage(msg,""));  		
	}
	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public List<String> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<String> arquivos) {
		this.arquivos = arquivos;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
	


}
