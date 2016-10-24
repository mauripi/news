package br.com.mauricio.news.model.ti;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.mauricio.news.model.Auditoria;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="equipamento")
@PersistenceUnit(unitName="news")
public class Equipamento extends Auditoria implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(length=10,unique=true)
	private String patrimonio;
	@Column(length=50)
	private String marca;
	@Column(length=100)
	private String modelo;
	@Column(length=100)
	private String host;
	@Column(length=20)
	private String serie;
	@Column(length=20)
	private String hd;
	@Column(length=20)
	private String memoria;
	@Column(length=15)
	private String ip;	
	@Column(length=300)
	private String observacao;	
	@OneToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="filial_id")	
	private Filial filial;	
	@OneToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="tipo_id")	
	private TipoEquipamento tipo;
	@OneToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="local_id")	
	private LocalEquipamento local;		
	@OneToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="colaborador_id")		
	private Login colaborador;	
	@ManyToMany
    @JoinTable(name = "equipamento_licenca", joinColumns = @JoinColumn(name = "equipamento_id"), inverseJoinColumns = @JoinColumn(name = "licenca_id"))
	@ForeignKey(name="FK_equipamento_licenca")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Licenca> licencas;

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getHd() {
		return hd;
	}
	public void setHd(String hd) {
		this.hd = hd;
	}
	public String getMemoria() {
		return memoria;
	}
	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Filial getFilial() {
		return filial;
	}
	public void setFilial(Filial filial) {
		this.filial = filial;
	}
	public TipoEquipamento getTipo() {
		return tipo;
	}
	public void setTipo(TipoEquipamento tipo) {
		this.tipo = tipo;
	}
	public LocalEquipamento getLocal() {
		return local;
	}
	public void setLocal(LocalEquipamento local) {
		this.local = local;
	}
	public Login getColaborador() {
		return colaborador;
	}
	public void setColaborador(Login colaborador) {
		this.colaborador = colaborador;
	}
	public List<Licenca> getLicencas() {
		return licencas;
	}
	public void setLicencas(List<Licenca> licencas) {
		this.licencas = licencas;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((colaborador == null) ? 0 : colaborador.hashCode());
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((hd == null) ? 0 : hd.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result
				+ ((licencas == null) ? 0 : licencas.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((memoria == null) ? 0 : memoria.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result
				+ ((patrimonio == null) ? 0 : patrimonio.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Equipamento other = (Equipamento) obj;
		if (colaborador == null) {
			if (other.colaborador != null)
				return false;
		} else if (!colaborador.equals(other.colaborador))
			return false;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
			return false;
		if (hd == null) {
			if (other.hd != null)
				return false;
		} else if (!hd.equals(other.hd))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (licencas == null) {
			if (other.licencas != null)
				return false;
		} else if (!licencas.equals(other.licencas))
			return false;
		if (local == null) {
			if (other.local != null)
				return false;
		} else if (!local.equals(other.local))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (memoria == null) {
			if (other.memoria != null)
				return false;
		} else if (!memoria.equals(other.memoria))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (patrimonio == null) {
			if (other.patrimonio != null)
				return false;
		} else if (!patrimonio.equals(other.patrimonio))
			return false;
		if (serie == null) {
			if (other.serie != null)
				return false;
		} else if (!serie.equals(other.serie))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

}
