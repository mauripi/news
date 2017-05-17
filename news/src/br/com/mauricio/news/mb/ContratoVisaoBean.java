package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import br.com.mauricio.news.ln.ContratoLN;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.model.Login;

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
    
    private DashboardModel model;
    
	
    @PostConstruct
    public void init(){
        contrato = new Contrato();
        usuarioLogado();       
        changeDatas();
        createDashboard();
    }
    
    private void createDashboard() {
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
        DashboardColumn column4 = new DefaultDashboardColumn();
        column1.addWidget("contratos");
        column1.addWidget("grafico_geral");
        
        column2.addWidget("receita_despesa");        
        column2.addWidget("sem_valores");

        column3.addWidget("receita_despesa3");        
        column3.addWidget("sem_valores3");
        
        column4.addWidget("receita_despesa4");        
        column4.addWidget("sem_valores4");
        
        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
        model.addColumn(column4);
	}

	public void changeDatas() {
    	listar();
		for(Contrato c : listAll){
			if(c.getValorMensal()==0 || c.getValorTotal()==0){
				if (c.getOrigem().equals("V")) listVlrZeradosReceita.add(c);
				else listVlrZeradosDespesa.add(c);
				listVlrZerados.add(c);
			}else{
				if (c.getOrigem().equals("V")) listComVlrReceita.add(c);
				else listComVlrDespesa.add(c);
				listComVlr.add(c);
			}
		}
		System.out.println("Total de Contratos Ativos: " + listAll.size());
		System.out.println("Total de Contratos de Receita: " + (listComVlrReceita.size()  +  listVlrZeradosReceita.size()));
		System.out.println("Total de Contratos de Receita sem Valores: " + listVlrZeradosReceita.size());
		System.out.println("Total de Contratos de Receita com Valores: " + listComVlrReceita.size());		
		System.out.println("Total de Contratos de Despesa: " + (listComVlrDespesa.size()  +  listVlrZeradosDespesa.size()));
		System.out.println("Total de Contratos de Despesa sem Valores: " + listVlrZeradosDespesa.size());
		System.out.println("Total de Contratos de Despesa com Valores: " + listComVlrDespesa.size());
		
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

	public DashboardModel getModel() {
		return model;
	}

	public void setModel(DashboardModel model) {
		this.model = model;
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
	
}