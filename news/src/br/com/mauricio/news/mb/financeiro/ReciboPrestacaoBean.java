package br.com.mauricio.news.mb.financeiro;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.financeiro.ReciboPrestacaoLN;
import br.com.mauricio.news.model.financeiro.Despesa;
import br.com.mauricio.news.model.financeiro.PrestacaoConta;

@ManagedBean(name="reciboPrestacaoMB")
@SessionScoped
public class ReciboPrestacaoBean implements Serializable {

	private static final long serialVersionUID = -5434138924149662559L;
	private PrestacaoConta prestacao = new PrestacaoConta();
	private Double totalRestituir = new Double("0.0");
	private Date dataRecibo = new Date();
	private Integer id;
	private String motivoDespesa;
	
	public ReciboPrestacaoBean(){
		limpar();
	}

	public void prestacaoSelecionada(){
		limpar();
		GenericLN<PrestacaoConta> gln = new GenericLN<PrestacaoConta>();
		prestacao = gln.find(new PrestacaoConta(), id);
		if(prestacao!=null){
			calculaTotais();
			motivoDespesa = prestacao.getMotivodespesa();
			if(totalRestituir<=new Double("0.0"))
				mensagem("Não existe valor a pagar para esta prestação!");
			
		}else{
			limpar();
			mensagem("Presção não localizada!");
		}

	}
	
	private void limpar() {
		prestacao = new PrestacaoConta();
		totalRestituir = new Double("0.0");
		motivoDespesa = "";
		dataRecibo = new Date();
	}

	public void calculaTotais() {
    	Double totalDespesa = new Double("0.0");
    	if(prestacao.getValoradiantado()==null)
    		prestacao.setValoradiantado(new Double("0.0"));
    	
    	if(prestacao.getDespesas().size()>0)
			for(Despesa d:prestacao.getDespesas())
				totalDespesa =totalDespesa + d.getValor();
		
    	if(prestacao.getValoradiantado()!=null)
    		if(prestacao.getValoradiantado()==new Double("0.0"))
    			totalRestituir = totalDespesa;
			if(prestacao.getValoradiantado()<totalDespesa)
				totalRestituir =totalDespesa-prestacao.getValoradiantado();

	}
	
	public void imprimir(){
		ReciboPrestacaoLN ln = new ReciboPrestacaoLN();
		prestacao.setMotivodespesa(motivoDespesa);
		ln.gerarRecibo(prestacao, dataRecibo, totalRestituir);	
		limpar();
	}
	
	public void mensagem(String msg){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage(msg,""));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------


	public Date getDataRecibo() {
		return dataRecibo;
	}

	public void setDataRecibo(Date dataRecibo) {
		this.dataRecibo = dataRecibo;
	}

	public PrestacaoConta getPrestacao() {
		return prestacao;
	}
	public void setPrestacao(PrestacaoConta prestacao) {
		this.prestacao = prestacao;
	}

	public Double getTotalRestituir() {
		return totalRestituir;
	}

	public void setTotalRestituir(Double totalRestituir) {
		this.totalRestituir = totalRestituir;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMotivoDespesa() {
		return motivoDespesa;
	}

	public void setMotivoDespesa(String motivoDespesa) {
		this.motivoDespesa = motivoDespesa;
	}

}
