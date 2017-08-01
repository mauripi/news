package br.com.mauricio.news.mb.comercial;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.comercial.AnexoPdf;
import br.com.mauricio.news.util.SaveFile;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/

@ManagedBean(name="anexopdfMB")
@ViewScoped
public class AnexoPdfBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private AnexoPdf anexopdf = new AnexoPdf();;
	private List<AnexoPdf> listAll;
	private String msg;
		
	@PostConstruct
	public void inicio(){
		listar();
	}
	
	public void grava(){
		GenericLN<AnexoPdf> gln = new GenericLN<AnexoPdf>();
		for (AnexoPdf x : listAll) 
			if(anexopdf.getTipo().equals(x.getTipo())){
				x.setStatus(false);
				gln.update(x);
			}		
		gln.add(anexopdf);
		listar();
		limpaCadastro();
	}
	
	public void novo(){
		anexopdf = new AnexoPdf();
	}
	
	public void listar(){
		GenericLN<AnexoPdf> gln = new GenericLN<AnexoPdf>();
		listAll = gln.listWithoutRemoved("anexopdf", "id asc");
	}
	
	public void selecao(SelectEvent event){
		anexopdf = (AnexoPdf) event.getObject();	
	}

    public void handleFileUpload(FileUploadEvent event) {
        String caminho = "C:\\ARQUIVOS_INTRANET\\COMERCIAL\\";
        anexopdf.setUrl(event.getFile().getFileName());
        try {
			SaveFile.criaArquivo(event, caminho);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }	
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();                
        context.addMessage(null, new FacesMessage(msg,""));          
    }
	
	public void limpaCadastro(){
		anexopdf = new AnexoPdf();
	}

	public AnexoPdf getAnexopdf() {
		return anexopdf;
	}

	public void setAnexopdf(AnexoPdf anexopdf) {
		this.anexopdf = anexopdf;
	}

	public List<AnexoPdf> getListAll() {
		return listAll;
	}

	public void setListAll(List<AnexoPdf> listAll) {
		this.listAll = listAll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
