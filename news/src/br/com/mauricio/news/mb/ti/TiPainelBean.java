package br.com.mauricio.news.mb.ti;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.ln.ti.SolicitacaoLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.ti.Atendente;


@ManagedBean(name="tipainelMB")
@ViewScoped
public class TiPainelBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SolicitacaoLN ln;
	private String msg;
	private Login usuarioLogado = new Login();
    private LoginLN loginLN;
	private Boolean eAtendente=false;
	private DashboardModel dashmodel;
	private Integer quantidadeAbertoMatriz=0;
	private Integer quantidadeAbertoAraraquara=0;
	private Integer quantidadePausadoMatriz=0;
	private Integer quantidadePausadoAraraquara=0;
	private Integer quantidadeEmTransferenciaMatriz=0;
	private Integer quantidadeEmTransferenciaAraraquara=0;	
	private Integer quantidadeEmAndamentoMatriz=0;
	private Integer quantidadeEmAndamentoAraraquara=0;	
	private Integer quantidadeResolvidaMatriz=0;
	private Integer quantidadeResolvidaAraraquara=0;	
	
	@PostConstruct
  	public void init(){
		montaDashBoard();
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuarioLogado = (Login) sessao.getAttribute("login");
		verificaSeQuemEstaLogadoEAtendente();
		atualizaEstatistica();
	}

    public void atualizaEstatistica() {
		SolicitacaoLN ln = new SolicitacaoLN();
		List<Integer> estatisticas = ln.atualizaEstatistica();
		if(estatisticas.size()==10){
			quantidadeAbertoMatriz=estatisticas.get(0);
			quantidadeAbertoAraraquara=estatisticas.get(1);
			quantidadePausadoMatriz=estatisticas.get(2);
			quantidadePausadoAraraquara=estatisticas.get(3);
			quantidadeEmTransferenciaMatriz=estatisticas.get(4);
			quantidadeEmTransferenciaAraraquara=estatisticas.get(5);
			quantidadeEmAndamentoMatriz=estatisticas.get(6);
			quantidadeEmAndamentoAraraquara=estatisticas.get(7);	
			quantidadeResolvidaMatriz=estatisticas.get(8);
			quantidadeResolvidaAraraquara=estatisticas.get(9);				
		}
	}

	private void montaDashBoard() {
    	dashmodel = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
         
        column1.addWidget("solicitacao");
        column1.addWidget("atendente");
        column1.addWidget("procedimento");
        
        column2.addWidget("relatorio");
        column2.addWidget("equipamento");
         
        column3.addWidget("licenca");
        column3.addWidget("local");     

        dashmodel.addColumn(column1);
        dashmodel.addColumn(column2);
        dashmodel.addColumn(column3);
    }
    
	private void verificaSeQuemEstaLogadoEAtendente(){
 		ln = new SolicitacaoLN();
 		Atendente a = ln.getAtendente(usuarioLogado);
 		if(a.getLogin()!=null)
 			eAtendente=true;
	}
	//=======================================================================================================================================================//

	public SolicitacaoLN getLn() {
		return ln;
	}

	public void setLn(SolicitacaoLN ln) {
		this.ln = ln;
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

	public LoginLN getLoginLN() {
		return loginLN;
	}

	public void setLoginLN(LoginLN loginLN) {
		this.loginLN = loginLN;
	}

	public Boolean geteAtendente() {
		return eAtendente;
	}

	public void seteAtendente(Boolean eAtendente) {
		this.eAtendente = eAtendente;
	}

	public DashboardModel getDashmodel() {
		return dashmodel;
	}

	public void setDashmodel(DashboardModel dashmodel) {
		this.dashmodel = dashmodel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getQuantidadeAbertoMatriz() {
		return quantidadeAbertoMatriz;
	}

	public void setQuantidadeAbertoMatriz(Integer quantidadeAbertoMatriz) {
		this.quantidadeAbertoMatriz = quantidadeAbertoMatriz;
	}

	public Integer getQuantidadeAbertoAraraquara() {
		return quantidadeAbertoAraraquara;
	}

	public void setQuantidadeAbertoAraraquara(Integer quantidadeAbertoAraraquara) {
		this.quantidadeAbertoAraraquara = quantidadeAbertoAraraquara;
	}

	public Integer getQuantidadePausadoMatriz() {
		return quantidadePausadoMatriz;
	}

	public void setQuantidadePausadoMatriz(Integer quantidadePausadoMatriz) {
		this.quantidadePausadoMatriz = quantidadePausadoMatriz;
	}

	public Integer getQuantidadePausadoAraraquara() {
		return quantidadePausadoAraraquara;
	}

	public void setQuantidadePausadoAraraquara(Integer quantidadePausadoAraraquara) {
		this.quantidadePausadoAraraquara = quantidadePausadoAraraquara;
	}

	public Integer getQuantidadeEmTransferenciaMatriz() {
		return quantidadeEmTransferenciaMatriz;
	}

	public void setQuantidadeEmTransferenciaMatriz(
			Integer quantidadeEmTransferenciaMatriz) {
		this.quantidadeEmTransferenciaMatriz = quantidadeEmTransferenciaMatriz;
	}

	public Integer getQuantidadeEmTransferenciaAraraquara() {
		return quantidadeEmTransferenciaAraraquara;
	}

	public void setQuantidadeEmTransferenciaAraraquara(
			Integer quantidadeEmTransferenciaAraraquara) {
		this.quantidadeEmTransferenciaAraraquara = quantidadeEmTransferenciaAraraquara;
	}

	public Integer getQuantidadeEmAndamentoMatriz() {
		return quantidadeEmAndamentoMatriz;
	}

	public void setQuantidadeEmAndamentoMatriz(Integer quantidadeEmAndamentoMatriz) {
		this.quantidadeEmAndamentoMatriz = quantidadeEmAndamentoMatriz;
	}

	public Integer getQuantidadeEmAndamentoAraraquara() {
		return quantidadeEmAndamentoAraraquara;
	}

	public void setQuantidadeEmAndamentoAraraquara(
			Integer quantidadeEmAndamentoAraraquara) {
		this.quantidadeEmAndamentoAraraquara = quantidadeEmAndamentoAraraquara;
	}

	public Integer getQuantidadeResolvidaMatriz() {
		return quantidadeResolvidaMatriz;
	}

	public void setQuantidadeResolvidaMatriz(Integer quantidadeResolvidaMatriz) {
		this.quantidadeResolvidaMatriz = quantidadeResolvidaMatriz;
	}

	public Integer getQuantidadeResolvidaAraraquara() {
		return quantidadeResolvidaAraraquara;
	}

	public void setQuantidadeResolvidaAraraquara(
			Integer quantidadeResolvidaAraraquara) {
		this.quantidadeResolvidaAraraquara = quantidadeResolvidaAraraquara;
	}
	
}
