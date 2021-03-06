package br.com.mauricio.news.mb.rh;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import br.com.mauricio.news.ln.rh.BaseLN;
import br.com.mauricio.news.ln.rh.HoleriteLN;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.RelatorioLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.Base;
import br.com.mauricio.news.model.rh.Holerite;
import br.com.mauricio.news.util.FormataNumero;

/*
* @author MAURICIO CRUZ
*/

@ManagedBean(name="holeriteBean")
@ViewScoped
public class HoleriteBean implements Serializable{

	private static final long serialVersionUID = -1720029126855499058L;
	private int controlaCadastro = 0;
	private String msg;
	private Login usuarioLogado = new Login();
	private Login funcionarioSelecionado = new Login();
	private BaseLN bLn = new BaseLN();
	private List<Base> bases = new ArrayList<Base>();
	private Base baseSelecionada = new Base();
	private List<Holerite> holerites = new ArrayList<Holerite>();
	private Double totalLiquido;
	private Double totalProvento;
	private Double totalDesconto;
	private Double fgtsMes;
	private List<Login> funcionarios = new ArrayList<Login>();
	private int ano=2019;
	
	public HoleriteBean(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		usuarioLogado = (Login) sessao.getAttribute("login");
		carregaSelecaoDosPeriodos(usuarioLogado);
		funcionarios.addAll(getFuncionarios());
	}
	
	public void getHolerite(){
		HoleriteLN hLn = new HoleriteLN();
		holerites = hLn.getHolerites(usuarioLogado, baseSelecionada.getMes(), baseSelecionada.getAno(),baseSelecionada.getPeriodo());
		totalDesconto = hLn.getTotalDesconto(holerites);
		totalProvento = hLn.getTotalProvento(holerites);
		totalLiquido = hLn.getTotalLiquido(totalProvento,totalDesconto);
		fgtsMes = hLn.calculaFGTS(holerites,baseSelecionada,usuarioLogado);		
	}


	public void getHoleriteFuncionario(){
		HoleriteLN hLn = new HoleriteLN();
		holerites = hLn.getHolerites(funcionarioSelecionado, baseSelecionada.getMes(), baseSelecionada.getAno(),baseSelecionada.getPeriodo());
		totalDesconto = hLn.getTotalDesconto(holerites);
		totalProvento = hLn.getTotalProvento(holerites);
		totalLiquido = hLn.getTotalLiquido(totalProvento,totalDesconto);
		fgtsMes = hLn.calculaFGTS(holerites,baseSelecionada,funcionarioSelecionado);
	}
	
	public void usuarioSelecionado(){
		carregaSelecaoDosPeriodos(funcionarioSelecionado);
		controlaCadastro=0;
	}

	private void carregaSelecaoDosPeriodos(Login l){
		this.bases = bLn.listaPeriodosDisponiveis(l,ano); 
	}

	public void carregaSelecaoDosPeriodos(){
		this.bases = bLn.listaPeriodosDisponiveis(usuarioLogado,ano); 
	}

	public void carregaSelecaoDosPeriodosFuncionario(){
		this.bases = bLn.listaPeriodosDisponiveis(funcionarioSelecionado,ano); 
	}
	
	public void getImprimir(){
		RelatorioLN rLn = new RelatorioLN();
		try {
			rLn.geraRelatorioPedido(holerites, totalLiquido, baseSelecionada, 
					totalProvento, totalDesconto, usuarioLogado);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage() + " ===> em holeritebean getImprimir()");
			e.printStackTrace();
		} catch (JRException e) {
			System.out.println(e.getLocalizedMessage() + " ===> em holeritebean getImprimir()");
			msg="Ocorreu um erro ao tentar gerar o relatório. Tente novamente.";
			mensagens();
			e.printStackTrace();
		}
	}
 	
	public void getImprimirHoleriteFuncionario(){
		RelatorioLN rLn = new RelatorioLN();
		try {
			rLn.geraRelatorioPedido(holerites, totalLiquido, baseSelecionada, 
					totalProvento, totalDesconto, funcionarioSelecionado);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage() + " ===> em holeritebean getImprimirHoleriteFuncionario()");
			e.printStackTrace();
		} catch (JRException e) {
			System.out.println(e.getLocalizedMessage() + " ===> em holeritebean getImprimirHoleriteFuncionario()");			
			msg="Ocorreu um erro ao tentar gerar o relatório. Tente novamente.";
			mensagens();
			e.printStackTrace();
		}
	}	

	public String formataCpf(String cpf){
		if (cpf.length()==11) return FormataNumero.cpfToString(cpf);
		return cpf;
	}
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));
	}
 	/*=============GETTERS E SETTERS====================*/

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BaseLN getbLn() {
		return bLn;
	}

	public void setbLn(BaseLN bLn) {
		this.bLn = bLn;
	}

	public List<Base> getBases() {
		return bases;
	}

	public void setBases(List<Base> bases) {
		this.bases = bases;
	}

	public Base getBaseSelecionada() {
		return baseSelecionada;
	}

	public void setBaseSelecionada(Base baseSelecionada) {
		this.baseSelecionada = baseSelecionada;
	}

	public List<Holerite> getHolerites() {
		return holerites;
	}

	public void setHolerites(List<Holerite> holerites) {
		this.holerites = holerites;
	}

	public Double getTotalLiquido() {
		return totalLiquido;
	}

	public void setTotalLiquido(Double totalLiquido) {
		this.totalLiquido = totalLiquido;
	}

	public Double getTotalProvento() {
		return totalProvento;
	}

	public void setTotalProvento(Double totalProvento) {
		this.totalProvento = totalProvento;
	}

	public Double getTotalDesconto() {
		return totalDesconto;
	}

	public void setTotalDesconto(Double totalDesconto) {
		this.totalDesconto = totalDesconto;
	}

	public Double getFgtsMes() {
		return fgtsMes;
	}

	public void setFgtsMes(Double fgtsMes) {
		this.fgtsMes = fgtsMes;
	}


	public List<Login> getFuncionarios() {
		GenericLN<Login> ln = new GenericLN<>();
		return ln.listAll(usuarioLogado, "chapa");
	}

	public void setFuncionarios(List<Login> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Login getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(Login funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
 	
}
