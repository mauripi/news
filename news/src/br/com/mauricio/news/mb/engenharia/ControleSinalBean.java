package br.com.mauricio.news.mb.engenharia;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.engenharia.ControleSinalLN;
import br.com.mauricio.news.model.engenharia.ControleSinal;
import br.com.mauricio.news.model.engenharia.Posto;


@ManagedBean(name="controlesinalMB")
@SessionScoped
public class ControleSinalBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ControleSinal controlesinal = new ControleSinal();
	private ControleSinal controlesinalSel = new ControleSinal();
	private ControleSinalLN ln = new ControleSinalLN();
	private List<ControleSinal> controlesinais = new ArrayList<ControleSinal>();
	private int controlaCadastro = 0;
	private String msg;
	private Posto posto = new Posto();
	private List<Posto> postos = new ArrayList<Posto>();
	private List<String> erroValidacoes;
	private String nomeArquivo;
	private InputStream is;
	private StreamedContent fileDownload;
	private String CAMINHO_DO_ARQUIVO = "C:\\ARQUIVOS_INTRANET\\ENGENHARIA\\CONTROLESINAIS\\";
	private List<String> images;
	
	@PostConstruct
	public void init(){
		listar();
		listarPosto();
		limpaCadastro();
	}

	public void listar(){
		ln = new ControleSinalLN();
		controlesinais = ln.getList();
	}

	public void listarPosto(){
		GenericLN<Posto> gln = new GenericLN<Posto>();
		postos = gln.listWithoutRemoved("posto", "id");
	}
	
	public void novo(){
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(controlesinal.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			ln = new ControleSinalLN();
			msg = ln.delete(controlesinal);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			if(salvarArquivo())
				controlesinal.setArquivo(nomeArquivo);	
			controlesinal.setPosto(posto);
			ln = new ControleSinalLN();
			if(controlaCadastro==1){
				msg = ln.create(controlesinal);
			}
			if(controlaCadastro==2)	{	
				msg = ln.update(controlesinal);
			}
			mensagens(); 
			listar();
			limpaCadastro();	
		}else{
	        FacesContext context = FacesContext.getCurrentInstance();  	          
			for(String s:erroValidacoes)
				context.addMessage(null, new FacesMessage("",s));  
		}
	}
	
	private boolean validaCampos(){
		erroValidacoes = new ArrayList<String>();
		boolean v=true;
		if (posto.getId()==null){
			erroValidacoes.add("Selecione o posto.");
			v=false;
		}
		if (controlesinal.getTecnico().length()==0){
			erroValidacoes.add("Informe o técnico.");
			v=false;
		}
		if (controlesinal.getProblema().length()==0){
			erroValidacoes.add("Descreva o problema.");
			v=false;
		}
		if (controlesinal.getTipoequipamento().length()==0){
			erroValidacoes.add("Selecione o tipo de equipamento.");	
			v=false;
		}
		if (controlesinal.getTipomanutencao().length()==0){
			erroValidacoes.add("Informe o tipo de manutenção.");
			v=false;
		}
		return v;	
	}
	
	public void limpaCadastro(){
		controlesinalSel = controlesinal = new ControleSinal();
		erroValidacoes = new ArrayList<String>();
		posto = new Posto();
		controlaCadastro=0;
		images = new ArrayList<String>();
		listarPosto();
	}

    public List<Posto> completePosto(String query) {
        List<Posto> filtered = new ArrayList<Posto>(); 
        for (int i = 0; i < postos.size(); i++) 
            if(postos.get(i).getNome().toLowerCase().contains(query))
                filtered.add(postos.get(i));
        return filtered;
    }
    
	public void selecao(){
		controlesinal = controlesinalSel;
		posto=controlesinal.getPosto();
		controlaCadastro=0;
		exibirFotos();
		exibeArquivo();
		edita();
	}

	private void exibirFotos(){
		ln = new ControleSinalLN();
		images = ln.carregaFotosPosto(controlesinal, caminho()+"fotos\\");
	}
	
	private void exibeArquivo(){
		ln = new ControleSinalLN();
		ln.carregaArquivo(controlesinal,caminho());		
	}
	
	private String caminho(){
		return CAMINHO_DO_ARQUIVO +controlesinal.getPosto().getNome()+"\\"+controlesinal.getId()+"\\";
	}
	
	public void openImages(){
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("exibirfotoscontrolesinal.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean salvarArquivo(){
    	boolean check=false;
    	if(is!=null){
	    	ln = new ControleSinalLN();    
	    	msg = ln.recebeArquivoUpload(is,nomeArquivo,caminho());
	    	check=true;
	    	if(msg!=""){
	    		msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
	    		mensagens();
	    		check=false;
	    	}
    	}
		return check;
    }
    
	public void handleFileUpload(FileUploadEvent event) {
        nomeArquivo =  event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
		} catch (IOException e) {
			msg = "Ocorreu erro ao importar arquivo.";
			System.out.println("ControleSinalBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    }

    public void fotoPosto(FileUploadEvent event) {//grava foto
    	ln = new ControleSinalLN();
    	try {
        	msg=ln.recebeArquivoUpload(event.getFile().getInputstream(), event.getFile().getFileName(),caminho()+"fotos\\");
        	if(msg!=""){
        		msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
        		mensagens();
        	}
		} catch (IOException e) {
			msg = "Ocorreu erro ao importar arquivo.";
			System.out.println("ControleSinalBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    	exibirFotos();
    }   
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------
	public Date getToday() {
        return new Date();
    }

	public ControleSinal getControleSinal() {
		return controlesinal;
	}

	public ControleSinal getControlesinal() {
		return controlesinal;
	}

	public void setControlesinal(ControleSinal controlesinal) {
		this.controlesinal = controlesinal;
	}

	public ControleSinal getControlesinalSel() {
		return controlesinalSel;
	}

	public void setControlesinalSel(ControleSinal controlesinalSel) {
		this.controlesinalSel = controlesinalSel;
	}

	public ControleSinalLN getLn() {
		return ln;
	}

	public void setLn(ControleSinalLN ln) {
		this.ln = ln;
	}

	public List<ControleSinal> getControlesinais() {
		return controlesinais;
	}

	public void setControlesinais(List<ControleSinal> controlesinais) {
		this.controlesinais = controlesinais;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Posto getPosto() {
		return posto;
	}

	public void setPosto(Posto posto) {
		this.posto = posto;
	}

	public List<Posto> getPostos() {
		return postos;
	}

	public void setPostos(List<Posto> postos) {
		this.postos = postos;
	}

	public List<String> getErroValidacoes() {
		return erroValidacoes;
	}

	public void setErroValidacoes(List<String> erroValidacoes) {
		this.erroValidacoes = erroValidacoes;
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

	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getCAMINHO_DO_ARQUIVO() {
		return CAMINHO_DO_ARQUIVO;
	}

	public void setCAMINHO_DO_ARQUIVO(String cAMINHO_DO_ARQUIVO) {
		CAMINHO_DO_ARQUIVO = cAMINHO_DO_ARQUIVO;
	}

}
