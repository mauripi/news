package br.com.mauricio.news.mb.rh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.mauricio.news.dao.CCustoDao;
import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Login;

@ViewScoped
@ManagedBean(name="reqpessoaMB")
public class RequisicaoPessoaBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private Login usuarioLogado = new Login();
	private Login requisitante = new Login();
    private LoginLN loginLN;
	private List<Login> todosUsuarios = new ArrayList<Login>();
	private List<CCusto> centroCustos;
	private CCusto centroCusto;
	private String sexo;
	private Integer frequencia;
	
	@PostConstruct
	public void init(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuarioLogado = (Login) sessao.getAttribute("login");
		listar();
	}

	private void listar(){
		loginLN=new LoginLN();
		todosUsuarios=loginLN.listaUsuarios("nome");
		CCustoDao daoc = new CCustoDao();
		centroCustos = daoc.list();		
	}
	
    public List<Login> completeTextRequisitante(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:todosUsuarios) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))results.add(l);  
        return results;
    }	
    
	public Date getToday() {
        return new Date();
    }

	
	//==========GTST==============
	public Login getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Login usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Login getRequisitante() {
		return requisitante;
	}

	public void setRequisitante(Login requisitante) {
		this.requisitante = requisitante;
	}

	public LoginLN getLoginLN() {
		return loginLN;
	}

	public void setLoginLN(LoginLN loginLN) {
		this.loginLN = loginLN;
	}

	public List<Login> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(List<Login> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}

	public List<CCusto> getCentroCustos() {
		return centroCustos;
	}

	public void setCentroCustos(List<CCusto> centroCustos) {
		this.centroCustos = centroCustos;
	}

	public CCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}	
	
}
