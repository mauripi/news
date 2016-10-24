package br.com.mauricio.news.mb.financeiro;

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

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.ln.financeiro.AdiantamentoLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.financeiro.Adiantamento;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="adiantamentoMB")
@ViewScoped
public class AdiantamentoBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Adiantamento adiantamento;
	private Adiantamento adiantamentoSel;
	private List<Adiantamento> adiantamentos = new ArrayList<Adiantamento>();
	private GenericLN<Adiantamento> gln;
	private int controlaCadastro = 0;
	private String msg;
	private String motivo;
	private Login favorecido = new Login();
	private Login usuarioLogado = new Login();
	private List<Login> todosUsuarios = new ArrayList<Login>();
	private String lblBotao;
	private String lblDialogo;
	
	@PostConstruct
	public void init(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuarioLogado = (Login) sessao.getAttribute("login");
		adiantamento = new Adiantamento();
		listar();		
		LoginLN loginLN = new LoginLN();
		todosUsuarios=loginLN.listaUsuarios("nome");
	}

	public void listar(){
		AdiantamentoLN ln = new AdiantamentoLN();
		adiantamentos = ln.listByUsuario(usuarioLogado);
	}

	public void novo(){
		adiantamento = new Adiantamento();
		adiantamento.setData(getToday());
		favorecido = usuarioLogado;
		adiantamento.setFavorecido(favorecido);
		adiantamento.setSolicitante(usuarioLogado);
		controlaCadastro=1;
		lblDialogo = "Novo Adiantamento";
		lblBotao = "Gravar";
	}

	public void edita(){
		controlaCadastro=2;
		lblDialogo = "Atualizar Adiantamento";
		lblBotao = "Atualizar";
	}

	public void exclui(){
		if(adiantamento.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<Adiantamento>();
			msg = gln.update(adiantamento);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<Adiantamento>();
			if(controlaCadastro==2)	
				msg = gln.update(adiantamento);
			if(controlaCadastro==1)
				msg = gln.add(adiantamento);	
			enviarEmail();
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	
	private void enviarEmail() {
		AdiantamentoLN ln = new AdiantamentoLN();
		ln.enviarEmail(adiantamento);
	}

	private boolean validaCampos(){
		if(motivo=="")
			return false;
		else
			adiantamento.setMotivo(motivo);
		GenericLN<Login> loginLN = new GenericLN<Login>();
		adiantamento.setFavorecido(loginLN.find(new Login(), favorecido.getId()));
		return true;		
	}
	
	public void limpaCadastro(){
		adiantamento = adiantamentoSel = new Adiantamento();
		controlaCadastro=0;
		lblDialogo = "Novo Adiantamento";
		lblBotao = "Gravar";
	}

	public void selecao(){
		adiantamento = adiantamentoSel;
		controlaCadastro=0;
		edita();
	}
	
    public List<Login> completeText(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:todosUsuarios) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(l);     
        return results;
    }

    public void onRowSelect(SelectEvent event) {
    	adiantamento = (Adiantamento) event.getObject();
    	favorecido = adiantamento.getFavorecido();
    	motivo = adiantamento.getMotivo();
        edita();
    }
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	public Date getToday() {
        return new Date();
    }
		
	
/*==================/GETTERS E SETTERS/===========================*/

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

	public Adiantamento getAdiantamento() {
		return adiantamento;
	}

	public void setAdiantamento(Adiantamento adiantamento) {
		this.adiantamento = adiantamento;
	}

	public Adiantamento getAdiantamentoSel() {
		return adiantamentoSel;
	}

	public void setAdiantamentoSel(Adiantamento adiantamentoSel) {
		this.adiantamentoSel = adiantamentoSel;
	}

	public List<Adiantamento> getAdiantamentos() {
		return adiantamentos;
	}

	public void setAdiantamentos(List<Adiantamento> adiantamentos) {
		this.adiantamentos = adiantamentos;
	}

	public GenericLN<Adiantamento> getGln() {
		return gln;
	}

	public void setGln(GenericLN<Adiantamento> gln) {
		this.gln = gln;
	}

	public Login getFavorecido() {
		return favorecido;
	}

	public void setFavorecido(Login favorecido) {
		this.favorecido = favorecido;
	}

	public Login getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Login usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Login> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(List<Login> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getLblBotao() {
		return lblBotao;
	}

	public void setLblBotao(String lblBotao) {
		this.lblBotao = lblBotao;
	}

	public String getLblDialogo() {
		return lblDialogo;
	}

	public void setLblDialogo(String lblDialogo) {
		this.lblDialogo = lblDialogo;
	}
	
}
