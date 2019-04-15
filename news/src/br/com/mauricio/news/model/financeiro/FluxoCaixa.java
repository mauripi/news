package br.com.mauricio.news.model.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

public class FluxoCaixa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer ctafin;
	private Date data;
	private BigDecimal valor;
	private String codtns;
	private String codccu;
	@Column(length=250)  
	private String observacao;
	private Integer ctared;
	private String prorea;
	private String numtit;
	private String clifor;	
	private String desctared;
	private String desccu;
	private String desctafin;
	@Column(length=1)  
	private String orilct;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCtafin() {
		return ctafin;
	}
	public void setCtafin(Integer ctafin) {
		this.ctafin = ctafin;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getCodtns() {
		return codtns;
	}
	public void setCodtns(String codtns) {
		this.codtns = codtns;
	}
	public String getCodccu() {
		return codccu;
	}
	public void setCodccu(String codccu) {
		this.codccu = codccu;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Integer getCtared() {
		return ctared;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}
	public String getProrea() {
		return prorea;
	}
	public void setProrea(String prorea) {
		this.prorea = prorea;
	}
	public String getNumtit() {
		return numtit;
	}
	public void setNumtit(String numtit) {
		this.numtit = numtit;
	}
	public String getClifor() {
		return clifor;
	}
	public void setClifor(String clifor) {
		this.clifor = clifor;
	}
	public String getDesctared() {
		return desctared;
	}
	public void setDesctared(String desctared) {
		this.desctared = desctared;
	}
	public String getDesccu() {
		return desccu;
	}
	public void setDesccu(String desccu) {
		this.desccu = desccu;
	}
	public String getDesctafin() {
		return desctafin;
	}
	public void setDesctafin(String desctafin) {
		this.desctafin = desctafin;
	}
	public String getOrilct() {
		return orilct;
	}
	public void setOrilct(String orilct) {
		this.orilct = orilct;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clifor == null) ? 0 : clifor.hashCode());
		result = prime * result + ((codccu == null) ? 0 : codccu.hashCode());
		result = prime * result + ((codtns == null) ? 0 : codtns.hashCode());
		result = prime * result + ((ctafin == null) ? 0 : ctafin.hashCode());
		result = prime * result + ((ctared == null) ? 0 : ctared.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((desccu == null) ? 0 : desccu.hashCode());
		result = prime * result + ((desctafin == null) ? 0 : desctafin.hashCode());
		result = prime * result + ((desctared == null) ? 0 : desctared.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numtit == null) ? 0 : numtit.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((orilct == null) ? 0 : orilct.hashCode());
		result = prime * result + ((prorea == null) ? 0 : prorea.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FluxoCaixa other = (FluxoCaixa) obj;
		if (clifor == null) {
			if (other.clifor != null)
				return false;
		} else if (!clifor.equals(other.clifor))
			return false;
		if (codccu == null) {
			if (other.codccu != null)
				return false;
		} else if (!codccu.equals(other.codccu))
			return false;
		if (codtns == null) {
			if (other.codtns != null)
				return false;
		} else if (!codtns.equals(other.codtns))
			return false;
		if (ctafin == null) {
			if (other.ctafin != null)
				return false;
		} else if (!ctafin.equals(other.ctafin))
			return false;
		if (ctared == null) {
			if (other.ctared != null)
				return false;
		} else if (!ctared.equals(other.ctared))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (desccu == null) {
			if (other.desccu != null)
				return false;
		} else if (!desccu.equals(other.desccu))
			return false;
		if (desctafin == null) {
			if (other.desctafin != null)
				return false;
		} else if (!desctafin.equals(other.desctafin))
			return false;
		if (desctared == null) {
			if (other.desctared != null)
				return false;
		} else if (!desctared.equals(other.desctared))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numtit == null) {
			if (other.numtit != null)
				return false;
		} else if (!numtit.equals(other.numtit))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (orilct == null) {
			if (other.orilct != null)
				return false;
		} else if (!orilct.equals(other.orilct))
			return false;
		if (prorea == null) {
			if (other.prorea != null)
				return false;
		} else if (!prorea.equals(other.prorea))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}	
}
