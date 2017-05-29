package br.com.mauricio.news.mb.ti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.ti.AreaSolicitacaoLN;
import br.com.mauricio.news.model.ti.AreaSolicitacao;
import br.com.mauricio.news.model.ti.CategoriaSolicitacao;


@ManagedBean(name="areaSolicitacaoMB")
@ViewScoped
public class AreaSolicitacaoBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private AreaSolicitacao area = new AreaSolicitacao();
	private AreaSolicitacao areaSel = new AreaSolicitacao();
	private List<AreaSolicitacao> areas = new ArrayList<AreaSolicitacao>();
	GenericLN<AreaSolicitacao> gln = new GenericLN<AreaSolicitacao>();
	private AreaSolicitacaoLN ln;
	private String msg;
	private int controlaCadastro = 0;
	private CategoriaSolicitacao categoriaSelecionada=CategoriaSolicitacao.HELPDESK;
	
	@PostConstruct
	public void init(){
		listar();
	}

	public void listar(){
		gln = new GenericLN<AreaSolicitacao>();
		areas = gln.listWithoutRemoved("solicitacao_area", "id");
	}

	public void novo(){
		area = new AreaSolicitacao();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		gln = new GenericLN<AreaSolicitacao>();
		msg = gln.remove(gln.find(new AreaSolicitacao(), area.getId()));
		mensagens();
		listar();
		limpaCadastro();
	}
	
	public void grava(){
		if(validaCampos()){
			gln = new GenericLN<AreaSolicitacao>();
			area.setCategoria(categoriaSelecionada);
			if(controlaCadastro==1)
				msg = gln.add(area);
			
			if(controlaCadastro==2)	
				msg = gln.update(area);
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}

	private boolean validaCampos(){
		if(area.getDescricao().length()==0)
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		area=new AreaSolicitacao();
		controlaCadastro=0;
	}

 	public void selecao(){
		area = areaSel;
		categoriaSelecionada=area.getCategoria();
		controlaCadastro=0;
		edita();
	}	
	
 	public SelectItem[] getCategorias() {
 		ln = new AreaSolicitacaoLN();
 		SelectItem[] items=null;

 		int i = 0;
		
		items = new SelectItem[CategoriaSolicitacao.values().length];
		for(CategoriaSolicitacao c: CategoriaSolicitacao.values()) 
			items[i++] = new SelectItem(c, c.name());

		return items;
 	}
  	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	
	//=======================================================================================================================================================//

	public AreaSolicitacao getArea() {
		return area;
	}

	public void setArea(AreaSolicitacao area) {
		this.area = area;
	}

	public List<AreaSolicitacao> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaSolicitacao> areas) {
		this.areas = areas;
	}

	public AreaSolicitacaoLN getLn() {
		return ln;
	}

	public void setLn(AreaSolicitacaoLN ln) {
		this.ln = ln;
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

	public AreaSolicitacao getAreaSel() {
		return areaSel;
	}

	public void setAreaSel(AreaSolicitacao areaSel) {
		this.areaSel = areaSel;
	}

	public GenericLN<AreaSolicitacao> getGln() {
		return gln;
	}

	public void setGln(GenericLN<AreaSolicitacao> gln) {
		this.gln = gln;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}

	public CategoriaSolicitacao getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(CategoriaSolicitacao categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

}
