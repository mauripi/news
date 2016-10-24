package br.com.mauricio.news.mb.ti;

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
import br.com.mauricio.news.model.ti.LocalEquipamento;
import br.com.mauricio.news.util.UsuarioLogado;

@ManagedBean(name="localequipamentoMB")
@ViewScoped
public class LocalEquipamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private LocalEquipamento local = new LocalEquipamento();
	private LocalEquipamento localSel = new LocalEquipamento();
	private GenericLN<LocalEquipamento> gln = new GenericLN<LocalEquipamento>();
	private List<LocalEquipamento> locais = new ArrayList<LocalEquipamento>();
	private int controlaCadastro = 0;
	private String msg;
	
	@PostConstruct
	public void init(){
		local = new LocalEquipamento();
		listar();		
	}

	public void listar(){
		GenericLN<LocalEquipamento> gln = new GenericLN<LocalEquipamento>();
		locais = gln.listAll(new LocalEquipamento(), "descricao");
	}

	public void novo(){
		local = new LocalEquipamento();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(local.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<LocalEquipamento>();
			local.setRemovido(1);
			local.setDataAlteracao(new Date(System.currentTimeMillis()));
			local.setUsuarioAlteracao(UsuarioLogado.getUser());
			msg = gln.update(local);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}

	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<LocalEquipamento>();
			if(controlaCadastro==1){
				local.setDataCriacao(new Date(System.currentTimeMillis()));			
				local.setUsuarioCriacao(UsuarioLogado.getUser());
				local.setRemovido(0);
				msg = gln.add(local);
			}
			if(controlaCadastro==2)	{	
				local.setDataAlteracao(new Date(System.currentTimeMillis()));
				local.setUsuarioAlteracao(UsuarioLogado.getUser());	
				msg = gln.update(local);
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
		if(local.getDescricao()=="")
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		local = localSel = new LocalEquipamento();
		controlaCadastro=0;
	}

	public void selecao(){
		local = localSel;
		controlaCadastro=0;
		edita();
	}
	    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------

	public LocalEquipamento getLocal() {
		return local;
	}

	public void setLocal(LocalEquipamento local) {
		this.local = local;
	}

	public LocalEquipamento getLocalSel() {
		return localSel;
	}

	public void setLocalSel(LocalEquipamento localSel) {
		this.localSel = localSel;
	}

	public GenericLN<LocalEquipamento> getGln() {
		return gln;
	}

	public void setGln(GenericLN<LocalEquipamento> gln) {
		this.gln = gln;
	}

	public List<LocalEquipamento> getLocais() {
		return locais;
	}

	public void setLocais(List<LocalEquipamento> locais) {
		this.locais = locais;
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
