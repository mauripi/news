package br.com.mauricio.news.mb.compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.compra.ContatoFor;
import br.com.mauricio.news.model.compra.Fornecedor;
import br.com.mauricio.news.util.UsuarioLogado;

@ManagedBean(name="fornecedorMB")
@ViewScoped
public class FornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Fornecedor forn = new Fornecedor();
	private Fornecedor fornSel = new Fornecedor();
	private GenericLN<Fornecedor> gln = new GenericLN<Fornecedor>();
	private List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
	private int controlaCadastro = 0;
	private String msg;
	private List<ContatoFor> contatos = new ArrayList<ContatoFor>();
	
	@PostConstruct
	public void init(){
		forn = new Fornecedor();
		listar();		
	}

	public void listar(){
		GenericLN<Fornecedor> gln = new GenericLN<Fornecedor>();
		fornecedores = gln.listWithoutRemoved("fornecedor", "nome");
	}

	public void novo(){
		forn = new Fornecedor();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(forn.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<Fornecedor>();
			forn.setRemovido(1);
			forn.setDataAlteracao(new Date(System.currentTimeMillis()));
			forn.setUsuarioAlteracao(UsuarioLogado.getUser());
			msg = gln.update(forn);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}

	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<Fornecedor>();
			if(controlaCadastro==1){
				forn.setDataCriacao(new Date(System.currentTimeMillis()));			
				forn.setUsuarioCriacao(UsuarioLogado.getUser());
				msg = gln.add(forn);
			}
			if(controlaCadastro==2)	{	
				forn.setContatos(new ArrayList<ContatoFor>());
				forn.getContatos().addAll(contatos);
				forn.setDataAlteracao(new Date(System.currentTimeMillis()));
				forn.setUsuarioAlteracao(UsuarioLogado.getUser());	
				msg = gln.update(forn);
			}
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	private boolean validaCampos(){
		if(forn.getNome()=="")
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		forn = fornSel = new Fornecedor();
		contatos = new ArrayList<ContatoFor>();
		controlaCadastro=0;
	}

	public void selecao(){
		forn = fornSel;
		contatos = new ArrayList<ContatoFor>();
		contatos.addAll(forn.getContatos());
		controlaCadastro=0;
	}
	
	public void newContact(){
		ContatoFor f = new ContatoFor();
		if(getContatos()==null){
			setContatos(new ArrayList<ContatoFor>());
			f.setFornecedor(forn);
			getContatos().add(f);
		}else{
			f.setFornecedor(forn);
			getContatos().add(f);
		}
	}
	
    public void onCellEdit(CellEditEvent event) {

    }	
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------

	public Fornecedor getForn() {
		return forn;
	}

	public void setForn(Fornecedor forn) {
		this.forn = forn;
	}

	public Fornecedor getFornSel() {
		return fornSel;
	}

	public void setFornSel(Fornecedor fornSel) {
		this.fornSel = fornSel;
	}

	public GenericLN<Fornecedor> getGln() {
		return gln;
	}

	public void setGln(GenericLN<Fornecedor> gln) {
		this.gln = gln;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
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

	public List<ContatoFor> getContatos() {
		return contatos;
	}

	public void setContatos(List<ContatoFor> contatos) {
		this.contatos = contatos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
