package br.com.mauricio.news.mb.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.financeiro.FluxoCaixaLN;
import br.com.mauricio.news.model.financeiro.ClassificacaoFluxoCaixa;
import br.com.mauricio.news.model.financeiro.FluxoCaixa;


@ManagedBean(name="fluxocaixaMB")
@ViewScoped
public class FluxoCaixaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private FluxoCaixa fluxo;
	private List<FluxoCaixa> movimentos = new ArrayList<FluxoCaixa>();
	private List<ClassificacaoFluxoCaixa> classificacoes = new ArrayList<ClassificacaoFluxoCaixa>();
	private List<String> msgs;
	private FluxoCaixaLN fluxoLN;
	private Date data;
	private ClassificacaoFluxoCaixa classificacao = new ClassificacaoFluxoCaixa();
	private boolean valido;

	@PostConstruct
	public void init(){
		valido = true;
		listar();
		listarClassificacao();
	}

	private void listar() {
		fluxoLN = new FluxoCaixaLN();
		movimentos = fluxoLN.listar();		
	}
	
	private void listarClassificacao() {
		fluxoLN = new FluxoCaixaLN();
		classificacoes = fluxoLN.listarClassificacao();		
	}	
	
	public void novo(){
		fluxo = new FluxoCaixa();
		fluxo.setCodccu("30001");
		fluxo.setDesccu("MATRIZ");
		fluxo.setDesctafin("");
		fluxo.setDesctared("");
		fluxo.setOrilct("M");
		fluxo.setCodtns("");
		fluxo.setCtafin(0);
		fluxo.setProrea("PROJETADO");
		fluxo.setValor(new BigDecimal("0.0"));
		fluxo.setData(new Date(System.currentTimeMillis()));
		valido = false;
	}
	
	public void remover(){
		fluxoLN = new FluxoCaixaLN();
		msgs = new ArrayList<String>();
		msgs.add(fluxoLN.remover(fluxo.getId()));
		listar();
		mensagens(msgs);
		limparCadastro();
	} 
	
	public void gravar(){
		fluxoLN = new FluxoCaixaLN();
		msgs = new ArrayList<String>();
		if(validado()){
			if(fluxo.getId()!= null){
				msgs.add(fluxoLN.alterar(fluxo));
			}else{
				msgs.add(fluxoLN.adicionar(fluxo));
			}
			listar();
			mensagens(msgs);
			limparCadastro();
		}else{
			mensagens(msgs);
		}
	}

	private void limparCadastro() {
		novo();
		msgs = new ArrayList<String>();
		classificacao = new ClassificacaoFluxoCaixa();
		data = null;
		valido = true;
	}

	private boolean validado() {
		msgs = new ArrayList<String>();
		valido = true;

		if(data != null){
			fluxo.setData(this.data);			
		}else{
			msgs.add("Informe a data do lançamento.");
			valido = false;			
		}
		
		if(classificacao.getCtared()==0){
			msgs.add("Informe a classificação do lançamento.");
			valido = false;
		}else{
			fluxo.setCtared(classificacao.getCtared());
			fluxo.setDesctared(classificacao.getDescta());
		}
		
		if(fluxo.getClifor().length()==0){
			msgs.add("Informe o Cliente / Fornecedor.");
			valido = false;
		}

		if(fluxo.getObservacao().length()==0){
			msgs.add("Informe a observação.");
			valido = false;
		}

		if(fluxo.getNumtit().length()==0){
			msgs.add("Informe o número do título.");
			valido = false;
		}
		
		if(fluxo.getValor() == null){
			msgs.add("Informe o valor do título.");
			valido = false;
		}
		return valido;
	}
	
    public void onRowSelect(SelectEvent event) {
        fluxo = (FluxoCaixa) event.getObject(); 
        this.classificacao = new ClassificacaoFluxoCaixa();
        this.classificacao.setCtared(fluxo.getCtared());
        this.classificacao.setDescta(fluxo.getDesctared());
        this.data = fluxo.getData();
        valido = true;
    }
	
	
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
        	context.addMessage(null, new FacesMessage(m,m));  			
	}

	
	/*--------------- GAS -----------------------*/
	public FluxoCaixa getFluxo() {
		return fluxo;
	}

	public void setFluxo(FluxoCaixa fluxo) {
		this.fluxo = fluxo;
	}

	public List<FluxoCaixa> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<FluxoCaixa> movimentos) {
		this.movimentos = movimentos;
	}

	public List<ClassificacaoFluxoCaixa> getClassificacoes() {
		return classificacoes;
	}

	public void setClassificacoes(List<ClassificacaoFluxoCaixa> classificacoes) {
		this.classificacoes = classificacoes;
	}

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public ClassificacaoFluxoCaixa getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(ClassificacaoFluxoCaixa classificacao) {
		this.classificacao = classificacao;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}
	
}
