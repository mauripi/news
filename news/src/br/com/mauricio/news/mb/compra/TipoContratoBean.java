package br.com.mauricio.news.mb.compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.compra.TipoContrato;
import br.com.mauricio.news.util.UsuarioLogado;

@ManagedBean(name="tipocontratoMB")
@ViewScoped
public class TipoContratoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoContrato tipo = new TipoContrato();
	private TipoContrato tipoSel = new TipoContrato();
	private GenericLN<TipoContrato> gln = new GenericLN<TipoContrato>();
	private List<TipoContrato> tipos = new ArrayList<TipoContrato>();
	private int controlaCadastro = 0;
	private String msg;
	
	
	@PostConstruct
	public void init(){
		tipo = new TipoContrato();
		listar();		
	}

	public void listar(){
		GenericLN<TipoContrato> gln = new GenericLN<TipoContrato>();
		tipos = gln.listWithoutRemoved("tipocontrato", "descricao");
	}

	public void novo(){
		tipo = new TipoContrato();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(tipo.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<TipoContrato>();
			tipo.setRemovido(1);
			tipo.setDataAlteracao(new Date(System.currentTimeMillis()));
			tipo.setUsuarioAlteracao(UsuarioLogado.getUser());
			msg = gln.update(tipo);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}

	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<TipoContrato>();
			if(controlaCadastro==1){
				tipo.setDataCriacao(new Date(System.currentTimeMillis()));			
				tipo.setUsuarioCriacao(UsuarioLogado.getUser());
				msg = gln.add(tipo);
			}
			if(controlaCadastro==2)	{		
				tipo.setDataAlteracao(new Date(System.currentTimeMillis()));
				tipo.setUsuarioAlteracao(UsuarioLogado.getUser());	
				msg = gln.update(tipo);
			}
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	private boolean validaCampos(){
		if(tipo.getDescricao()=="")
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		tipo = new TipoContrato();
		controlaCadastro=0;
	}

	public void selecao(){
		tipo = tipoSel;
		controlaCadastro=0;
		edita();
	}
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------
	public Date getToday() {
        return new Date();
    }

	public TipoContrato getTipo() {
		return tipo;
	}

	public void setTipo(TipoContrato tipo) {
		this.tipo = tipo;
	}

	public TipoContrato getTipoSel() {
		return tipoSel;
	}

	public void setTipoSel(TipoContrato tipoSel) {
		this.tipoSel = tipoSel;
	}

	public GenericLN<TipoContrato> getGln() {
		return gln;
	}

	public void setGln(GenericLN<TipoContrato> gln) {
		this.gln = gln;
	}

	public List<TipoContrato> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoContrato> tipos) {
		this.tipos = tipos;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
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
