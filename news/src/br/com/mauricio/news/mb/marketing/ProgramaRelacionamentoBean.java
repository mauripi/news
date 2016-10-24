package br.com.mauricio.news.mb.marketing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.marketing.ProgramaIbope;
import br.com.mauricio.news.model.marketing.ProgramaRelacionamento;

@ManagedBean(name="programarelacionamentoMB")
@ViewScoped
public class ProgramaRelacionamentoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String msg;
	private List<ProgramaRelacionamento> relacionamento = new ArrayList<ProgramaRelacionamento>();
	private List<ProgramaIbope> ibopes = new ArrayList<ProgramaIbope>();
	private ProgramaIbope ibope = new ProgramaIbope();
	
	@PostConstruct
	public void init(){
		listar();
		ibope = new ProgramaIbope();
	}
	
	public void gravar(){
		GenericLN<ProgramaRelacionamento> gln = new GenericLN<ProgramaRelacionamento>();
		for(ProgramaRelacionamento pr:relacionamento)
			if(pr.getProgramaibope()!=null)
				gln.update(pr);
		msg="Gravado com sucesso!";
		mensagens();
	}
	
	private void listar() {
		GenericLN<ProgramaRelacionamento> gln = new GenericLN<ProgramaRelacionamento>();
		relacionamento = gln.listWithoutRemoved("programarelacionamento", "id");
		GenericLN<ProgramaIbope> gibln = new GenericLN<ProgramaIbope>();
		ibopes = gibln.listWithoutRemoved("programaibope", "nome");		
	}

    public List<ProgramaIbope> completeText(String query) {
        List<ProgramaIbope> results = new ArrayList<ProgramaIbope>();
        for(ProgramaIbope i:ibopes) 
        	if(i.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(i);     
        return results;
    }


    public List<ProgramaIbope> completeNumber(String query) {  	
        List<ProgramaIbope> results = new ArrayList<ProgramaIbope>();
        for(ProgramaIbope i:ibopes) 
        	if(i.getCodigoibope().toString().contains(query))
        		results.add(i);     
        return results;
    }
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
	/* ==========================================================================*/
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<ProgramaRelacionamento> getRelacionamento() {
		return relacionamento;
	}
	public void setRelacionamento(List<ProgramaRelacionamento> relacionamento) {
		this.relacionamento = relacionamento;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ProgramaIbope getIbope() {
		return ibope;
	}
	public void setIbope(ProgramaIbope ibope) {
		this.ibope = ibope;
	}
	public List<ProgramaIbope> getIbopes() {
		return ibopes;
	}
	public void setIbopes(List<ProgramaIbope> ibopes) {
		this.ibopes = ibopes;
	}



}
