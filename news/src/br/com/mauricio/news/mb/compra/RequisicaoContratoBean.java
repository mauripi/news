package br.com.mauricio.news.mb.compra;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.compra.RelRequisicaoContratoLN;
import br.com.mauricio.news.ln.compra.RequisicaoContratoLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.compra.RequisicaoContrato;
import br.com.mauricio.news.util.ValidaCNPJ;
import br.com.mauricio.news.util.ValidaCPF;
import br.com.mauricio.news.util.ValidaEmail;

@ManagedBean(name="requisicaocontratoMB")
@ViewScoped
public class RequisicaoContratoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private GenericLN<RequisicaoContrato> gln = new GenericLN<RequisicaoContrato>();
	private RequisicaoContrato requisicao = new RequisicaoContrato();
	private List<RequisicaoContrato> requisicoes = new ArrayList<RequisicaoContrato>();
	private int controlaCadastro = 0;
	private String msg;
	private Login usuarioLogado = new Login();
	private List<String> msgs;
	
	
	@PostConstruct
  	public void init(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuarioLogado = (Login) sessao.getAttribute("login");
		listar();
	}
	
	public void listar(){
		RequisicaoContratoLN ln = new RequisicaoContratoLN();
		requisicoes = ln.listaPorUsuario(usuarioLogado);
	}
	
	public void novo(){
		requisicao = new RequisicaoContrato();
		controlaCadastro=1;
		
	}
	
	public void edita(){
		controlaCadastro=2;
	}
	
	public void exclui(){
		

	}
	
	private boolean validaCampos(){
		msgs = new ArrayList<String>();
		boolean v=true;
		
		if(requisicao.getRazaosocial().length()==0){
			msgs.add("Informe a Razão Social.");
			v=false;
		}
		if(requisicao.getCnpj().length()==0){
			msgs.add("Informe o CNPJ.");
			v=false;
		}else{
			if(requisicao.getCnpj().length()<11){
				msgs.add("CNPJ inválido.");
				v=false;	
			}else{
				String s=requisicao.getCnpj().replace(".", "").replace("-", "").replace("/", "");
				if(s.length()>11){
					if(!ValidaCNPJ.isValidCNPJ(s)){
						msgs.add("CNPJ inválido.");
						v=false;					
					}
				}
				if(s.length()==11){
					if(!ValidaCPF.isValidCPF(s)){
						msgs.add("CPF inválido.");
						v=false;					
					}
				}				
			}
		}
		
		if(requisicao.getTelefone().length()==0){
			msgs.add("Informe o telefone.");
			v=false;		
		}
		if(requisicao.getEmail().length()==0){
			msgs.add("Informe o nome do contato.");
			v=false;		
		}else{
			if(!ValidaEmail.validar(requisicao.getEmail())){
				msgs.add("E-mail informado inválido.");
				v=false;				
			}			
		}
		if(requisicao.getSolicitante().length()==0){
			msgs.add("Informe o solicitante.");
			v=false;		
		}
		if(requisicao.getVigencia().length()==0){
			msgs.add("Informe o período de vigência.");
			v=false;		
		}	
		if(requisicao.getValor()==null){
			msgs.add("Informe o valor.");
			v=false;		
		}			
		if(requisicao.getInternoexterno()==null){
			msgs.add("Informe onde será confeccionado.");
			v=false;		
		}	
		if(requisicao.getTipo().length()==0){
			msgs.add("Informe o tipo.");
			v=false;		
		}	
		return v;		
	}	
	
	public void grava(){
		if(validaCampos()){
			gln = new GenericLN<RequisicaoContrato>();
			
			if(controlaCadastro==1){
				requisicao.setDatarequisicao(new Date());
				requisicao.setUsuario(usuarioLogado);
				msg = gln.add(requisicao);
			}
			
			if(controlaCadastro==2)
				msg = gln.update(requisicao);			
			
			enviaEmail();
			imprimir();
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			mensagens(msgs);
		}
	}
	
	private void enviaEmail() {
		RequisicaoContratoLN rln = new RequisicaoContratoLN();
		rln.enviarEmail(requisicao);		
	}

	public void limpaCadastro(){
		controlaCadastro=0;
		requisicao=new RequisicaoContrato();
	}
	
	public void imprimir(){		
		RelRequisicaoContratoLN rel = new RelRequisicaoContratoLN();
		try {
			rel.geraRelatorio(requisicao);
		} catch (IOException e) {
			System.out.println("Erro ao imprimir relatório de requisição de contrato RequisicaoContratoBean.imprimir()");
			msg="Erro ao imprimir";
			mensagens();
		}
	}

	public void selecao(){
		controlaCadastro=0;
		edita();
	}
	
	public Date getToday() {
        return new Date();
    }	
	
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
        	context.addMessage(null, new FacesMessage("",m));  		
	}
  	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
//====================================================================================================================================
	public GenericLN<RequisicaoContrato> getGln() {
		return gln;
	}

	public void setGln(GenericLN<RequisicaoContrato> gln) {
		this.gln = gln;
	}

	public RequisicaoContrato getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(RequisicaoContrato requisicao) {
		this.requisicao = requisicao;
	}

	public List<RequisicaoContrato> getRequisicoes() {
		return requisicoes;
	}

	public void setRequisicoes(List<RequisicaoContrato> requisicoes) {
		this.requisicoes = requisicoes;
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

	public Login getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Login usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}
	
}
