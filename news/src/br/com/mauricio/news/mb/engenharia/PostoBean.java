package br.com.mauricio.news.mb.engenharia;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.engenharia.PostoLN;
import br.com.mauricio.news.model.engenharia.FotoPosto;
import br.com.mauricio.news.model.engenharia.Posto;


@ManagedBean(name="postoMB")
@ViewScoped
public class PostoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Posto posto = new Posto();
	private Posto postoSel = new Posto();
	private GenericLN<Posto> gln = new GenericLN<Posto>();
	private List<Posto> postos = new ArrayList<Posto>();
	private List<FotoPosto> fotos = new ArrayList<FotoPosto>();
	private String msg;
	private String CAMINHO_DO_ARQUIVO = "C:\\ARQUIVOS_INTRANET\\ENGENHARIA\\POSTO\\";
	private String nomeArquivo="";
	private InputStream is;
	private int controlaCadastro = 0;
	
	@PostConstruct
	public void init(){
		posto = new Posto();
		listar();	
	}

	public void novo(){
		posto = new Posto();
		controlaCadastro=1;
	}	

	public void edita(){
		controlaCadastro=2;
	}
	
	public void listar(){
		GenericLN<Posto> gln = new GenericLN<Posto>();
		postos = gln.listWithoutRemoved("posto", "id desc");
	}

	public void exclui(){
		if(posto.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<Posto>();
			msg = gln.remove(gln.find(posto, posto.getId()));
			posto = new Posto();
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){	
		if(validaCampos()){			
			PostoLN ln = new PostoLN();
			if(controlaCadastro==1)	{
				posto.setFotos(fotos);
				msg=ln.add(posto);				
			}		

			if(controlaCadastro==2)	{
				posto.getFotos().addAll(fotos);
				msg=ln.update(posto);				
			}		
			mensagens();
			limpaCadastro();	
			listar();
		}else{
			msg = "Favor preencha o nome do posto, pois é obrigatório.";
			mensagens();
		}
	}
	
	private boolean validaCampos(){	
		/*
		if(posto.getId()!=null)
			if(posto.getNome().length()==0)
				return false;
		*/
		return true;		
	}
	
	public void limpaCadastro(){
		posto =  new Posto();
		fotos = new ArrayList<FotoPosto>();
	}

	public void selecao(){
		posto=postoSel;
		fotos = new ArrayList<FotoPosto>();
		fotos = posto.getFotos();
		exibeArquivo();
	}
 
	private void exibeArquivo(){
		PostoLN ln = new PostoLN();
		ln.carregaArquivo(posto,caminho());		
	}
	
	private String caminho(){
		return CAMINHO_DO_ARQUIVO +posto.getNome()+"\\";
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		is=event.getFile().getInputstream();
    	if(is!=null){
    		nomeArquivo =  event.getFile().getFileName();
    		PostoLN ln = new PostoLN();    
			msg = ln.recebeArquivoUpload(is,nomeArquivo,caminho());
			FotoPosto foto = new FotoPosto();
			foto.setNome(nomeArquivo);
			if(posto.getId()!=null)
				foto.setPosto(posto);
			ln.salvarFoto(foto);
			fotos.add(foto);
			if(msg!=""){
				msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
				mensagens();
			}
		} else{
			System.out.println("is é null em ContratoBean.handleFileUpload(FileUploadEvent event)");
		}
    }

	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------

	public Posto getPosto() {
		return posto;
	}

	public void setPosto(Posto posto) {
		this.posto = posto;
	}

	public GenericLN<Posto> getGln() {
		return gln;
	}

	public void setGln(GenericLN<Posto> gln) {
		this.gln = gln;
	}

	public List<Posto> getPostos() {
		return postos;
	}

	public void setPostos(List<Posto> postos) {
		this.postos = postos;
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

	public String getCAMINHO_DO_ARQUIVO() {
		return CAMINHO_DO_ARQUIVO;
	}

	public void setCAMINHO_DO_ARQUIVO(String cAMINHO_DO_ARQUIVO) {
		CAMINHO_DO_ARQUIVO = cAMINHO_DO_ARQUIVO;
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

	public List<FotoPosto> getFotos() {
		return fotos;
	}

	public void setFotos(List<FotoPosto> fotos) {
		this.fotos = fotos;
	}

	public Posto getPostoSel() {
		return postoSel;
	}

	public void setPostoSel(Posto postoSel) {
		this.postoSel = postoSel;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}

}
