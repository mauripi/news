package br.com.mauricio.news.mb.ti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.ti.Atendente;

@ManagedBean(name="atendenteMB")
@ViewScoped
public class AtendenteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Atendente atendente = new Atendente();
	private Atendente atendenteSel = new Atendente();
	private GenericLN<Atendente> gln = new GenericLN<Atendente>();
	private List<Atendente> atendentes = new ArrayList<Atendente>();
	private int controlaCadastro = 0;
	private String msg;
	private List<Login> todosUsuarios = new ArrayList<Login>();
	private Login usuario = new Login();
	
	@PostConstruct
	public void init(){
		atendente = new Atendente();
		listar();		
	}

	public void listar(){
		GenericLN<Atendente> gln = new GenericLN<Atendente>();
		GenericLN<Login> lln = new GenericLN<Login>();
		atendentes = gln.listWithoutRemoved("atendente", "id");
		todosUsuarios = lln.listAll(new Login(), "id");
	}

	public void novo(){
		atendente = new Atendente();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(atendenteSel.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<Atendente>();
			msg = gln.update(atendenteSel);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}

	
	public void grava(){	
		if(validaCampos()){
			gln = new GenericLN<Atendente>();
			if(controlaCadastro==1){
				atendente = new Atendente();
				atendente.setLogin(usuario);
				msg = gln.add(atendente);
			}
			if(controlaCadastro==2)	{	
				msg = gln.update(atendente);
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
		if(usuario.getId()==null)
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		atendente = atendenteSel = new Atendente();
		controlaCadastro=0;
	}

	public void selecao(){
		atendente = atendenteSel;
		usuario = atendente.getLogin();
		controlaCadastro=0;
		edita();
	}

    public List<Login> completeText(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:todosUsuarios) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(l);     
        return results;
    }
  
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------

	public Atendente getAtendente() {
		return atendente;
	}

	public void setAtendente(Atendente atendente) {
		this.atendente = atendente;
	}

	public Atendente getAtendenteSel() {
		return atendenteSel;
	}

	public void setAtendenteSel(Atendente atendenteSel) {
		this.atendenteSel = atendenteSel;
	}

	public GenericLN<Atendente> getGln() {
		return gln;
	}

	public void setGln(GenericLN<Atendente> gln) {
		this.gln = gln;
	}

	public List<Atendente> getAtendentes() {
		return atendentes;
	}

	public void setAtendentes(List<Atendente> atendentes) {
		this.atendentes = atendentes;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Login> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(List<Login> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}

	public Login getUsuario() {
		return usuario;
	}

	public void setUsuario(Login usuario) {
		this.usuario = usuario;
	}

}
