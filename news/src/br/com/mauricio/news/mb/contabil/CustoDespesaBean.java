package br.com.mauricio.news.mb.contabil;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.joda.time.DateTime;

import br.com.mauricio.news.ln.contabil.CustoDespesaContabilLN;

@ManagedBean(name="custodespesaMB")
@ViewScoped
public class CustoDespesaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String msg;
	private CustoDespesaContabilLN ln;
	private Integer ano;
	private Boolean gerado=false;
	
	@PostConstruct
	public void init(){
		DateTime hoje = new DateTime();
		ano = hoje.getYear();
	}
		
	public void gerar(){
		ln = new CustoDespesaContabilLN();
		gerado=false;
		try {
			ln.gerarPlanilha(ano);
			gerado=true;
		} catch (IOException e) {
			gerado=false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("","Erro ao gerar arquivo.")); 
			e.printStackTrace();
		}
	}

	
	
	
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Boolean getGerado() {
		return gerado;
	}

	public void setGerado(Boolean gerado) {
		this.gerado = gerado;
	}

}
