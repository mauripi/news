package br.com.mauricio.news.model.contabil;

import java.io.Serializable;

/**
*
* @author MAURICIO CRUZ
*/

public class RelacionamentoSig implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer ctared;
	private Integer clacta;
	private Integer ctasig;
	private Integer clasig;
	private String dessig;

	
	public Integer getCtared() {
		return ctared;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}
	public Integer getClacta() {
		return clacta;
	}
	public void setClacta(Integer clacta) {
		this.clacta = clacta;
	}
	public Integer getCtasig() {
		return ctasig;
	}
	public void setCtasig(Integer ctasig) {
		this.ctasig = ctasig;
	}
	public Integer getClasig() {
		return clasig;
	}
	public void setClasig(Integer clasig) {
		this.clasig = clasig;
	}
	public String getDessig() {
		return dessig;
	}
	public void setDessig(String dessig) {
		this.dessig = dessig;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	

}
