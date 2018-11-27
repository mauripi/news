package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.ti.RamalLN;
import br.com.mauricio.news.model.Ramal;
import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.DeptoRamal;
import br.com.mauricio.news.model.Login;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@ManagedBean (name="ramalMB")
@ViewScoped
public class RamalBean implements Serializable {


	private static final long serialVersionUID = 1L;
	private int controlaCadastro = 0;
	private Ramal ramal;
	private List<Ramal> ramais;
	private DeptoRamal depto;
	private List<DeptoRamal> deptos;
	private List<Login> userlogins;
	private Login usu;
	private List<String> msgs;
	private String msg;
	private CCusto ccusto;
	private List<CCusto> ccustos = new ArrayList<CCusto>();
	

	@PostConstruct
	public void init(){
		lista();
		listaDeptos();
		listaCCustos();
	}
		
    private void listaCCustos() {
		GenericLN<CCusto> gln = new GenericLN<CCusto>();
		ccustos = gln.listWithoutRemoved("ccusto", "id");
		
	}

	public List<Login> completeText(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:userlogins) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(l);     
        return results;
    }
	
	public void novo(){
		ramal = new Ramal();
		controlaCadastro = 1;
	}

	public void edita(){
		controlaCadastro = 2;
	}

	public void lista(){
		GenericLN<Ramal> gln = new GenericLN<Ramal>();
		ramais = gln.listWithoutRemoved("ramal", "id");
	}	
	
	public void listaDeptos(){
		GenericLN<DeptoRamal> gln = new GenericLN<DeptoRamal>();
		deptos = gln.listWithoutRemoved("deptoramal", "id");
	}	
	
	public void exclui (){
		RamalLN ln = new RamalLN();
		msg = ln.excluir(ramal);
		System.out.println(msg);
		mensagens();
		lista();
	}
	
	public void limpaCadastro(){
		ramal = new Ramal();
		controlaCadastro = 0;
		lista();
	}
	
	public void grava(){
		ramal.setDepto(depto);
		GenericLN<Ramal> gln = new GenericLN<Ramal>();
		if(validar()){
			ramal.setCcusto(ccusto);
			if(controlaCadastro==1) msg = gln.add(ramal);
			if(controlaCadastro==2) msg = gln.update(ramal);
			
			mensagens();
			limpaCadastro();
		}
	}
	
	public void selecao(SelectEvent event){
		ramal=(Ramal) event.getObject();
		depto=ramal.getDepto();
		ccusto=ramal.getCcusto();
		edita();
	}
	
	private boolean validar() {
		boolean isValid = true;
		if(ramal.getNome().length() < 1){
			isValid = false;
			msgs.add("O campo Nome não pode estar vazio.");
		}
		return isValid;
	}

    @SuppressWarnings("unused")
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
            context.addMessage(null, new FacesMessage(m,m));              
    }
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();                
        context.addMessage(null, new FacesMessage(msg,""));          
    }

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}

	public Ramal getRamal() {
		return ramal;
	}

	public void setRamal(Ramal ramal) {
		this.ramal = ramal;
	}

	public List<Ramal> getRamais() {
		return ramais;
	}

	public void setRamais(List<Ramal> ramais) {
		this.ramais = ramais;
	}

	public DeptoRamal getDepto() {
		return depto;
	}

	public void setDepto(DeptoRamal depto) {
		this.depto = depto;
	}

	public List<DeptoRamal> getDeptos() {
		return deptos;
	}

	public void setDeptos(List<DeptoRamal> deptos) {
		this.deptos = deptos;
	}

	public List<Login> getUserlogins() {
		return userlogins;
	}

	public void setUserlogins(List<Login> userlogins) {
		this.userlogins = userlogins;
	}

	public Login getUsu() {
		return usu;
	}

	public void setUsu(Login usu) {
		this.usu = usu;
	}

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public CCusto getCcusto() {
		return ccusto;
	}

	public void setCcusto(CCusto ccusto) {
		this.ccusto = ccusto;
	}

	public List<CCusto> getCcustos() {
		return ccustos;
	}

	public void setCcustos(List<CCusto> ccustos) {
		this.ccustos = ccustos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}




}
