package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.DeptoRamal;
import br.com.mauricio.news.model.LocalRamal;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@ManagedBean (name="deptoramalMB")
@ViewScoped
public class DeptoRamalBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int controlaCadastro = 0;
	private DeptoRamal depto;
	private List<DeptoRamal> deptos;
	private LocalRamal local;
	private List<LocalRamal> locais;
	private List<String> msgs;
	private String msg;
	

	@PostConstruct
	public void init(){
		lista();
		listaLocais();
	}
		
	public void novo(){
		depto = new DeptoRamal();
		controlaCadastro = 1;
	}

	public void edita(){
		controlaCadastro = 2;
	}

	public void lista(){
		GenericLN<DeptoRamal> gln = new GenericLN<DeptoRamal>();
		deptos = gln.listWithoutRemoved("deptoramal", "id");
	}	
	
	public void listaLocais(){
		GenericLN<LocalRamal> gln = new GenericLN<LocalRamal>();
		locais = gln.listWithoutRemoved("localramal", "id");
	}
	
	public void exclui (){
		GenericLN<DeptoRamal> gln = new GenericLN<DeptoRamal>();
		msg = gln.remove(gln.find(new DeptoRamal(), depto.getId()));
		mensagens();
		lista();
	}
	
	public void limpaCadastro(){
		depto = new DeptoRamal();
		controlaCadastro = 0;
		lista();
	}
	
	public void grava(){
		depto.setLocal(local);
		GenericLN<DeptoRamal> gln = new GenericLN<DeptoRamal>();
		if(validar()){
			if(controlaCadastro==1) msg = gln.add(depto);
			if(controlaCadastro==2) msg = gln.update(depto);
			
			mensagens();
			limpaCadastro();
		}
	}
	
	public void selecao(SelectEvent event){
		depto=(DeptoRamal) event.getObject();
		local=depto.getLocal();
		edita();
	}
	
	private boolean validar() {
		boolean isValid = true;
		if(depto.getNome().length() < 1){
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

	public LocalRamal getLocal() {
		return local;
	}

	public void setLocal(LocalRamal local) {
		this.local = local;
	}

	public List<LocalRamal> getLocais() {
		return locais;
	}

	public void setLocais(List<LocalRamal> locais) {
		this.locais = locais;
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
}
