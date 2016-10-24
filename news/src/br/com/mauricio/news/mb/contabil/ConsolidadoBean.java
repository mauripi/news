package br.com.mauricio.news.mb.contabil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.contabil.ConsolidadoLN;
import br.com.mauricio.news.model.contabil.Consolidado;
import br.com.mauricio.news.model.contabil.HistoricoConsolidado;
import br.com.mauricio.news.model.contabil.PlanoConsolidado;
import br.com.mauricio.news.model.contabil.Razao;
import br.com.mauricio.news.model.contabil.ViewConsolidado;

@ManagedBean(name="consolidadoMB")
@SessionScoped
public class ConsolidadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer mes=0;
	private Integer ano=2016;
	private String msg;
	private String descricaoRazao;
	private Double totalRazao=0.0;
	private ConsolidadoLN ln;
	private List<PlanoConsolidado> plano = new ArrayList<PlanoConsolidado>();
	private List<HistoricoConsolidado> contasObservacao = new ArrayList<HistoricoConsolidado>();
	private List<ViewConsolidado> views;
	private List<HistoricoConsolidado> historicos;
	private List<ViewConsolidado> totais;
	private List<ViewConsolidado> viewsReceita;
	private List<ViewConsolidado> viewsDespesa;
	private ViewConsolidado viewSelecionada = new ViewConsolidado();
	private Consolidado contaSelecionada = new Consolidado();
	private PlanoConsolidado planoSelecionado = new PlanoConsolidado();
	private List<Razao> razoes = new ArrayList<Razao>();

	
	@PostConstruct
	public void init(){
		totais = new ArrayList<ViewConsolidado>();
		ln=new ConsolidadoLN();
		plano=ln.getListPlanoConsolidado();
		views=ln.view(ano);
		historicos = ln.listaHistoricoPorMesAno(mes,ano);
		separaReceitaDespesa();
		totalizaReceitaDespesa();
	}
	
	public String buscaDescricao(int conta){
		for(PlanoConsolidado p:plano){
			if(p.getClacta().equals(conta))
				return p.getDescta();
		}
		return "";
	}

	public String buscaDescricaoTotais(int i){;
       switch (i) {
           case 3:
        	   return "Totais de Receitas";
           case 4:
        	   return "Totais de Despesas";
           case 5:
        	   return "Saldo Total";
           case 6:
        	   return "Acumulado";        	   
       }
	   return "";
	}
	
	public void atualizaPeriodo(){
		if(mes.equals(0)){
			msg="Selecione um mês.";
			mensagens();
		}else{
			ln=new ConsolidadoLN();
			msg=ln.importaConsolidado(mes,ano);
			views=ln.view(ano);
			separaReceitaDespesa();
			mensagens();
		}
	}
		
    private void separaReceitaDespesa() {
    	viewsDespesa = new ArrayList<ViewConsolidado>();
    	viewsReceita = new ArrayList<ViewConsolidado>();
    	for(ViewConsolidado v : views)
    		if(v.getClacta().toString().substring(0, 1).equals("3"))
    			viewsReceita.add(v);
    		else
    			viewsDespesa.add(v);   				
	}

    private void totalizaReceitaDespesa() {
    	
		ViewConsolidado totalReceita = new  ViewConsolidado();
		ViewConsolidado totalDespesa = new ViewConsolidado();
		ViewConsolidado saldo = new ViewConsolidado();
		ViewConsolidado acumulado = new ViewConsolidado();
		
		totalReceita.setClacta(3);
		totalDespesa.setClacta(4);
		saldo.setClacta(5);
		acumulado.setClacta(6);
		
    	for(ViewConsolidado v : views){
    		if(v.getClacta().toString().substring(0, 1).equals("3")){
    			totalReceita.setJaneiro(totalReceita.getJaneiro()+v.getJaneiro());
    			totalReceita.setFevereiro(totalReceita.getFevereiro()+v.getFevereiro());
    			totalReceita.setMarco(totalReceita.getMarco()+v.getMarco());
    			totalReceita.setAbril(totalReceita.getAbril()+v.getAbril());
    			totalReceita.setMaio(totalReceita.getMaio()+v.getMaio());
    			totalReceita.setJunho(totalReceita.getJunho()+v.getJunho());
    			totalReceita.setJulho(totalReceita.getJulho()+v.getJulho());
    			totalReceita.setAgosto(totalReceita.getAgosto()+v.getAgosto());
    			totalReceita.setSetembro(totalReceita.getSetembro()+v.getSetembro());
    			totalReceita.setOutubro(totalReceita.getOutubro()+v.getOutubro());
    			totalReceita.setNovembro(totalReceita.getNovembro()+v.getNovembro());
    			totalReceita.setDezembro(totalReceita.getDezembro()+v.getDezembro());
    			
    		}else{
    			totalDespesa.setJaneiro(totalDespesa.getJaneiro()+v.getJaneiro());
    			totalDespesa.setFevereiro(totalDespesa.getFevereiro()+v.getFevereiro());
    			totalDespesa.setMarco(totalDespesa.getMarco()+v.getMarco());
    			totalDespesa.setAbril(totalDespesa.getAbril()+v.getAbril());
    			totalDespesa.setMaio(totalDespesa.getMaio()+v.getMaio());
    			totalDespesa.setJunho(totalDespesa.getJunho()+v.getJunho());
    			totalDespesa.setJulho(totalDespesa.getJulho()+v.getJulho());
    			totalDespesa.setAgosto(totalDespesa.getAgosto()+v.getAgosto());
    			totalDespesa.setSetembro(totalDespesa.getSetembro()+v.getSetembro());
    			totalDespesa.setOutubro(totalDespesa.getOutubro()+v.getOutubro());
    			totalDespesa.setNovembro(totalDespesa.getNovembro()+v.getNovembro());
    			totalDespesa.setDezembro(totalDespesa.getDezembro()+v.getDezembro());   			
    		}
    		saldo.setJaneiro(totalReceita.getJaneiro()-totalDespesa.getJaneiro());
    		saldo.setFevereiro(totalReceita.getFevereiro()-totalDespesa.getFevereiro());
    		saldo.setMarco(totalReceita.getMarco()-totalDespesa.getMarco());
    		saldo.setAbril(totalReceita.getAbril()-totalDespesa.getAbril());
    		saldo.setMaio(totalReceita.getMaio()-totalDespesa.getMaio());
    		saldo.setJunho(totalReceita.getJunho()-totalDespesa.getJunho());
			saldo.setJulho(totalReceita.getJulho()-totalDespesa.getJulho());
			saldo.setAgosto(totalReceita.getAgosto()-totalDespesa.getAgosto());
			saldo.setSetembro(totalReceita.getSetembro()-totalDespesa.getSetembro());
			saldo.setOutubro(totalReceita.getOutubro()-totalDespesa.getOutubro());
			saldo.setNovembro(totalReceita.getNovembro()-totalDespesa.getNovembro());
			saldo.setDezembro(totalReceita.getDezembro()-totalDespesa.getDezembro());
			
			acumulado.setJaneiro(saldo.getJaneiro());
			acumulado.setFevereiro(saldo.getFevereiro()+acumulado.getJaneiro());
			acumulado.setMarco(saldo.getMarco()+acumulado.getFevereiro());
			acumulado.setAbril(saldo.getAbril()+acumulado.getMarco());
			acumulado.setMaio(saldo.getMaio()+acumulado.getAbril());
			acumulado.setJunho(saldo.getJunho()+acumulado.getMaio());
			acumulado.setJulho(saldo.getJulho()+acumulado.getJunho());
			acumulado.setAgosto(saldo.getAgosto()+acumulado.getJulho());
			acumulado.setSetembro(saldo.getSetembro()+acumulado.getAgosto());
			acumulado.setOutubro(saldo.getOutubro()+acumulado.getSetembro());
			acumulado.setNovembro(saldo.getNovembro()+acumulado.getOutubro());
			acumulado.setDezembro(saldo.getDezembro()+acumulado.getNovembro());
			
			totais = new ArrayList<ViewConsolidado>();
    		totais.add(totalReceita);
    		totais.add(totalDespesa);
    		totais.add(saldo);
    		totais.add(acumulado);
    	}
				
	}

	public void observacaoConta(int conta) {
    	ln=new ConsolidadoLN();
    	contasObservacao=ln.listaPorContaAno(conta,ano);  
    	if(contasObservacao.size()>0)
    		contaSelecionada=ln.findByContaMesAno(conta, mes, ano);
    	else
    		contaSelecionada=new Consolidado();
    }	
    
    public void celulaSelecionada(int conta, int m){
    	totalRazao=0.0;
    	razoes = new ArrayList<Razao>();
    	ConsolidadoLN ln = new ConsolidadoLN();
    	razoes = ln.listRazaoContaMesAno(conta, m, ano);
    	descricaoRazao="Conta  "+conta+ " - "+buscaDescricao(conta);
    	for(Razao r:razoes)
    		totalRazao =totalRazao+r.getValor();
    }

    public void limpaRazao(){
    	razoes = new ArrayList<Razao>();
    	descricaoRazao="";
    }

	public void localizaSolicitacao(){
		contaSelecionada = new Consolidado();
		ln=new ConsolidadoLN();
		int conta = planoSelecionado.getClacta();
		contaSelecionada=ln.findByContaMesAno(conta,mes,ano);
		if(contaSelecionada.getId()==null){
			msg="Lançamento não localizado!";
			mensagens();
		}
			
	}

	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
 	public String nomeMes(int i){
		String nome="";
	       switch (i) {
	           case 1:
	        	   nome= "Janeiro";
	               break;
	           case 2:
	        	   nome= "Fevereiro";
	               break;
	           case 3:
	        	   nome= "Março";
	               break;
	           case 4:
	        	   nome= "Abril";
	               break;
	           case 5:
	        	   nome= "Maio"; 
	               break;
	           case 6:
	        	   nome= "Junho";
	               break;
	            case 7:
	               nome= "Julho";
	               break;
	            case 8:
	            	nome= "Agosto";
	                break;
	            case 9:
	            	nome= "Setembro";
	                break;
	            case 10:
	            	nome= "Outubro";
	                break;
	            case 11:
	            	 nome= "Novembro";
	                 break;
	            case 12:
	            	 nome= "Dezembro";
	                 break; 
	       }
		return nome;	       
    }
	
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

	public List<ViewConsolidado> getViews() {
		return views;
	}

	public void setViews(List<ViewConsolidado> views) {
		this.views = views;
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

	public List<HistoricoConsolidado> getContasObservacao() {
		return contasObservacao;
	}

	public void setContasObservacao(List<HistoricoConsolidado> contasObservacao) {
		this.contasObservacao = contasObservacao;
	}

	public ViewConsolidado getViewSelecionada() {
		return viewSelecionada;
	}

	public void setViewSelecionada(ViewConsolidado viewSelecionada) {
		this.viewSelecionada = viewSelecionada;
	}

	public PlanoConsolidado getPlanoSelecionado() {
		return planoSelecionado;
	}

	public void setPlanoSelecionado(PlanoConsolidado planoSelecionado) {
		this.planoSelecionado = planoSelecionado;
	}

	public List<Razao> getRazoes() {
		return razoes;
	}

	public void setRazoes(List<Razao> razoes) {
		this.razoes = razoes;
	}

	public String getDescricaoRazao() {
		return descricaoRazao;
	}

	public void setDescricaoRazao(String descricaoRazao) {
		this.descricaoRazao = descricaoRazao;
	}

	public Double getTotalRazao() {
		return totalRazao;
	}

	public void setTotalRazao(Double totalRazao) {
		this.totalRazao = totalRazao;
	}

	public List<ViewConsolidado> getViewsReceita() {
		return viewsReceita;
	}

	public void setViewsReceita(List<ViewConsolidado> viewsReceita) {
		this.viewsReceita = viewsReceita;
	}

	public List<ViewConsolidado> getViewsDespesa() {
		return viewsDespesa;
	}

	public void setViewsDespesa(List<ViewConsolidado> viewsDespesa) {
		this.viewsDespesa = viewsDespesa;
	}

	public List<ViewConsolidado> getTotais() {
		return totais;
	}

	public void setTotais(List<ViewConsolidado> totais) {
		this.totais = totais;
	}

	public List<HistoricoConsolidado> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<HistoricoConsolidado> historicos) {
		this.historicos = historicos;
	}

}
