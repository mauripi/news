package br.com.mauricio.news.mb.manutencao;

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
import br.com.mauricio.news.ln.manutencao.PedidoManutencaoLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.manutencao.PedidoManutencao;


/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@ManagedBean(name="pedidomanutencaoMB")
@ViewScoped
public class PedidoManutencaoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private PedidoManutencao pedido;
	private List<PedidoManutencao> pedidos = new ArrayList<PedidoManutencao>();
	private List<PedidoManutencao> pedidosUsuario = new ArrayList<PedidoManutencao>();
	private List<Login> todosUsuarios = new ArrayList<Login>();
	private String msg;
	private Login usuarioLogado = new Login();
	private Login favorecido = new Login();
	private Date dataabertura;
	private Date horaabertura;
	private PedidoManutencaoLN ln;
	private Boolean filtro=false;
	private boolean filial1;  
    private boolean filial2;	
	
	@PostConstruct
  	public void init(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuarioLogado = (Login) sessao.getAttribute("login");
		pedido = new PedidoManutencao();
		LoginLN loginLN=new LoginLN();
		todosUsuarios=loginLN.listaUsuarios("nome");
		listar();
		listarPorUsuario();
	}
	
	public void novo(){
		pedido = new PedidoManutencao();
		pedido.setDataabertura(new Date());
		pedido.setHoraabertura(new Date());
		favorecido = usuarioLogado;
	}

	public void edita(){
		dataabertura = pedido.getDataabertura();
		horaabertura = pedido.getHoraabertura();
		favorecido = pedido.getFavorecido();
	}	
	
	public void listarPorUsuario(){
		ln = new PedidoManutencaoLN();
		pedidosUsuario = ln.listByUsuario(usuarioLogado);
	}	

	public void listar(){
		ln = new PedidoManutencaoLN();
		pedidos = ln.listarAbertos(filtro);
	}

	public void listarPorFilial(){
		List<PedidoManutencao> list = new ArrayList<PedidoManutencao>();
		listar();
		if(filial1)
			for(PedidoManutencao s:pedidos)
				if(s.getFavorecido().getFilial().getId()==1)
					list.add(s);
		if(filial2)
			for(PedidoManutencao s:pedidos)
				if(s.getFavorecido().getFilial().getId()==2)
					list.add(s);
		pedidos = new ArrayList<PedidoManutencao>();
		pedidos = list;
	}
	
	public void gravar(){
		GenericLN<PedidoManutencao> gln = new GenericLN<PedidoManutencao>();
		pedido.setSolicitante(usuarioLogado);
		pedido.setFavorecido(favorecido);
		if(pedido.getId() != null){
			msg=gln.update(pedido);
			listarPorUsuario();
			listar();
		}else{
			msg = gln.add(pedido);
			listarPorUsuario();
		}
		limpaCadastro();
		mensagens();
	}
	
	public void limpaCadastro(){
		pedido = new PedidoManutencao();
		dataabertura = new Date();
		horaabertura = new Date();
		favorecido = new Login();
	}	
	
    public List<Login> completeText(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:todosUsuarios) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(l);     
        return results;
    }
    
    public void onRowSelect(SelectEvent event) {
    	pedido = (PedidoManutencao) event.getObject(); 

    }
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	public Date getToday() {
        return new Date();
    }

    
/*        GAS              */
	
	public PedidoManutencao getPedido() {
		return pedido;
	}

	public void setPedido(PedidoManutencao pedido) {
		this.pedido = pedido;
	}

	public List<PedidoManutencao> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoManutencao> pedidos) {
		this.pedidos = pedidos;
	}

	public List<PedidoManutencao> getPedidosUsuario() {
		return pedidosUsuario;
	}

	public void setPedidosUsuario(List<PedidoManutencao> pedidosUsuario) {
		this.pedidosUsuario = pedidosUsuario;
	}

	public List<Login> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(List<Login> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
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

	public Login getFavorecido() {
		return favorecido;
	}

	public void setFavorecido(Login favorecido) {
		this.favorecido = favorecido;
	}

	public Date getDataabertura() {
		return dataabertura;
	}

	public void setDataabertura(Date dataabertura) {
		this.dataabertura = dataabertura;
	}

	public Date getHoraabertura() {
		return horaabertura;
	}

	public void setHoraabertura(Date horaabertura) {
		this.horaabertura = horaabertura;
	}

	public PedidoManutencaoLN getLn() {
		return ln;
	}

	public void setLn(PedidoManutencaoLN ln) {
		this.ln = ln;
	}

	public Boolean getFiltro() {
		return filtro;
	}

	public void setFiltro(Boolean filtro) {
		this.filtro = filtro;
	}

	public boolean isFilial1() {
		return filial1;
	}

	public void setFilial1(boolean filial1) {
		this.filial1 = filial1;
	}

	public boolean isFilial2() {
		return filial2;
	}

	public void setFilial2(boolean filial2) {
		this.filial2 = filial2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}    

    

}
