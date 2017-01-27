package br.com.mauricio.news.mb;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.Modulo;
import br.com.mauricio.news.util.Cripto;
import br.com.mauricio.news.util.UsuarioLogado;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean
@SessionScoped
public class AcessoBean implements Serializable {

	private static final long serialVersionUID = -8083042147042481389L;
	private Login login = new Login();
	private String senha1;
	private String senha2;
	private LoginLN ln = new LoginLN();
	private String msgBoasVindas;
	private String msg;
	private List<Modulo> todosModulos;
	private List<Modulo> modulosUsuario;
    private Boolean temAcessoCliente = false;
    private Boolean temAcessoContrato = false;
    private Boolean temAcessoFornecedor = false;
    private Boolean temAcessoHolerite = false;
    private Boolean temAcessoHoleriteselecao = false;
    private Boolean temAcessoImpostoDeRenda = false;
    private Boolean temAcessoPrestacaoDeContas = false;
    private Boolean temAcessoTipoContrato = false;
    private Boolean temAcessoUsuario = false;
    private Boolean temAcessoUsuariosPrestacao = false;
    private Boolean temAcessoUsuariosAcessos = false;
    private Boolean temAcessoEquipamentos = false;
    private Boolean temAcessoLicenca = false;
    private Boolean temAcessoLocalEquipamentos = false;
    private Boolean temAcessoImportacaoHolerite = false;
    private Boolean temAcessoSig = false;
    private Boolean temAcessoConsolidado = false;
    private Boolean temAcessoImportConsolidado = false;
    private Boolean temAcessoControletConsolidado = false;
    private Boolean temAcessoImportarFluxoDiario = false;
    private Boolean temAcessoFluxoDiario = false;
    private Boolean temAcessoPainelChamado = false;
	private Boolean temAcessoCadastroSolicitacaoArea = false;
	private Boolean temAcessoAtendentes = false;        
	private Boolean temAcessoBaixaBem = false; 
	private Boolean temAcessoMovimentoBem = false; 	
	private Boolean temAcessoControleSinal = false; 
	private Boolean temAcessoIbopeMidiaMais = false;
	private Boolean temAcessoControleTarefa = false; 
	private Boolean temAcessoControleTarefaVisao = false; 
	private Boolean temAcessoControleTransporte = false; 
	
	public void entrar() throws IOException{
		FacesContext cx = FacesContext.getCurrentInstance();
	    HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);

		Boolean permissao=ln.autorizaLogin(this.login.getCpf(), this.login.getSenha());
		if(permissao==false){
			msg="Acesso Negado."; 
			mensagens();
	        sessao.invalidate();
	        this.login = null;
		}else{
			this.login = (Login) sessao.getAttribute("login");
			carregaPermissaoUsuario();
			if(this.login.getTrocarSenha()==1){
				trocarSenhaLogin();
			}else{
				paginaInicial();			
			}
		}
	}
	
	private void carregaPermissaoUsuario() {

		GenericLN<Modulo> glm = new GenericLN<Modulo>();
		todosModulos = glm.listWithoutRemoved("modulo", "id");
		modulosUsuario = new ArrayList<Modulo>();
		for(Modulo a:this.login.getAcessos())
			for(Modulo m:todosModulos)
				if(a.getId().equals(m.getId()))
					modulosUsuario.add(m);
				
		for(Modulo m:modulosUsuario){
			if(m.getNome().equals("cliente"))
				temAcessoCliente = true;
			if(m.getNome().equals("contrato"))
				temAcessoContrato = true;
			if(m.getNome().equals("fornecedor"))
				temAcessoFornecedor = true;
			if(m.getNome().equals("holerite"))
				temAcessoHolerite = true;	
			if(m.getNome().equals("holeriteselecao"))		
				temAcessoHoleriteselecao = true;		
			if(m.getNome().equals("impostoderenda"))
				temAcessoImpostoDeRenda = true;		
			if(m.getNome().equals("prestacaodecontas"))
				 temAcessoPrestacaoDeContas = true;
			if(m.getNome().equals("tipocontrato"))	
				temAcessoTipoContrato = true;
			if(m.getNome().equals("usuario"))	
				temAcessoUsuario = true;
			if(m.getNome().equals("usuariosprestacao"))		
				temAcessoUsuariosPrestacao = true;
			if(m.getNome().equals("usuariosacessos"))
				temAcessoUsuariosAcessos = true;
			if(m.getNome().equals("equipamentos"))			
				temAcessoEquipamentos = true;		
			if(m.getNome().equals("licencas"))			
				temAcessoLicenca = true;
			if(m.getNome().equals("localequipamentos"))			
				temAcessoLocalEquipamentos = true;		
			if(m.getNome().equals("importaholerite"))			
				temAcessoImportacaoHolerite = true;	
			if(m.getNome().equals("sig"))			
				temAcessoSig = true;		
			if(m.getNome().equals("consolidado"))			
				temAcessoConsolidado = true;
			if(m.getNome().equals("importarconsolidado"))			
				temAcessoImportConsolidado = true;			
			if(m.getNome().equals("controleconsolidado"))			
				temAcessoControletConsolidado = true;		
			if(m.getNome().equals("importarfluxodiario"))			
				temAcessoImportarFluxoDiario = true;
			if(m.getNome().equals("fluxodiario"))			
				temAcessoFluxoDiario = true;
			if(m.getNome().equals("painelchamado"))			
				temAcessoPainelChamado = true;
			if(m.getNome().equals("cadastrosolicitacaoarea"))			
				temAcessoCadastroSolicitacaoArea = true;	
			if(m.getNome().equals("atendente"))			
				temAcessoAtendentes = true;			
			if(m.getNome().equals("baixabem"))			
				temAcessoBaixaBem = true;	
			if(m.getNome().equals("movimentobem"))			
				temAcessoMovimentoBem = true;		
			if(m.getNome().equals("controlesinal"))			
				temAcessoControleSinal = true;			
			if(m.getNome().equals("ibopemidiamais"))			
				temAcessoIbopeMidiaMais = true;	
			if(m.getNome().equals("controletarefa"))			
				temAcessoControleTarefa = true;			
			if(m.getNome().equals("controletarefavisao"))			
				temAcessoControleTarefaVisao =true;	
			if(m.getNome().equals("controletransporte"))			
				temAcessoControleTransporte=true;							
		}
	}
	
	private void paginaInicial() throws IOException{
		this.msgBoasVindas = ""+this.login.getNome();	
		FacesContext cx = FacesContext.getCurrentInstance();
		cx.getExternalContext().redirect("sistema/index.jsf");
	}
	
	public void controleSinal() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("controlesinal.jsf");
		}
	}

	public void controleTarefa() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("controletarefa.jsf");
		}
	}

	public void controleTarefaVisao() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("controletarefavisao.jsf");
		}
	}
	
	public void programaRelacionamento() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("programarelacionamento.jsf");
		}
	}

	public void posvenda() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("posvenda.jsf");
		}
	}
	
	public void importarMidiaMais() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("importarmidiamais.jsf");
		}
	}
	
	public void importarIbope() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("importaribope.jsf");
		}
	}
	
	public void relControleSinal() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("relcontrolesinal.jsf");
		}
	}

	public void contratoVisao() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("contratovisao.jsf");
		}
	}
	
	public void prestacao() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("prestacao.jsf");
		}
	}

	public void adiantamento() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("adiantamento.jsf");
		}
	}
	
	public void posto() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("posto.jsf");
		}
	}

	public void engloc() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("engloc.jsf");
		}
	}
	
	public void notaDebito() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("notadebitoenergia.jsf");
		}
	}

	public void notaDebitoConsolidado() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("notadebitoenergiaconsolidado.jsf");
		}
	}
	
	public void importarHolerite() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("importarholerite.jsf");	
		}
	}

	public void holerite() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("holerite.jsf");
		}	
	}	
	
	public void selecaoHolerite() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("holeriteSelecao.jsf");
		}
	}
 	
	public void cadastroUsers() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("usuario.jsf");
		}
    }

	public void cadastroUsersRh() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("criarusuario.jsf");
		}
    }	
	
	public void contrato() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("contrato.jsf");
		}
	}

	public void irpf() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("impostoderenda.jsf");	
		}
	}
	
	public void comprasContrato() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("impostoderenda.jsf");	
		}
	}
	
	public void comprasFornecedor() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("fornecedor.jsf");	
		}
	}

	public void comprasCliente() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("cliente.jsf");	
		}
	}
	
	public void comprasTipoContrato() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("tipocontrato.jsf");
		}
	}

	public void tiAtendente() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("atendente.jsf");	
		}
	}
	
	public void tiLicenca() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("licenca.jsf");	
		}
	}
	
	public void tiLocalEquipamento() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("localequipamento.jsf");
		}
	}
	
	public void trocarSenhaLogin(){
		RequestContext.getCurrentInstance().execute("PF('dgTroca').show()");
	}

	public void tiEquipamento() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("equipamento.jsf");
		}
	}
	
	public void engenhariaTv() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("tv.jsf");	
		}
	}

	public void sig() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("downloadsig.jsf");
		}
	}
	
	public void consolidado() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("consolidado.jsf");	
		}
	}
	
	public void importarconsolidado() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("importconsolidado.jsf");
		}
	}	

	public void controleConsolidado() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("controleconsolidado.jsf");	
		}
	}	
	
	public void baixaBem() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("baixabem.jsf");	
		}
	}		

	public void movimentoBem() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("movimentobem.jsf");
		}
	}			
	
	public void importarFluxoDiario() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("importarfluxodiario.jsf");	
		}
	}		
		
	public void fluxodiario() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("fluxodiario.jsf");
		}
	}		
	
	public void tiPainel() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("painelti.jsf");	
		}
	}		

	public void tiSolicitacaoAbertas() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("solicitacaoaberta.jsf");	
		}
	}
	
	public void tiSolicitacaoFechadas() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("solicitacaofechada.jsf");	
		}
	}
	
	public void tiAreaSolicitacao() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("areasolicitacao.jsf");	
		}
	}
	
	public void chamado() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("solicitacao.jsf");
		}
	}			
	
	public void requisicaoContrato() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("requisicaocontrato.jsf");
		}
	}		
	
	public void cadTipoDespTransp() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("tipodespesatransporte.jsf");
		}
	}

	public void cadGastoTransp() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("gastotransporte.jsf");
		}
	}
	
	public void viewGastoTransp() throws IOException{
		if (this.login.getId()==null){
			sair();
		}else{
			FacesContext cx = FacesContext.getCurrentInstance();
			cx.getExternalContext().redirect("gastotranspconsolidado.jsf");
		}
	}
		
	public void trocarSenha(){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentWidth", 400);
        RequestContext.getCurrentInstance().openDialog("trocarsenha", options, null);
	}
	
	public void alterarSenhaLogin() throws IOException{
		if(!this.senha1.equals(this.senha2)){
			msg = "As senhas devem ser iguais.";
			mensagens();
		}else{
			try {
				this.login.setSenha(Cripto.criptografa(getSenha1()));
				this.login.setTrocarSenha(0);
				ln.atualiza(this.login);
				paginaInicial();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void alterarSenha() throws IOException{
		if(!this.senha1.equals(this.senha2)){
			msg = "As senhas devem ser iguais.";
			mensagens();
		}else{
			try {
				this.login = ln.findById(this.login);
				this.login.setSenha(Cripto.criptografa(getSenha1()));
				this.login.setTrocarSenha(1);
				this.login.setDataAlteracao(new Date(System.currentTimeMillis()));
				this.login.setUsuarioAlteracao(UsuarioLogado.getUser());
				ln.atualiza(this.login);
				RequestContext.getCurrentInstance().closeDialog("trocarsenha");
			} catch (NoSuchAlgorithmException e) {
				RequestContext.getCurrentInstance().closeDialog("trocarsenha");
				e.printStackTrace();
			}
		}
	}
   
    public void home() throws IOException{
		if (this.login.getId()==null)
			sair();
    	FacesContext cx = FacesContext.getCurrentInstance();
    	cx.getExternalContext().redirect("index.jsf");
    }
       
    public void sair() throws IOException {
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
        sessao.setAttribute("login", null);
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();    
        sessao.invalidate();       
        this.login = null;
	    temAcessoCliente = false;
	    temAcessoContrato = false;
	    temAcessoFornecedor = false;
	    temAcessoHolerite = false;
	    temAcessoHoleriteselecao = false;
	    temAcessoImpostoDeRenda = false;
	    temAcessoPrestacaoDeContas = false;
	    temAcessoTipoContrato = false;
	    temAcessoUsuario = false;
	    temAcessoUsuariosPrestacao = false;
	    temAcessoUsuariosAcessos = false;
	    temAcessoEquipamentos = false;
	    temAcessoImportacaoHolerite = false;	
		cx.getExternalContext().redirect(request.getContextPath()+"/login.jsf");
    }

 	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

 	
 	
 /*======================== Getters and Setters ==================================*/
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getSenha1() {
		return senha1;
	}

	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public String getMsgBoasVindas() {
		return msgBoasVindas;
	}

	public void setMsgBoasVindas(String msgBoasVindas) {
		this.msgBoasVindas = msgBoasVindas;
	}

	public LoginLN getLn() {
		return ln;
	}

	public void setLn(LoginLN ln) {
		this.ln = ln;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Modulo> getTodosModulos() {
		return todosModulos;
	}

	public void setTodosModulos(List<Modulo> todosModulos) {
		this.todosModulos = todosModulos;
	}

	public Boolean getTemAcessoCliente() {
		return temAcessoCliente;
	}

	public void setTemAcessoCliente(Boolean temAcessoCliente) {
		this.temAcessoCliente = temAcessoCliente;
	}

	public Boolean getTemAcessoContrato() {
		return temAcessoContrato;
	}

	public void setTemAcessoContrato(Boolean temAcessoContrato) {
		this.temAcessoContrato = temAcessoContrato;
	}

	public Boolean getTemAcessoFornecedor() {
		return temAcessoFornecedor;
	}

	public void setTemAcessoFornecedor(Boolean temAcessoFornecedor) {
		this.temAcessoFornecedor = temAcessoFornecedor;
	}

	public Boolean getTemAcessoHolerite() {
		return temAcessoHolerite;
	}

	public void setTemAcessoHolerite(Boolean temAcessoHolerite) {
		this.temAcessoHolerite = temAcessoHolerite;
	}

	public Boolean getTemAcessoHoleriteselecao() {
		return temAcessoHoleriteselecao;
	}

	public void setTemAcessoHoleriteselecao(Boolean temAcessoHoleriteselecao) {
		this.temAcessoHoleriteselecao = temAcessoHoleriteselecao;
	}

	public Boolean getTemAcessoImpostoderenda() {
		return temAcessoImpostoDeRenda;
	}

	public void setTemAcessoImpostoderenda(Boolean temAcessoImpostoderenda) {
		this.temAcessoImpostoDeRenda = temAcessoImpostoderenda;
	}

	public Boolean getTemAcessoPrestacaoDeContas() {
		return temAcessoPrestacaoDeContas;
	}

	public void setTemAcessoPrestacaoDeContas(Boolean temAcessoPrestacaoDeContas) {
		this.temAcessoPrestacaoDeContas = temAcessoPrestacaoDeContas;
	}


	public Boolean getTemAcessoTipoContrato() {
		return temAcessoTipoContrato;
	}

	public void setTemAcessoTipoContrato(Boolean temAcessoTipoContrato) {
		this.temAcessoTipoContrato = temAcessoTipoContrato;
	}

	public List<Modulo> getModulosUsuario() {
		return modulosUsuario;
	}

	public void setModulosUsuario(List<Modulo> modulosUsuario) {
		this.modulosUsuario = modulosUsuario;
	}

	public Boolean getTemAcessoUsuario() {
		return temAcessoUsuario;
	}

	public void setTemAcessoUsuario(Boolean temAcessoUsuario) {
		this.temAcessoUsuario = temAcessoUsuario;
	}

	public Boolean getTemAcessoUsuariosPrestacao() {
		return temAcessoUsuariosPrestacao;
	}

	public void setTemAcessoUsuariosPrestacao(Boolean temAcessoUsuariosPrestacao) {
		this.temAcessoUsuariosPrestacao = temAcessoUsuariosPrestacao;
	}

	public Boolean getTemAcessoUsuariosAcessos() {
		return temAcessoUsuariosAcessos;
	}

	public void setTemAcessoUsuariosAcessos(Boolean temAcessoUsuariosAcessos) {
		this.temAcessoUsuariosAcessos = temAcessoUsuariosAcessos;
	}

	public Boolean getTemAcessoEquipamentos() {
		return temAcessoEquipamentos;
	}

	public void setTemAcessoEquipamentos(Boolean temAcessoEquipamentos) {
		this.temAcessoEquipamentos = temAcessoEquipamentos;
	}

	public Boolean getTemAcessoImpostoDeRenda() {
		return temAcessoImpostoDeRenda;
	}

	public void setTemAcessoImpostoDeRenda(Boolean temAcessoImpostoDeRenda) {
		this.temAcessoImpostoDeRenda = temAcessoImpostoDeRenda;
	}

	public Boolean getTemAcessoLicenca() {
		return temAcessoLicenca;
	}

	public void setTemAcessoLicenca(Boolean temAcessoLicenca) {
		this.temAcessoLicenca = temAcessoLicenca;
	}

	public Boolean getTemAcessoLocalEquipamentos() {
		return temAcessoLocalEquipamentos;
	}

	public void setTemAcessoLocalEquipamentos(Boolean temAcessoLocalEquipamentos) {
		this.temAcessoLocalEquipamentos = temAcessoLocalEquipamentos;
	}

	public Boolean getTemAcessoImportacaoHolerite() {
		return temAcessoImportacaoHolerite;
	}

	public void setTemAcessoImportacaoHolerite(Boolean temAcessoImportacaoHolerite) {
		this.temAcessoImportacaoHolerite = temAcessoImportacaoHolerite;
	}

	public Boolean getTemAcessoSig() {
		return temAcessoSig;
	}

	public void setTemAcessoSig(Boolean temAcessoSig) {
		this.temAcessoSig = temAcessoSig;
	}

	public Boolean getTemAcessoConsolidado() {
		return temAcessoConsolidado;
	}

	public void setTemAcessoConsolidado(Boolean temAcessoConsolidado) {
		this.temAcessoConsolidado = temAcessoConsolidado;
	}

	public Boolean getTemAcessoImportConsolidado() {
		return temAcessoImportConsolidado;
	}

	public void setTemAcessoImportConsolidado(Boolean temAcessoImportConsolidado) {
		this.temAcessoImportConsolidado = temAcessoImportConsolidado;
	}

	public Boolean getTemAcessoControletConsolidado() {
		return temAcessoControletConsolidado;
	}

	public void setTemAcessoControletConsolidado(
			Boolean temAcessoControletConsolidado) {
		this.temAcessoControletConsolidado = temAcessoControletConsolidado;
	}

	public Boolean getTemAcessoImportarFluxoDiario() {
		return temAcessoImportarFluxoDiario;
	}

	public void setTemAcessoImportarFluxoDiario(Boolean temAcessoImportarFluxoDiario) {
		this.temAcessoImportarFluxoDiario = temAcessoImportarFluxoDiario;
	}

	public Boolean getTemAcessoFluxoDiario() {
		return temAcessoFluxoDiario;
	}

	public void setTemAcessoFluxoDiario(Boolean temAcessoFluxoDiario) {
		this.temAcessoFluxoDiario = temAcessoFluxoDiario;
	}

	public Boolean getTemAcessoPainelChamado() {
		return temAcessoPainelChamado;
	}

	public void setTemAcessoPainelChamado(Boolean temAcessoPainelChamado) {
		this.temAcessoPainelChamado = temAcessoPainelChamado;
	}

	public Boolean getTemAcessoCadastroSolicitacaoArea() {
		return temAcessoCadastroSolicitacaoArea;
	}

	public void setTemAcessoCadastroSolicitacaoArea(Boolean temAcessoCadastroSolicitacaoArea) {
		this.temAcessoCadastroSolicitacaoArea = temAcessoCadastroSolicitacaoArea;
	}

	public Boolean getTemAcessoAtendentes() {
		return temAcessoAtendentes;
	}

	public void setTemAcessoAtendentes(Boolean temAcessoAtendentes) {
		this.temAcessoAtendentes = temAcessoAtendentes;
	}

	public Boolean getTemAcessoBaixaBem() {
		return temAcessoBaixaBem;
	}

	public void setTemAcessoBaixaBem(Boolean temAcessoBaixaBem) {
		this.temAcessoBaixaBem = temAcessoBaixaBem;
	}

	public Boolean getTemAcessoMovimentoBem() {
		return temAcessoMovimentoBem;
	}

	public void setTemAcessoMovimentoBem(Boolean temAcessoMovimentoBem) {
		this.temAcessoMovimentoBem = temAcessoMovimentoBem;
	}

	public Boolean getTemAcessoControleSinal() {
		return temAcessoControleSinal;
	}

	public void setTemAcessoControleSinal(Boolean temAcessoControleSinal) {
		this.temAcessoControleSinal = temAcessoControleSinal;
	}

	public Boolean getTemAcessoIbopeMidiaMais() {
		return temAcessoIbopeMidiaMais;
	}

	public void setTemAcessoIbopeMidiaMais(Boolean temAcessoIbopeMidiaMais) {
		this.temAcessoIbopeMidiaMais = temAcessoIbopeMidiaMais;
	}

	public Boolean getTemAcessoControleTarefa() {
		return temAcessoControleTarefa;
	}

	public void setTemAcessoControleTarefa(Boolean temAcessoControleTarefa) {
		this.temAcessoControleTarefa = temAcessoControleTarefa;
	}

	public Boolean getTemAcessoControleTarefaVisao() {
		return temAcessoControleTarefaVisao;
	}

	public void setTemAcessoControleTarefaVisao(
			Boolean temAcessoControleTarefaVisao) {
		this.temAcessoControleTarefaVisao = temAcessoControleTarefaVisao;
	}

	public Boolean getTemAcessoControleTransporte() {
		return temAcessoControleTransporte;
	}

	public void setTemAcessoControleTransporte(
			Boolean temAcessoControleTransporte) {
		this.temAcessoControleTransporte = temAcessoControleTransporte;
	}


}
