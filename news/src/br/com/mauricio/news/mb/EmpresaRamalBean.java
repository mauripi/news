package br.com.mauricio.news.mb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.EmpresaRamal;

/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean (name="empresaramalMB")
@ViewScoped
public class EmpresaRamalBean {

	private int controlaCadastro = 0;
	private EmpresaRamal empresa;
	private List<EmpresaRamal> empresas;
	private List<String> msgs;
	private String msg;
	

	@PostConstruct
	public void init(){
		lista();
	}
		
	public void novo(){
		empresa = new EmpresaRamal();
		controlaCadastro = 1;
	}

	public void edita(){
		controlaCadastro = 2;
	}

	public void lista(){
		GenericLN<EmpresaRamal> gln = new GenericLN<EmpresaRamal>();
		empresas = gln.listWithoutRemoved("empresaramal", "id");
	}	
	
	public void exclui (){
		GenericLN<EmpresaRamal> gln = new GenericLN<EmpresaRamal>();
		msg = gln.remove(gln.find(new EmpresaRamal(), empresa.getId()));
		mensagens();
		lista();
	}
	
	public void limpaCadastro(){
		empresa = new EmpresaRamal();
		controlaCadastro = 0;
		lista();
	}
	
	public void grava(){
		GenericLN<EmpresaRamal> gln = new GenericLN<EmpresaRamal>();
		if(validar()){
			if(controlaCadastro==1) msg = gln.add(empresa);
			if(controlaCadastro==2) msg = gln.update(empresa);
			
			mensagens();
			limpaCadastro();
		}
	}
	
	public void selecao(SelectEvent event){
		empresa=(EmpresaRamal) event.getObject();
		edita();
	}
	
	private boolean validar() {
		boolean isValid = true;
		if(empresa.getNome().length() < 1){
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
