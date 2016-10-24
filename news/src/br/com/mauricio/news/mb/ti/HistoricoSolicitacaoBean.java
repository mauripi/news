package br.com.mauricio.news.mb.ti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.ti.HistoricoSolicitacaoLN;
import br.com.mauricio.news.model.ti.HistoricoSolicitacao;


@ManagedBean(name="historicoMB")
@ViewScoped
public class HistoricoSolicitacaoBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private HistoricoSolicitacao historico = new HistoricoSolicitacao();
	private List<HistoricoSolicitacao> historicos = new ArrayList<HistoricoSolicitacao>();
	private HistoricoSolicitacaoLN ln;
	private String msg;
	
	
	
	
	
	@PostConstruct
	public void init(){
	
	}

	public void listar(){
		ln = new HistoricoSolicitacaoLN();
		historicos = ln.getList();
	}

	public void novo(){

	}

	public void edita(){
		
	}

	public void exclui(){


	}
	
	public void grava(){		
	}

	public void limpaCadastro(){

	}
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	
	
	public HistoricoSolicitacao getHistorico() {
		return historico;
	}

	public void setHistorico(HistoricoSolicitacao historico) {
		this.historico = historico;
	}

	public List<HistoricoSolicitacao> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<HistoricoSolicitacao> historicos) {
		this.historicos = historicos;
	}

	public HistoricoSolicitacaoLN getLn() {
		return ln;
	}

	public void setLn(HistoricoSolicitacaoLN ln) {
		this.ln = ln;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
