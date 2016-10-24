package br.com.mauricio.news.mb.contabil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.contabil.ConsolidadoLN;
import br.com.mauricio.news.model.contabil.Consolidado;
import br.com.mauricio.news.model.contabil.HistoricoConsolidado;
import br.com.mauricio.news.model.contabil.PlanoConsolidado;

@ManagedBean(name="historicoconsolidadoMB")
@SessionScoped
public class HistoricoConsolidadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer mes=0;
	private Integer ano=2016;
	private Integer clacta;
	private PlanoConsolidado planoSelecionado = new PlanoConsolidado();
	private String msg;
	private String descricaoRazao;
	private String historico;
	private ConsolidadoLN ln;
	private List<PlanoConsolidado> plano = new ArrayList<PlanoConsolidado>();
	private List<Consolidado> contasObservacao = new ArrayList<Consolidado>();
	private List<HistoricoConsolidado> historicos;
	private HistoricoConsolidado historicoSelecionado = new HistoricoConsolidado();
	private Consolidado contaSelecionada = new Consolidado();

	
	@PostConstruct
	public void init(){
		ln=new ConsolidadoLN();
		plano=ln.getListPlanoConsolidado();
		historicos = ln.listaHistoricoPorMesAno(mes,ano);
	}

	public void observacaoConta(int conta) {
    	ln=new ConsolidadoLN();
    	contasObservacao=ln.findByConta(conta,ano);  
    	if(contasObservacao.size()>0)
    		contaSelecionada=contasObservacao.get(0);
    	else
    		contaSelecionada=new Consolidado();
    }	
    
	public String buscaDescricao(int conta){
		for(PlanoConsolidado p:plano){
			if(p.getClacta().equals(conta))
				return p.getDescta();
		}
		return "";
	}
	
     public List<PlanoConsolidado> completeText(String query) {
    	historico="";
    	historicoSelecionado = new HistoricoConsolidado();
    	contaSelecionada = new Consolidado();
        List<PlanoConsolidado> results = new ArrayList<PlanoConsolidado>();
        for(PlanoConsolidado p:plano) 
        	if(p.getDescta().toLowerCase().contains(query.toLowerCase()))
        		results.add(p);     
        return results;
    }
  
	public void localizaSolicitacao(){
		contaSelecionada = new Consolidado();
		ln=new ConsolidadoLN();
		int conta = planoSelecionado.getClacta();
		contaSelecionada=ln.findByContaMesAno(conta,mes,ano);
		if(contaSelecionada.getId()==null){
			msg="Lançamento não localizado!";
			mensagens();
		}else{
			historicoSelecionado = ln.findByHistoricoContaMesAno(contaSelecionada.getClacta(),mes,ano);
			if(historicoSelecionado!=null)
				historico = historicoSelecionado.getObservacao();
			else
				historico="";
		}
			
	}

	public void gravarObservacao(){
		GenericLN<HistoricoConsolidado> gln = new GenericLN<HistoricoConsolidado>();
		if(historicoSelecionado!=null){
			historicoSelecionado.setObservacao(historico);
			msg=gln.update(historicoSelecionado);
		}else{
			HistoricoConsolidado h = new HistoricoConsolidado();
			h.setClacta(contaSelecionada.getClacta());
			h.setAno(ano);
			h.setMes(mes);
			h.setObservacao(historico);
			msg=gln.add(h);
		}
		contaSelecionada = new Consolidado();
		mensagens();
	}	
	
	public void remove(){
		GenericLN<HistoricoConsolidado> gln = new GenericLN<HistoricoConsolidado>();
		if(historicoSelecionado!=null)
			msg=gln.remove(historicoSelecionado);
		mensagens();
	}

    public void onRowSelect(SelectEvent event) {
        historicoSelecionado = (HistoricoConsolidado) event.getObject();
        clacta = historicoSelecionado.getClacta();
    }
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
 //========================================================================================================================	
	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ConsolidadoLN getLn() {
		return ln;
	}

	public void setLn(ConsolidadoLN ln) {
		this.ln = ln;
	}

	public List<PlanoConsolidado> getPlano() {
		return plano;
	}

	public void setPlano(List<PlanoConsolidado> plano) {
		this.plano = plano;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Consolidado getContaSelecionada() {
		return contaSelecionada;
	}

	public void setContaSelecionada(Consolidado contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

	public List<Consolidado> getContasObservacao() {
		return contasObservacao;
	}

	public void setContasObservacao(List<Consolidado> contasObservacao) {
		this.contasObservacao = contasObservacao;
	}

	public PlanoConsolidado getPlanoSelecionado() {
		return planoSelecionado;
	}

	public void setPlanoSelecionado(PlanoConsolidado planoSelecionado) {
		this.planoSelecionado = planoSelecionado;
	}

	public String getDescricaoRazao() {
		return descricaoRazao;
	}

	public void setDescricaoRazao(String descricaoRazao) {
		this.descricaoRazao = descricaoRazao;
	}

	public List<HistoricoConsolidado> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<HistoricoConsolidado> historicos) {
		this.historicos = historicos;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public HistoricoConsolidado getHistoricoSelecionado() {
		return historicoSelecionado;
	}

	public void setHistoricoSelecionado(HistoricoConsolidado historicoSelecionado) {
		this.historicoSelecionado = historicoSelecionado;
	}

	public Integer getClacta() {
		return clacta;
	}

	public void setClacta(Integer clacta) {
		this.clacta = clacta;
	}

}
