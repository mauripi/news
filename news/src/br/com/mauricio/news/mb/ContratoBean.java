package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.ContratoLN;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.MCLIFOR;
import br.com.mauricio.news.model.TipoContrato;
import br.com.mauricio.news.util.ValidaEmail;
import br.com.mauricio.news.util.VerificaString;

@ManagedBean(name="contratoMB")
@ViewScoped
public class ContratoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Contrato contrato = new Contrato();
	private Contrato contratoSel = new Contrato();
	private GenericLN<Contrato> gln = new GenericLN<Contrato>();
	private List<Contrato> contratos = new ArrayList<Contrato>();
	private List<TipoContrato> tipoContratos = new ArrayList<TipoContrato>();
	private TipoContrato tipoContrato = new TipoContrato();
	private Login userLogado = new Login();
	private int controlaCadastro = 0;
	private String msg;
	private List<String> emails;
	private String emailAgendamento;
	private boolean skip;
    @ManagedProperty("#{mcliforService}")
    private MCLIFORService service;
	private MCLIFOR mclifor;	
	
	@PostConstruct
	public void init(){
		contrato = new Contrato();
		usuarioLogado();
		listar();
		listarTipos();
		emails = new ArrayList<String>();
		emailAgendamento = "";
	}
	
	private void listarTipos(){
		GenericLN<TipoContrato> gtln = new GenericLN<TipoContrato>();
		tipoContratos = gtln.listWithoutRemoved("tipocontrato", "descricao");
	}
	
	private void usuarioLogado(){
		FacesContext cx = FacesContext.getCurrentInstance();
	    HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
	    userLogado = (Login) sessao.getAttribute("login");
	}

	public void listar(){
		ContratoLN ln = new ContratoLN();
		contratos = ln.list(userLogado);
	}

	public void novo(){
		contrato = new Contrato();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(contrato.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<Contrato>();
			msg = gln.update(contrato);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){	
		contrato.setEmailsAviso(listToString(emails));
		contrato.setTipocontrato(tipoContrato);
		if(validaCampos()){
			gln = new GenericLN<Contrato>();
			if(controlaCadastro==1){
				contrato.setResponsavel(userLogado);
				msg = gln.add(contrato);	
			}
			if(controlaCadastro==2)				
				msg = gln.update(contrato);			
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	private String listToString(List<String> list){
		StringBuilder sb = new StringBuilder();
		for(String s:list)
			sb.append(s + ",");
		return sb.toString();
	}
	
	private void stringToList(){
		if(contrato.getEmailsAviso()!=null){
			String s[] = contrato.getEmailsAviso().split(",");
			emails = new ArrayList<String>();
			for(String x:s)
				emails.add(x);
		}
	}
	
	private boolean validaCampos(){

		return true;		
	}
	
	public void limpaCadastro(){
		contrato = new Contrato();
		controlaCadastro=0;
	}

	public void selecao(){
		contrato = contratoSel;
		controlaCadastro=0;
		edita();
	}

    public void onRowSelect(SelectEvent event) {
        contrato = (Contrato) event.getObject(); 
        stringToList();
        tipoContrato = contrato.getTipocontrato();
        emailAgendamento="";
    }
    
    public List<MCLIFOR> completeCliFor(String query) {
        List<MCLIFOR> allCliFor = service.getAllCliFor();
        List<MCLIFOR> filteredCliFor = new ArrayList<MCLIFOR>();
        for (int i = 0; i < allCliFor.size(); i++) {
        	MCLIFOR skin = allCliFor.get(i);
        	if(VerificaString.isInt(query)){
        		if(skin.getCgccpf().toString().startsWith(query.toLowerCase())) 
        			filteredCliFor.add(skin);         		
        	}else{
        		if(skin.getNomfan().toLowerCase().contains(query.toLowerCase())) 
        			filteredCliFor.add(skin);        		      		
        	}
        }         
        return filteredCliFor;
    }

    public void chooseCliFor() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("cadclifor", options, null);
    }
     
    public void onCliForChosen(SelectEvent event) {
    	mclifor = (MCLIFOR) event.getObject();  
    	if(mclifor.getId()!=null)
    		contrato.setMclifor(mclifor);
    }        
    
    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }    
   
    public void addEmail() {
    	if(ValidaEmail.validar(emailAgendamento)){
    		emails.add(emailAgendamento);   		
    		emailAgendamento="";  
    	}else{
    		msg="Informe um e-mail válido";
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg,msg)); 
    	}
    }    
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------
	public Date getToday() {
        return new Date();
    }

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Contrato getContratoSel() {
		return contratoSel;
	}

	public void setContratoSel(Contrato contratoSel) {
		this.contratoSel = contratoSel;
	}

	public GenericLN<Contrato> getGln() {
		return gln;
	}

	public void setGln(GenericLN<Contrato> gln) {
		this.gln = gln;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public Login getUserLogado() {
		return userLogado;
	}

	public void setUserLogado(Login userLogado) {
		this.userLogado = userLogado;
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

	public List<TipoContrato> getTipoContratos() {
		return tipoContratos;
	}

	public void setTipoContratos(List<TipoContrato> tipoContratos) {
		this.tipoContratos = tipoContratos;
	}

	public TipoContrato getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public void setService(MCLIFORService service) {
		this.service = service;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public String getEmailAgendamento() {
		return emailAgendamento;
	}

	public void setEmailAgendamento(String emailAgendamento) {
		this.emailAgendamento = emailAgendamento;
	}

	public MCLIFOR getMclifor() {
		return mclifor;
	}

	public void setMclifor(MCLIFOR mclifor) {
		this.mclifor = mclifor;
	}

}
