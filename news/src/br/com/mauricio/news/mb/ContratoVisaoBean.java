package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import br.com.mauricio.news.ln.ContratoLN;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.util.DateUtil;

@ManagedBean(name="contratoVisaoMB")
@ViewScoped
public class ContratoVisaoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Contrato contrato = new Contrato();
    private List<Contrato> listAll = new ArrayList<Contrato>();
    private List<Contrato> contratosPesquisados = new ArrayList<Contrato>();
    private List<Contrato> listVlrZerados = new ArrayList<Contrato>();
    private List<Contrato> listVlrZeradosReceita = new ArrayList<Contrato>();
    private List<Contrato> listVlrZeradosDespesa = new ArrayList<Contrato>();
    private List<Contrato> listComVlr = new ArrayList<Contrato>();
    private List<Contrato> listComVlrReceita = new ArrayList<Contrato>();
    private List<Contrato> listComVlrDespesa = new ArrayList<Contrato>();   
    private Login userLogado = new Login();
    private List<String> anexos;
    private String anexo;
    private String pesquisa;
    private String pathPdfAnexo="";
    
    @PostConstruct
    public void init(){
        contrato = new Contrato();
        listar();
        usuarioLogado();
    }

	private void usuarioLogado(){
        FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
        userLogado = (Login) sessao.getAttribute("login");
    }

    private void listar(){
        ContratoLN ln = new ContratoLN();
        listAll = ln.listAtivo();
    }

    public void pesquisar(){
    	contratosPesquisados = listAll.stream()
		        .filter(c -> c.toString().toLowerCase().contains(pesquisa.toLowerCase()))
		        .collect(Collectors.toList());
    }

    public void onRowSelect(SelectEvent event){
    	contrato=(Contrato) event.getObject();
        ContratoLN ln = new ContratoLN();
        anexos = ln.getAnexos(contrato);
    }
    
    public void visualizar(){
    	System.out.println(contrato.getId());
    }
    
    public void montaCaminho(){
        pathPdfAnexo = ""+contrato.getId()+"/"+anexo;
    }
    
    public String diasParaTermino(Date fim){
    	return DateUtil.diferencaEmDias(new Date(), fim)+""; 	
    }
    
     public void mensagens(String msg){
        FacesContext context = FacesContext.getCurrentInstance();                
        context.addMessage(null, new FacesMessage(msg,""));          
    }  
    //--------------------------GETTERS E SETTERS ----------------------------------

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public List<Contrato> getListAll() {
		return listAll;
	}

	public void setListAll(List<Contrato> listAll) {
		this.listAll = listAll;
	}

	public List<Contrato> getListVlrZerados() {
		return listVlrZerados;
	}

	public void setListVlrZerados(List<Contrato> listVlrZerados) {
		this.listVlrZerados = listVlrZerados;
	}

	public List<Contrato> getListVlrZeradosReceita() {
		return listVlrZeradosReceita;
	}

	public void setListVlrZeradosReceita(List<Contrato> listVlrZeradosReceita) {
		this.listVlrZeradosReceita = listVlrZeradosReceita;
	}

	public List<Contrato> getListVlrZeradosDespesa() {
		return listVlrZeradosDespesa;
	}

	public void setListVlrZeradosDespesa(List<Contrato> listVlrZeradosDespesa) {
		this.listVlrZeradosDespesa = listVlrZeradosDespesa;
	}

	public List<Contrato> getListComVlr() {
		return listComVlr;
	}

	public void setListComVlr(List<Contrato> listComVlr) {
		this.listComVlr = listComVlr;
	}

	public List<Contrato> getListComVlrReceita() {
		return listComVlrReceita;
	}

	public void setListComVlrReceita(List<Contrato> listComVlrReceita) {
		this.listComVlrReceita = listComVlrReceita;
	}

	public List<Contrato> getListComVlrDespesa() {
		return listComVlrDespesa;
	}

	public void setListComVlrDespesa(List<Contrato> listComVlrDespesa) {
		this.listComVlrDespesa = listComVlrDespesa;
	}

	public Login getUserLogado() {
		return userLogado;
	}

	public void setUserLogado(Login userLogado) {
		this.userLogado = userLogado;
	}

	public List<String> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<String> anexos) {
		this.anexos = anexos;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public List<Contrato> getContratosPesquisados() {
		return contratosPesquisados;
	}

	public void setContratosPesquisados(List<Contrato> contratosPesquisados) {
		this.contratosPesquisados = contratosPesquisados;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public String getPathPdfAnexo() {
		return pathPdfAnexo;
	}

	public void setPathPdfAnexo(String pathPdfAnexo) {
		this.pathPdfAnexo = pathPdfAnexo;
	}
	
}