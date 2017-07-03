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
import br.com.mauricio.news.model.EmpresaRamal;
import br.com.mauricio.news.model.LocalRamal;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@ManagedBean (name="localramalMB")
@ViewScoped
public class LocalRamalBean  implements Serializable{

	private static final long serialVersionUID = 1L;
	private int controlaCadastro = 0;
	private LocalRamal local;
	private List<LocalRamal> locais;
	private EmpresaRamal empresa;
	private List<EmpresaRamal> empresas;
	private List<String> msgs;
	private String msg;
	

	@PostConstruct
	public void init(){
		lista();
		listaEmpresas();
	}
		
	public void novo(){
		local = new LocalRamal();
		controlaCadastro = 1;
	}

	public void edita(){
		controlaCadastro = 2;
	}

	public void listaEmpresas(){
		GenericLN<EmpresaRamal> gln = new GenericLN<EmpresaRamal>();
		empresas = gln.listWithoutRemoved("empresaramal", "id");
	}	
	
	public void lista(){
		GenericLN<LocalRamal> gln = new GenericLN<LocalRamal>();
		locais = gln.listWithoutRemoved("localramal", "id");
	}	
	
	public void exclui (){
		GenericLN<LocalRamal> gln = new GenericLN<LocalRamal>();
		msg = gln.remove(gln.find(new LocalRamal(), local.getId()));
		mensagens();
		lista();
	}
	
	public void limpaCadastro(){
		local = new LocalRamal();
		controlaCadastro = 0;
		lista();
	}
	
	public void grava(){
		local.setEmpresa(empresa);
		GenericLN<LocalRamal> gln = new GenericLN<LocalRamal>();
		if(validar()){
			if(controlaCadastro==1) msg = gln.add(local);
			if(controlaCadastro==2) msg = gln.update(local);
			
			mensagens();
			limpaCadastro();
		}
	}
	
	public void selecao(SelectEvent event){
		local=(LocalRamal) event.getObject();
		empresa=local.getEmpresa();
		edita();
	}
	
	private boolean validar() {
		boolean isValid = true;
		if(local.getNome().length() < 1){
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

	public EmpresaRamal getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaRamal empresa) {
		this.empresa = empresa;
	}

	public List<EmpresaRamal> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<EmpresaRamal> empresas) {
		this.empresas = empresas;
	}
}
