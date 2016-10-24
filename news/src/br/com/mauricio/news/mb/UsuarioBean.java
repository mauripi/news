package br.com.mauricio.news.mb;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.mauricio.news.dao.CCustoDao;
import br.com.mauricio.news.ln.FilialLN;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.Modulo;

@ManagedBean(name="usuarioMB")
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = -5434138924149662559L;
	private Login login = new Login();
	private Login usuarioLogado = new Login();
	private Login loginSelecionado;
	private List<Login> logins= new ArrayList<Login>();
	private List<String> niveis = new ArrayList<String>();
	private int controlaCadastro = 0;
	private String msg;
	private List<Filial> filiais = new ArrayList<Filial>();

    private DualListModel<Login> usuariosPrestacao;
    private DualListModel<Modulo> usuariosModulos;
	private List<Modulo> todosModulos;
	private List<Modulo> modulosUsuario;
	private List<CCusto> centroCustos;
	private List<Modulo> modulosSemPermisao ;	
	
	@PostConstruct
	public void init(){
		LoginLN ac = new LoginLN();
		this.logins = ac.listaUsuarios("nome");
		listarFiliais();
		todosModulos = listAllModulos();
		CCustoDao daoc = new CCustoDao();
		centroCustos = daoc.list();
		setUsuariosPrestacao(new DualListModel<Login>(logins, new ArrayList<Login>()));
		setUsuariosModulos(new DualListModel<Modulo>(todosModulos, new ArrayList<Modulo>()));

	}

	private List<Modulo> listAllModulos() {
		GenericLN<Modulo> glm = new GenericLN<Modulo>();
		return glm.listWithoutRemoved("modulo", "id");
	}

	private void listarFiliais(){
		FilialLN ln = new FilialLN();
		this.filiais = ln.list();
	}
	
	public void novo(){
		Login l = new Login();
		this.login = l;
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(this.login.getId()==null){
			msg = "Nenhum usuário selecionado para exclusão.";
			mensagens();			
		}else{
			LoginLN ln = new LoginLN();
			this.login.setRemovido(1);
			msg = ln.remove(this.login);
			mensagens();
			limpaCadastro();
			init();
		}
	}
	
	public void grava(){		
		if(this.login.getNome()=="" || this.login.getCpf()==""){
			msg = "Verifique se os campos Nome e Usuário estão preenchidos.";
			mensagens();
		}else{
			preparaGravacao();
			if(controlaCadastro==1){
				LoginLN ln = new LoginLN();
				this.login.setRemovido(0);
				this.login.setAtivo(true);
				this.login.setTrocarSenha(1);
				msg = ln.adiciona(this.login);
				mensagens();
				limpaCadastro();
				init();
			}else{
				if(this.login.getId()==null){
					msg = "Cancele e recomece o processo.";
					mensagens();				
				}else{
					if(controlaCadastro==2){
						LoginLN ln = new LoginLN();
						msg = ln.atualiza(this.login);
						mensagens();
						limpaCadastro();
						init();
					}
				}
			}
		}
	}
	
	private void preparaGravacao(){
		FacesContext cx = FacesContext.getCurrentInstance();
	    HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
	    usuarioLogado = (Login) sessao.getAttribute("login");
	    this.login.setAcessos(new ArrayList<Modulo>());
	    for(Modulo m:usuariosModulos.getTarget())
	    	this.login.getAcessos().add(m);
	    this.login.setUsuariosPrestacao(new ArrayList<Login>());
	    for(Login l:usuariosPrestacao.getTarget())
	    	this.login.getUsuariosPrestacao().add(l);
		this.login.setDataCriacao(new Date(System.currentTimeMillis()));
		this.login.setUsuarioCriacao(usuarioLogado);
	}
	
    public void onTransfer(TransferEvent event) {  } 	
		
	public void usuarioSelecionado(SelectEvent event){		
		LoginLN ln = new LoginLN();
		this.login = ln.findById((Login) event.getObject());		
		setUsuariosPrestacao(new DualListModel<Login>(logins, login.getUsuariosPrestacao()));
		atualizaModulos();
		setUsuariosModulos(new DualListModel<Modulo>(modulosSemPermisao, modulosUsuario));
		edita();
	}

	
	private void  atualizaModulos() {
		modulosUsuario = new ArrayList<Modulo>();
		modulosSemPermisao = new ArrayList<Modulo>();
		for(Modulo a:this.login.getAcessos())
			for(Modulo m:todosModulos)
				if(a.getId().equals(m.getId()))
					modulosUsuario.add(m);
		modulosSemPermisao.addAll(todosModulos);
		modulosSemPermisao.removeAll(modulosUsuario);
	}

	public void limpaCadastro(){
		Login l = new Login();
		this.login = l;
		controlaCadastro=0;
		setUsuariosPrestacao(new DualListModel<Login>(new ArrayList<Login>(), new ArrayList<Login>()));
		setUsuariosModulos(new DualListModel<Modulo>(new ArrayList<Modulo>(), new ArrayList<Modulo>()));
	}

	public void resetarSenha(){
		LoginLN ln = new LoginLN();
		msg = ln.reiniciarSenha(ln.findById(this.login));
		mensagens();
		limpaCadastro();
		init();
	}
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	public void fechar(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    

		try {
			cx.getExternalContext().redirect(request.getContextPath()+"/sistema/home.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	//--------------------------GETTERS E SETTERS ----------------------------------
	
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public List<Login> getLogins() {
		return logins;
	}

	public void setLogins(List<Login> logins) {
		this.logins = logins;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getNiveis() {
		return niveis;
	}

	public void setNiveis(List<String> niveis) {
		this.niveis = niveis;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}

	public Login getLoginSelecionado() {
		return loginSelecionado;
	}

	public void setLoginSelecionado(Login loginSelecionado) {
		this.loginSelecionado = loginSelecionado;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}

	public DualListModel<Login> getUsuariosPrestacao() {
		return usuariosPrestacao;
	}

	public void setUsuariosPrestacao(DualListModel<Login> usuariosPrestacao) {
		this.usuariosPrestacao = usuariosPrestacao;
	}

	public DualListModel<Modulo> getUsuariosModulos() {
		return usuariosModulos;
	}

	public void setUsuariosModulos(DualListModel<Modulo> usuariosModulos) {
		this.usuariosModulos = usuariosModulos;
	}

	public List<Modulo> getTodosModulos() {
		return todosModulos;
	}

	public void setTodosModulos(List<Modulo> todosModulos) {
		this.todosModulos = todosModulos;
	}

	public List<Modulo> getModulosUsuario() {
		return modulosUsuario;
	}

	public void setModulosUsuario(List<Modulo> modulosUsuario) {
		this.modulosUsuario = modulosUsuario;
	}

	public List<CCusto> getCentroCustos() {
		return centroCustos;
	}

	public void setCentroCustos(List<CCusto> centroCustos) {
		this.centroCustos = centroCustos;
	}

	public Login getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Login usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Modulo> getModulosSemPermisao() {
		return modulosSemPermisao;
	}

	public void setModulosSemPermisao(List<Modulo> modulosSemPermisao) {
		this.modulosSemPermisao = modulosSemPermisao;
	}

}
