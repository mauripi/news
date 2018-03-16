package br.com.mauricio.news.mb.financeiro;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import br.com.mauricio.news.ln.financeiro.ProjetadoRealizadoLN;
@ManagedBean(name="projetadoRealizadoMB")
@ViewScoped
public class ProjetadoRealizadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String msg;
	private Boolean gerado=false;
	private Date dataInicial = null;
	private Date dataFinal = null;
	
	public void gerar(){
		gerado=false;
		ProjetadoRealizadoLN ln = new ProjetadoRealizadoLN();
		try {
			ln.gerarArquivoProjetadoRealizado(dataInicial, dataFinal);
			gerado=true;
		} catch (IOException e) {
			gerado=true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("","Erro ao gerar arquivo.")); 
			e.printStackTrace();
		}
	}

	public Boolean getGerado() {
		return gerado;
	}

	public void setGerado(Boolean gerado) {
		this.gerado = gerado;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	
}
