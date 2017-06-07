package br.com.mauricio.news.mb.engenharia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.engenharia.NotaDebitoEnergiaLN;
import br.com.mauricio.news.model.engenharia.NotaDebitoEnergia;
import br.com.mauricio.news.model.engenharia.MENGLOC;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="notadebitoMB")
@ViewScoped
public class NotaDebitoBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	private NotaDebitoEnergia debito = new NotaDebitoEnergia();
	private NotaDebitoEnergia debitoSel = new NotaDebitoEnergia();
	private List<NotaDebitoEnergia> debitos = new ArrayList<NotaDebitoEnergia>();
	private GenericLN<NotaDebitoEnergia> gln;
	private MENGLOC mengloc = new MENGLOC();
	private List<MENGLOC> menglocs = new ArrayList<MENGLOC>();
	private int controlaCadastro = 0;
	private String msg;
	private List<String> msgs;
	
	@PostConstruct
	public void init(){
		debito = new NotaDebitoEnergia();
		listarLoc();
		listar();		
	}

	public void listar(){
		gln = new GenericLN<NotaDebitoEnergia>();
		debitos = gln.listWithoutRemoved("notadebitopraca", "mes,ano");
	}

	public void listarLoc(){
		GenericLN<MENGLOC> glnd = new GenericLN<MENGLOC>();
		menglocs = glnd.listWithoutRemoved("mengloc", "nome");
	}
	
	public void novo(){
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(debito.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<NotaDebitoEnergia>();
			msg = gln.remove(gln.find(new NotaDebitoEnergia(), debito.getId()));
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			
			gln = new GenericLN<NotaDebitoEnergia>();
			if(controlaCadastro==2)	
				msg = gln.update(debito);
			if(controlaCadastro==1)
				msg = gln.add(debito);
			
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			mensagens(msgs);
		}
	}
	
	private boolean existeNaBase(){
		NotaDebitoEnergiaLN ln = new NotaDebitoEnergiaLN();
		return ln.exiteNaBase(debito);
	}

	private boolean existeNaBaseMaisDeUm(){
		NotaDebitoEnergiaLN ln = new NotaDebitoEnergiaLN();
		return ln.existeNaBaseMaisDeUm(debito);
	}
	
	private boolean validaCampos(){
		msgs = new ArrayList<String>();
		boolean v=true;
		debito.setMengloc(mengloc);
		if(debito.getMes()<=0||debito.getMes()>12){
			msgs.add("Informe o mês entre 1 e 12.");
			v = false;
		}
		if(debito.getAno()<2014){
			msgs.add("Informe o ano acima de 2014.");
			v = false;
		}		
		if(debito.getValor()==null){
			msgs.add("Informe o valor.");
			v=false;		
		}	
		if(controlaCadastro==1){
			if(existeNaBase()){
				msgs.add("Já existe registro para esta Praça neste período!");
				v = false;				
			}
		}
		if(controlaCadastro==2){
			if(existeNaBaseMaisDeUm()){
				msgs.add("Já existe registro para esta Praça neste período!");
				v = false;				
			}
		}
		return v;		
	}
	
	public void limpaCadastro(){
		debito = debitoSel = new NotaDebitoEnergia();
		controlaCadastro=0;
		mengloc=new MENGLOC();
	}

	public void selecao(){
		debito = debitoSel;
		mengloc = debito.getMengloc();
		controlaCadastro=0;
		edita();
	}
	
    public List<MENGLOC> completeLoc(String query) {
        List<MENGLOC> filtered = new ArrayList<MENGLOC>(); 
        for (int i = 0; i < menglocs.size(); i++) 
            if(menglocs.get(i).getNome().toLowerCase().contains(query.toLowerCase()))
                filtered.add(menglocs.get(i));              
        return filtered;
    }
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
        	context.addMessage(null, new FacesMessage("",m));  		
	}	
/*==================/GETTERS E SETTERS/===========================*/

	public NotaDebitoEnergia getDebito() {
		return debito;
	}

	public void setDebito(NotaDebitoEnergia debito) {
		this.debito = debito;
	}

	public NotaDebitoEnergia getDebitoSel() {
		return debitoSel;
	}

	public void setDebitoSel(NotaDebitoEnergia debitoSel) {
		this.debitoSel = debitoSel;
	}

	public List<NotaDebitoEnergia> getDebitos() {
		return debitos;
	}

	public void setDebitos(List<NotaDebitoEnergia> debitos) {
		this.debitos = debitos;
	}

	public GenericLN<NotaDebitoEnergia> getGln() {
		return gln;
	}

	public void setGln(GenericLN<NotaDebitoEnergia> gln) {
		this.gln = gln;
	}

	public MENGLOC getMengloc() {
		return mengloc;
	}

	public void setMengloc(MENGLOC mengloc) {
		this.mengloc = mengloc;
	}

	public List<MENGLOC> getMenglocs() {
		return menglocs;
	}

	public void setMenglocs(List<MENGLOC> menglocs) {
		this.menglocs = menglocs;
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

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
