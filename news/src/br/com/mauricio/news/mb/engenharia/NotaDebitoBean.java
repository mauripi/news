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
import br.com.mauricio.news.model.engenharia.Praca;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="notadebitoMB")
@ViewScoped
public class NotaDebitoBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	private NotaDebitoEnergia debito;
	private NotaDebitoEnergia debitoSel;
	private List<NotaDebitoEnergia> debitos = new ArrayList<NotaDebitoEnergia>();
	private GenericLN<NotaDebitoEnergia> gln;
	private Praca praca;
	private List<Praca> pracas = new ArrayList<Praca>();
	private int controlaCadastro = 0;
	private String msg;
	private List<String> msgs;
	
	@PostConstruct
	public void init(){
		debito = new NotaDebitoEnergia();
		listarPraca();
		listar();		
	}

	public void listar(){
		gln = new GenericLN<NotaDebitoEnergia>();
		debitos = gln.listWithoutRemoved("notadebitopraca", "mes,ano desc");
	}

	public void listarPraca(){
		GenericLN<Praca> glnd = new GenericLN<Praca>();
		pracas = glnd.listWithoutRemoved("praca", "nome");
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
			msg = gln.update(debito);
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
		debito.setPraca(praca);
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
		praca=new Praca();
	}

	public void selecao(){
		debito = debitoSel;
		praca = debito.getPraca();
		controlaCadastro=0;
		edita();
	}
	
    public List<Praca> completePraca(String query) {
        List<Praca> filtered = new ArrayList<Praca>(); 
        for (int i = 0; i < pracas.size(); i++) 
            if(pracas.get(i).getNome().toLowerCase().contains(query.toLowerCase()))
                filtered.add(pracas.get(i));              
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

	public GenericLN<NotaDebitoEnergia> getGln() {
		return gln;
	}

	public void setGln(GenericLN<NotaDebitoEnergia> gln) {
		this.gln = gln;
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

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}

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

	public Praca getPraca() {
		return praca;
	}

	public void setPraca(Praca praca) {
		this.praca = praca;
	}

	public List<Praca> getPracas() {
		return pracas;
	}

	public void setPracas(List<Praca> pracas) {
		this.pracas = pracas;
	}

}
