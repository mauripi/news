package br.com.mauricio.news.mb.engenharia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.engenharia.ControleTarefa;
import br.com.mauricio.news.util.UsuarioLogado;

@ManagedBean(name="controletarefaMB")
@ViewScoped
public class ControleTarefaMB implements Serializable{
	private static final long serialVersionUID = 1L;
	private GenericLN<ControleTarefa> gln;
	private ControleTarefa tarefa=new ControleTarefa();
	private List<ControleTarefa> tarefas = new ArrayList<ControleTarefa>();
	private List<ControleTarefa> tarefasFiltradas = new ArrayList<ControleTarefa>();
	private String msg;
	private int filtro=0;
	private int controlaCadastro = 0;
	
	@PostConstruct
	public void init(){
		listar();		
	}

	public void novo(){
		limpaCadastro();
		controlaCadastro=1;
	}
	
	public void edita(){
		controlaCadastro=2;
	}
	
	public void limpaCadastro(){
		tarefa=new ControleTarefa();
		tarefas = new ArrayList<ControleTarefa>();
		tarefasFiltradas = new ArrayList<ControleTarefa>();
		controlaCadastro=0;
		listar();
	}
	
	public void listar(){
		gln = new GenericLN<ControleTarefa>();
		tarefas = gln.listWithoutRemoved("controletarefa", "datainicio,datafim");
		filtragem();		
	}
	
	public void filtragem(){
		DateTimeZone timeZone = DateTimeZone.forID("GMT");
		DateTime d = new DateTime();
		tarefasFiltradas = new ArrayList<ControleTarefa>();
		if(filtro==0){
			for(ControleTarefa t:tarefas){
				DateTime d1 = new DateTime(t.getDatainicio(),timeZone);
				DateTime d2 = new DateTime(t.getDatafim(),timeZone);
				DateTime hoje = new DateTime(d.getYear(),d.getMonthOfYear(),d.getDayOfMonth(),0,0,0,timeZone);
				if( (d1.compareTo(hoje)==0) || (d1.compareTo(hoje)>0) || hoje.compareTo(d2)==0 || hoje.compareTo(d2)<0 )
					tarefasFiltradas.add(t);				
			}
		}
		if(filtro==1){
			for(ControleTarefa t:tarefas){
				DateTime d2 = new DateTime(t.getDatafim(),timeZone);
				DateTime hoje = new DateTime(d.getYear(),d.getMonthOfYear(),d.getDayOfMonth(),0,0,0,timeZone);
				if( hoje.compareTo(d2)>0 )
					tarefasFiltradas.add(t);				
			}	
		}
		if(filtro==2)
			tarefasFiltradas=tarefas;
		
	}
	
 	public void exclui(){
		if(tarefa.getId()!=null){
			gln = new GenericLN<ControleTarefa>();
			msg = gln.remove(gln.find(tarefa, tarefa.getId()));
			tarefa=new ControleTarefa();
			listar();	
			mensagens();
		}
	}
	
	public void gravar(){
		gln = new GenericLN<ControleTarefa>();
		if(controlaCadastro==2)	
			msg=gln.update(tarefa);
		if(controlaCadastro==1)
			msg=gln.add(tarefa);
		RequestContext.getCurrentInstance().execute("PF('dgCad').hide()");
		limpaCadastro();
		mensagens();
	}

	public void atualizaAprovacao(boolean value,ControleTarefa t){
		t.setAprovado(value);
		t.setUsuarioaprovacao(UsuarioLogado.getUser());
		t.setDataaprovacao(new Date(System.currentTimeMillis()));
		gln = new GenericLN<ControleTarefa>();
		msg=gln.update(t);
	}
	
    public void onRowSelect(SelectEvent event) {
        tarefa = (ControleTarefa) event.getObject();
        edita();
    }
 
    public int calculaProgresso(Date di, Date df){
		DateTimeZone timeZone = DateTimeZone.forID("GMT");
		int progress = 0;
		DateTime d = new DateTime();
    	DateTime d1 = new DateTime(di,timeZone);
    	DateTime d2 = new DateTime(df,timeZone);
		DateTime hoje = new DateTime(d.getYear(),d.getMonthOfYear(),d.getDayOfMonth(),0,0,0,timeZone);
		int dias = Days.daysBetween(new DateTime(d1), new DateTime(d2)).getDays();
		
		if(d1.compareTo(hoje)==0){
			progress = (100 * 1)/dias;
		}else{
			if(d1.compareTo(hoje)>0){
				progress = 0;
			}else{				
				if(hoje.compareTo(d2)==0)
					progress = 100;				
				if(hoje.compareTo(d2)>0)					
					progress = 100;				
				if(hoje.compareTo(d2)<0){
					int y = Days.daysBetween(hoje, d2).getDays();
					int z = dias-y;
					progress = (100 * z)/dias;
				}
			}
		}    	
    	 return progress;
    }
    
    public String simboloMoeda(Integer i){
    	String m;
    	switch(i){
    	    case 1:
    	    	m="R$ ";
    	    	break;
    	    case 2:
    	    	m="US$ ";
    	    	break;
    	    case 3:
    	    	m="€ ";
    	    	break;
    	    default:
    	    	m="R$ ";
    	    	break;
    	}
    	
		return m;   	
    }
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}	
	//================================================================================================
	public GenericLN<ControleTarefa> getGln() {
		return gln;
	}

	public void setGln(GenericLN<ControleTarefa> gln) {
		this.gln = gln;
	}

	public ControleTarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(ControleTarefa tarefa) {
		this.tarefa = tarefa;
	}

	public List<ControleTarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<ControleTarefa> tarefas) {
		this.tarefas = tarefas;
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

	public int getFiltro() {
		return filtro;
	}

	public void setFiltro(int filtro) {
		this.filtro = filtro;
	}

	public List<ControleTarefa> getTarefasFiltradas() {
		return tarefasFiltradas;
	}

	public void setTarefasFiltradas(List<ControleTarefa> tarefasFiltradas) {
		this.tarefasFiltradas = tarefasFiltradas;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}
	
}
