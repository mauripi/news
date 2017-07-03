package br.com.mauricio.news.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
@PersistenceUnit(unitName="news")
@Entity(name="task")
public class Task implements Serializable{
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(length=35,nullable=false)
	private String titulo;
	@Temporal(TemporalType.DATE)
	private Date inicio;
	@Temporal(TemporalType.DATE)
	private Date fim;
	@OneToOne
	private Task pretask;
	@Column(length=500)
	private String descricao;
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="tarefas", joinColumns={@JoinColumn(name="task_id")}, inverseJoinColumns={@JoinColumn(name="pretask_id")})
	private Set<Task> tasks = new HashSet<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	public Task getPretask() {
		return pretask;
	}
	public void setPretask(Task pretask) {		
		this.pretask = pretask;
		if(pretask != null)
			pretask.getTasks().add(this);
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Set<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
