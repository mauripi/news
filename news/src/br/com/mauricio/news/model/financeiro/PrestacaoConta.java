package br.com.mauricio.news.model.financeiro;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import br.com.mauricio.news.model.Auditoria;
import br.com.mauricio.news.model.Login;

@PersistenceUnit(unitName="news")
@Entity(name="prestacaoconta")
public class PrestacaoConta  extends Auditoria implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	private Double valoradiantado;
	private Double valortotaldespesa;
	private Double valortotalrestituir;
	private Double valortotalreceber;
	private Date dataadiantamento;
	@OneToOne
	@JoinColumn(name="colaborador_id")		
	private Login colaborador;
    @Column(length=500)  
    private String motivodespesa="";
    @Column(length=7)  
    private String placaempresa;
    @Column(length=7)  
    private String placaparticular;
    @Column(length=30)  
    private String veiculoempresadesc;
    @Column(length=30)  
    private String veiculoparticulardesc;
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean veiculoempresa=false;
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean veiculoparticular=false;
	@OneToMany(mappedBy = "prestacaoconta", targetEntity = Despesa.class,cascade=CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Despesa> despesas;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getValoradiantado() {
		return valoradiantado;
	}
	public void setValoradiantado(Double valoradiantado) {
		this.valoradiantado = valoradiantado;
	}
	public Double getValortotaldespesa() {
		return valortotaldespesa;
	}
	public void setValortotaldespesa(Double valortotaldespesa) {
		this.valortotaldespesa = valortotaldespesa;
	}
	public Double getValortotalrestituir() {
		return valortotalrestituir;
	}
	public void setValortotalrestituir(Double valortotalrestituir) {
		this.valortotalrestituir = valortotalrestituir;
	}
	public Double getValortotalreceber() {
		return valortotalreceber;
	}
	public void setValortotalreceber(Double valortotalreceber) {
		this.valortotalreceber = valortotalreceber;
	}
	public Date getDataadiantamento() {
		return dataadiantamento;
	}
	public void setDataadiantamento(Date dataadiantamento) {
		this.dataadiantamento = dataadiantamento;
	}
	public Login getColaborador() {
		return colaborador;
	}
	public void setColaborador(Login colaborador) {
		this.colaborador = colaborador;
	}
	public String getMotivodespesa() {
		return motivodespesa;
	}
	public void setMotivodespesa(String motivodespesa) {
		this.motivodespesa = motivodespesa;
	}
	public String getPlacaempresa() {
		return placaempresa;
	}
	public void setPlacaempresa(String placaempresa) {
		this.placaempresa = placaempresa;
	}
	public String getPlacaparticular() {
		return placaparticular;
	}
	public void setPlacaparticular(String placaparticular) {
		this.placaparticular = placaparticular;
	}
	public String getVeiculoempresadesc() {
		return veiculoempresadesc;
	}
	public void setVeiculoempresadesc(String veiculoempresadesc) {
		this.veiculoempresadesc = veiculoempresadesc;
	}
	public String getVeiculoparticulardesc() {
		return veiculoparticulardesc;
	}
	public void setVeiculoparticulardesc(String veiculoparticulardesc) {
		this.veiculoparticulardesc = veiculoparticulardesc;
	}
	public boolean isVeiculoempresa() {
		return veiculoempresa;
	}
	public void setVeiculoempresa(boolean veiculoempresa) {
		this.veiculoempresa = veiculoempresa;
	}
	public boolean isVeiculoparticular() {
		return veiculoparticular;
	}
	public void setVeiculoparticular(boolean veiculoparticular) {
		this.veiculoparticular = veiculoparticular;
	}
	public List<Despesa> getDespesas() {
		return despesas;
	}
	public void setDespesas(List<Despesa> despesas) {
		this.despesas = despesas;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((colaborador == null) ? 0 : colaborador.hashCode());
		result = prime
				* result
				+ ((dataadiantamento == null) ? 0 : dataadiantamento.hashCode());
		result = prime * result
				+ ((despesas == null) ? 0 : despesas.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((motivodespesa == null) ? 0 : motivodespesa.hashCode());
		result = prime * result
				+ ((placaempresa == null) ? 0 : placaempresa.hashCode());
		result = prime * result
				+ ((placaparticular == null) ? 0 : placaparticular.hashCode());
		result = prime * result
				+ ((valoradiantado == null) ? 0 : valoradiantado.hashCode());
		result = prime
				* result
				+ ((valortotaldespesa == null) ? 0 : valortotaldespesa
						.hashCode());
		result = prime
				* result
				+ ((valortotalreceber == null) ? 0 : valortotalreceber
						.hashCode());
		result = prime
				* result
				+ ((valortotalrestituir == null) ? 0 : valortotalrestituir
						.hashCode());
		result = prime * result + (veiculoempresa ? 1231 : 1237);
		result = prime
				* result
				+ ((veiculoempresadesc == null) ? 0 : veiculoempresadesc
						.hashCode());
		result = prime * result + (veiculoparticular ? 1231 : 1237);
		result = prime
				* result
				+ ((veiculoparticulardesc == null) ? 0 : veiculoparticulardesc
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrestacaoConta other = (PrestacaoConta) obj;
		if (colaborador == null) {
			if (other.colaborador != null)
				return false;
		} else if (!colaborador.equals(other.colaborador))
			return false;
		if (dataadiantamento == null) {
			if (other.dataadiantamento != null)
				return false;
		} else if (!dataadiantamento.equals(other.dataadiantamento))
			return false;
		if (despesas == null) {
			if (other.despesas != null)
				return false;
		} else if (!despesas.equals(other.despesas))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (motivodespesa == null) {
			if (other.motivodespesa != null)
				return false;
		} else if (!motivodespesa.equals(other.motivodespesa))
			return false;
		if (placaempresa == null) {
			if (other.placaempresa != null)
				return false;
		} else if (!placaempresa.equals(other.placaempresa))
			return false;
		if (placaparticular == null) {
			if (other.placaparticular != null)
				return false;
		} else if (!placaparticular.equals(other.placaparticular))
			return false;
		if (valoradiantado == null) {
			if (other.valoradiantado != null)
				return false;
		} else if (!valoradiantado.equals(other.valoradiantado))
			return false;
		if (valortotaldespesa == null) {
			if (other.valortotaldespesa != null)
				return false;
		} else if (!valortotaldespesa.equals(other.valortotaldespesa))
			return false;
		if (valortotalreceber == null) {
			if (other.valortotalreceber != null)
				return false;
		} else if (!valortotalreceber.equals(other.valortotalreceber))
			return false;
		if (valortotalrestituir == null) {
			if (other.valortotalrestituir != null)
				return false;
		} else if (!valortotalrestituir.equals(other.valortotalrestituir))
			return false;
		if (veiculoempresa != other.veiculoempresa)
			return false;
		if (veiculoempresadesc == null) {
			if (other.veiculoempresadesc != null)
				return false;
		} else if (!veiculoempresadesc.equals(other.veiculoempresadesc))
			return false;
		if (veiculoparticular != other.veiculoparticular)
			return false;
		if (veiculoparticulardesc == null) {
			if (other.veiculoparticulardesc != null)
				return false;
		} else if (!veiculoparticulardesc.equals(other.veiculoparticulardesc))
			return false;
		return true;
	}
	
}
