package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import br.com.mauricio.news.ln.ContratoLN;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.MCLIFOR;
import br.com.mauricio.news.model.TipoContrato;
import br.com.mauricio.news.util.DateUtil;
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
    private List<String> msgs;
    private List<String> emails;
    private String emailAgendamento;
    private boolean skip;
    private MCLIFOR mclifor;    
    private List<String> anexos;
    private String anexo;
    private List<String> allEmailsCadastrados = new ArrayList<String>();
    private String pathPdfAnexo="";
    
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
        allEmailsCadastrados = new ArrayList<String>();
        allEmailsCadastrados = ln.emailsCadastrados();
    }

    public void novo(){
        contrato = new Contrato();
        controlaCadastro=1;
        limpaCadastro();
    }

    public void edita(){
        controlaCadastro=2;
    }

    public void exclui(){
        if(contrato.getId()==null){
            msg = "Nenhum registro selecionado para exclus�o.";
            mensagens();            
        }else{
        	ContratoLN ln = new ContratoLN();
            msg = ln.delete(contrato);
            mensagens();
            limpaCadastro();
            listar();
        }        
    }
    
    public void grava(){    
        if(validaCampos()){
            gln = new GenericLN<Contrato>();
            contrato.setEmailsAviso(listToString(emails));
            contrato.setTipocontrato(tipoContrato);
            contrato.setUsuario(userLogado);
            msg = gln.update(contrato);            
            mensagens();
            listar();
            limpaCadastro();    
        }else{
            msg = "Favor preencha todos campos com *, pois s�o obrigat�rios.";
            mensagens();
        }
    }
    
    private String listToString(List<String> list){
        StringBuilder sb = new StringBuilder();
        list.stream().forEach(x -> sb.append(x + ","));
        return sb.toString();
    }
    
    private void stringToList(){
        if(contrato!=null)
            if(contrato.getEmailsAviso() != null && contrato.getEmailsAviso().length()>0)
                emails = Arrays.stream(contrato.getEmailsAviso().split(",")).collect(Collectors.toList());
            else
                emails = new ArrayList<String>();                    
    }
       
    public void limpaCadastro(){
        contrato = new Contrato();
        allEmailsCadastrados = new ArrayList<String>();
        anexos = new ArrayList<String>();
        controlaCadastro=0;
        
    }

    public void selecao(){
        contrato = contratoSel;
        controlaCadastro=0;
        edita();
    }

    public void montaCaminho(){
        pathPdfAnexo = ""+contrato.getId()+"/"+anexo;
    }

    public void removeAnexo(){
        ContratoLN ln = new ContratoLN();
        msg=ln.removeAnexo(contrato,anexo);
        anexos.remove(anexo);
        mensagens();
        
    }
    
    public void onRowSelect(SelectEvent event) {
        contrato = (Contrato) event.getObject(); 
        stringToList();
        ContratoLN ln = new ContratoLN();
        anexos = ln.getAnexos(contrato);
        tipoContrato = contrato.getTipocontrato();
        emailAgendamento="";
        edita();
        setaPrimeiraAba();
    }
  
    public void contratoSelecionado(Contrato c) {
        ContratoLN ln = new ContratoLN();
        anexos = ln.getAnexos(c);
        tipoContrato = c.getTipocontrato();
    }   
    
    private void setaPrimeiraAba(){
    	/*
        Wizard wizard = (Wizard) FacesContext.getCurrentInstance().getViewRoot().findComponent(":formCad:wiz");
        wizard.setStep("gerais");
        RequestContext.getCurrentInstance().update("formCad");
        */
    }

    public void onBlurDiasAviso(){
        /*
        if(contrato.getDiasAviso()!=null){
            int intervalo = DateUtil.diferencaEmDias(contrato.getInicio(), contrato.getFim());
            if(intervalo>contrato.getDiasAviso())
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dias de aviso com per�odo j� est� vencido.","")); 
        }
        */
    }
        
    public List<MCLIFOR> completeCliFor(String query) {
        GenericLN<MCLIFOR> gln = new GenericLN<MCLIFOR>();
        List<MCLIFOR> allCliFor=gln.listWithoutRemoved("mclifor", "nomfan");
        if(VerificaString.isInt(query))
            return allCliFor.stream().filter(x -> x.getCgccpf().startsWith(query)).collect(Collectors.toList());
        else
            return allCliFor.stream().filter(x -> x.getNomfan().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

    }

    public List<String> completeEmail(String query) {
        return allEmailsCadastrados.stream().filter(x -> x.toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList()); 
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

    private boolean validaCampos(){
    	boolean isValid=true;
        if(contrato.getObjeto().length()<1){
            msgs.add("Informe o objeto do contrato.");
            isValid=false;
        }
        if(contrato.getMclifor()==null){                   
            msgs.add("Informe o Cliente ou Fornecedor");
            isValid=false;
        }
        if(contrato.getInicio().equals(null)){
            msgs.add("Informe a data inicial.");
            isValid=false;
        }   
        if(contrato.getFim().equals(null)){
            msgs.add("Informe a data final.");
            isValid=false;
        }   
        if(DateUtil.isMaior(contrato.getInicio(), contrato.getFim())){
            msgs.add("Data inicial � maior que a data final.");
            isValid=false;
        }   
         return isValid;        
    }
   
    public void addEmail() {
        if(ValidaEmail.validar(emailAgendamento)){
            emails.add(emailAgendamento);           
            emailAgendamento="";  
        }else{
            msg="Informe um e-mail v�lido";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg,msg)); 
        }
    }    

    public void cliforSelect(SelectEvent event){
    	contrato.setMclifor((MCLIFOR) event.getObject());
    }
    
    public String getPrimeiroAvisoEm(){
    	String s="";
    	try{
	    	LocalDate data = new LocalDate(contrato.getFim()).minusDays(contrato.getDiasAviso());
	    	s = DateUtil.formatDataFromBanco(data.toDate());  
    	}catch(Exception e){
    		s="";
    	}
		return s;  	
    }
    
    public void handleFileUpload(FileUploadEvent event){
        ContratoLN ln = new ContratoLN();
        if(contrato.getId()==null){
            gln = new GenericLN<Contrato>();
            contrato.setTipocontrato(tipoContrato);
            contrato.setUsuario(userLogado);                        
            gln.addT(contrato);
        }
        ln.recebeArquivoUpload(event,contrato);
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        anexos = new ArrayList<String>();
        anexos = ln.getAnexos(contrato);
    }
    
    @SuppressWarnings("unused")
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
            context.addMessage(null, new FacesMessage(m,m));              
    }
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();                
        context.addMessage(null, new FacesMessage(msg,""));          
    }

    public String diasParaTermino(Date fim){
    	if(fim!=null)
    		return DateUtil.diferencaEmDias(new Date(), fim)+""; 	
    	return "";
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

    public List<String> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<String> anexos) {
        this.anexos = anexos;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public List<String> getAllEmailsCadastrados() {
        return allEmailsCadastrados;
    }

    public void setAllEmailsCadastrados(List<String> allEmailsCadastrados) {
        this.allEmailsCadastrados = allEmailsCadastrados;
    }

    public String getPathPdfAnexo() {
        return pathPdfAnexo;
    }

    public void setPathPdfAnexo(String pathPdfAnexo) {
        this.pathPdfAnexo = pathPdfAnexo;
    }

}