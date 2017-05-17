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
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.RequisicaoConhecimento;
import br.com.mauricio.news.model.rh.RequisicaoPessoa;
import br.com.mauricio.news.model.rh.reqpess.ConhFreq;
import br.com.mauricio.news.model.rh.reqpess.ConhNiv;

@ViewScoped
@ManagedBean(name="reqpessoaMB")
public class RequisicaoPessoaBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private Login usuarioLogado = new Login();
	private Login requisitante = new Login();
    private LoginLN loginLN;
    private RequisicaoPessoa requisicao = new RequisicaoPessoa();
	private List<Login> todosUsuarios = new ArrayList<Login>();
	private List<RequisicaoConhecimento> todosConhecimentos;
	private RequisicaoConhecimento conhecimento = new RequisicaoConhecimento();
	private List<CCusto> centroCustos;
	private CCusto centroCusto;
	private String sexo;
	private Integer frequencia;
	private int reqfreq;
	private int reqidade;
	private int reqexp;
	private int horext=0;
	private boolean idadeEntre;
	private boolean idadeInferior;
	private boolean idadeSuperior;
	
	@PostConstruct
	public void init(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuarioLogado = (Login) sessao.getAttribute("login");
		requisicao = new RequisicaoPessoa();
		requisicao.setReqges(usuarioLogado);
		requisicao.setReqccu(usuarioLogado.getCusto());
		frequenciaHoraExtra();
		listar();
	}

	private void listar(){
		GenericLN<RequisicaoConhecimento> gln = new GenericLN<RequisicaoConhecimento>();
		loginLN=new LoginLN();
		todosUsuarios=loginLN.listaUsuarios("nome");
		CCustoDao daoc = new CCustoDao();
		centroCustos = daoc.list();	
		todosConhecimentos = gln.listWithoutRemoved("reqconhecimento", "descricao");
	}
	
	public boolean frequenciaHoraExtra(){
		if(horext==1) return true;
		return false;
	}

	public void radioIdade(){
		System.out.println(reqidade);
	}
	
    public List<Login> completeTextRequisitante(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:todosUsuarios) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))results.add(l);  
        return results;
    }	

    public List<RequisicaoConhecimento> completeTextConhecimento(String query) {
    	List<RequisicaoConhecimento> results = new ArrayList<RequisicaoConhecimento>();
        for(RequisicaoConhecimento c:todosConhecimentos) 
        	if(c.getDescricao().toLowerCase().contains(query.toLowerCase()))results.add(c);  
        return results;
    }
    
    public void addConhecimento(){
    	requisicao.getConhecimentos().add(conhecimento);
    	conhecimento = new RequisicaoConhecimento();
    }
    
    public ConhFreq[] getConhFreq() {
        return ConhFreq.values();
    }
    
    public ConhNiv[] getConhNiv() {
        return ConhNiv.values();
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

	public RequisicaoPessoa getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(RequisicaoPessoa requisicao) {
		this.requisicao = requisicao;
	}

	public int getReqfreq() {
		return reqfreq;
	}

	public void setReqfreq(int reqfreq) {
		this.reqfreq = reqfreq;
	}

	public int getReqidade() {
		return reqidade;
	}

	public void setReqidade(int reqidade) {
		this.reqidade = reqidade;
	}

	public int getReqexp() {
		return reqexp;
	}

	public void setReqexp(int reqexp) {
		this.reqexp = reqexp;
	}

	public int getHorext() {
		return horext;
	}

	public void setHorext(int horext) {
		this.horext = horext;
	}

	public List<RequisicaoConhecimento> getTodosConhecimentos() {
		return todosConhecimentos;
	}

	public void setTodosConhecimentos(List<RequisicaoConhecimento> todosConhecimentos) {
		this.todosConhecimentos = todosConhecimentos;
	}

	public RequisicaoConhecimento getConhecimento() {
		return conhecimento;
	}

	public void setConhecimento(RequisicaoConhecimento conhecimento) {
		this.conhecimento = conhecimento;
	}

	public boolean isIdadeEntre() {
		return idadeEntre;
	}

	public void setIdadeEntre(boolean idadeEntre) {
		this.idadeEntre = idadeEntre;
	}

	public boolean isIdadeInferior() {
		return idadeInferior;
	}

	public void setIdadeInferior(boolean idadeInferior) {
		this.idadeInferior = idadeInferior;
	}

	public boolean isIdadeSuperior() {
		return idadeSuperior;
	}

	public void setIdadeSuperior(boolean idadeSuperior) {
		this.idadeSuperior = idadeSuperior;
	}	
	
}
