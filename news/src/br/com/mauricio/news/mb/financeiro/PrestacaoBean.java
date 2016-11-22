package br.com.mauricio.news.mb.financeiro;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import br.com.mauricio.news.ln.RelatorioLN;
import br.com.mauricio.news.ln.financeiro.PrestacaoLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.financeiro.Despesa;
import br.com.mauricio.news.model.financeiro.PrestacaoConta;

@ManagedBean(name="prestacaoMB")
@SessionScoped
public class PrestacaoBean implements Serializable {

	private static final long serialVersionUID = -5434138924149662559L;
	private Login funcionario = new Login();
	private Login colaborador = new Login();
	private List<Login> colaboradores = new ArrayList<Login>();
	private int controlaCadastro = 0;
	private String msg;
	private PrestacaoConta prestacao = new PrestacaoConta();
	private List<PrestacaoConta> prestacoes = new ArrayList<PrestacaoConta>();
	private List<Despesa> despesaParaRemover = new ArrayList<Despesa>();
	private Despesa despesa = new Despesa();
	private PrestacaoLN ln = new PrestacaoLN();
	private Double totalDespesa = new Double("0.0");
	private Double totalRestituir = new Double("0.0");	
	private Double totalReceber = new Double("0.0");
	private int tipo=1;
	
	
	@PostConstruct
	public void init(){
		prestacao = new PrestacaoConta();
		usuarioLogado();
		listar();		
	}

	private void usuarioLogado(){
		FacesContext cx = FacesContext.getCurrentInstance();
	    HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
	    funcionario = (Login) sessao.getAttribute("login");
	    colaborador = funcionario;
	    colaboradores.add(colaborador);
	    colaboradores.addAll(funcionario.getUsuariosPrestacao());
	}
	
	public void listar(){
		PrestacaoLN pln = new PrestacaoLN();
		prestacoes = pln.listaPorFuncionario(colaborador);
	}

	public void novo(){
		limpaCadastro();
		this.prestacao.setDespesas(new ArrayList<Despesa>());
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(this.prestacao.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			ln = new PrestacaoLN();
			prestacao.setRemovido(1);
			prestacao.setDataCriacao(new Date(System.currentTimeMillis()));
			prestacao.setUsuarioCriacao(funcionario);
			msg = ln.delete(this.prestacao);
			mensagens();
			limpaCadastro();
			usuarioLogado();
			listar();
		}		
	}
	
	public void colaboradorSelececionado(){
		limpaCadastro();
		listar();
	}
	
	public void grava(){		
		if(validaCampos()){
			ln = new PrestacaoLN();
			prestacao.setColaborador(colaborador);
			if(controlaCadastro==1){
				prestacao.setDataCriacao(new Date(System.currentTimeMillis()));
				prestacao.setUsuarioCriacao(funcionario);
				prestacao.setRemovido(0);
				msg = ln.create(this.prestacao);

			}	
			if(controlaCadastro==2){		
				prestacao.setDataAlteracao(new Date(System.currentTimeMillis()));
				prestacao.setUsuarioAlteracao(funcionario);			
				msg = ln.update(this.prestacao,despesaParaRemover);
			}
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	private boolean validaCampos(){
		if(tipo==1)
			if(this.prestacao.getDataadiantamento()==null)
				return false;
		if(this.prestacao.getMotivodespesa().length()==0)
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		this.prestacao = new PrestacaoConta();
		totalDespesa = new Double("0.0");
		totalReceber = new Double("0.0");
		totalRestituir = new Double("0.0");
		controlaCadastro =0;
		despesaParaRemover = new ArrayList<Despesa>();
	}

	public void imprimir(){
		RelatorioLN rln = new RelatorioLN();
		try {
			rln.geraRelatorioDespesa(prestacao);
		} catch (IOException e) {
			msg="Erro ao gerar relatório. Arquivo não encontrado.";
			mensagens();
			e.printStackTrace();
		} catch (JRException e) {
			msg="Erro ao gerar relatório";
			mensagens();
			e.printStackTrace();
		}
	}
	
	public void prestacaoSelecionada(){
		calculaTotais();
		controlaCadastro=0;
		if(prestacao.getDataadiantamento()==null&&(prestacao.getValoradiantado()==0.0||prestacao.getValoradiantado()==null))
			tipo=2;
		else
			tipo=1;
	}
	
	public void calculaTotais() {
    	totalDespesa = new Double("0.0");
    	if(tipo==2)
    		prestacao.setValoradiantado(new Double("0.0"));
    	if(prestacao.getDespesas().size()>0)
			for(Despesa d:prestacao.getDespesas())
				totalDespesa =totalDespesa + d.getValor();
		
    	if(prestacao.getValoradiantado()!=null){
			if(prestacao.getValoradiantado()>=totalDespesa){
				totalRestituir =prestacao.getValoradiantado()-totalDespesa;
				totalReceber = new Double("0.0");
			}else{
				totalRestituir = new Double("0.0");
				totalReceber=totalDespesa-prestacao.getValoradiantado();			
			}
    	}else{
    		prestacao.setValoradiantado(new Double("0.0"));
    	}
	}

	public String dataFormatada(Date d){
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(d);
	}	
    
	public void addDespesa(){
		if(despesa.getData()==null){
			msg="Informe a data da despesa.";
			mensagens();				
		}else{
			if(tipo==1&&(this.prestacao.getValoradiantado()==null||this.prestacao.getValoradiantado()==0.0)){
				msg="Informe o valor que foi adiantado.";
				mensagens();			
			}else{
				if(prestacao.getDataadiantamento()!=null && (despesa.getData().before(prestacao.getDataadiantamento()))){
					msg="A data da despesa não pode ser anterior à data do adiantamento.";
					mensagens();
				}else{
					despesa.setPrestacaoconta(prestacao);
					despesa.setUsuarioCriacao(funcionario);
					despesa.setDataCriacao(new Date(System.currentTimeMillis()));
					despesa.setUsuarioAlteracao(funcionario);
					despesa.setDataAlteracao(new Date(System.currentTimeMillis()));
					despesa.setRemovido(0);
					this.prestacao.getDespesas().add(despesa);
					calculaTotais();
					despesa=new Despesa();
				}
			}
		}
	}
	
	public void removeDespesa(){
		this.prestacao.getDespesas().remove(despesa);
	}	
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------
	public Date getToday() {
        return new Date();
    }
	
	public Login getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Login funcionario) {
		this.funcionario = funcionario;
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

	public PrestacaoConta getPrestacao() {
		return prestacao;
	}

	public void setPrestacao(PrestacaoConta prestacao) {
		this.prestacao = prestacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public List<PrestacaoConta> getPrestacoes() {
		return prestacoes;
	}

	public void setPrestacoes(List<PrestacaoConta> prestacoes) {
		this.prestacoes = prestacoes;
	}
	public Double getTotalDespesa() {
		return totalDespesa;
	}

	public void setTotalDespesa(Double totalDespesa) {
		this.totalDespesa = totalDespesa;
	}

	public Double getTotalRestituir() {
		return totalRestituir;
	}

	public void setTotalRestituir(Double totalRestituir) {
		this.totalRestituir = totalRestituir;
	}

	public Double getTotalReceber() {
		return totalReceber;
	}

	public void setTotalReceber(Double totalReceber) {
		this.totalReceber = totalReceber;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Login getColaborador() {
		return colaborador;
	}

	public void setColaborador(Login colaborador) {
		this.colaborador = colaborador;
	}

	public List<Login> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Login> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public PrestacaoLN getLn() {
		return ln;
	}

	public void setLn(PrestacaoLN ln) {
		this.ln = ln;
	}

	public List<Despesa> getDespesaParaRemover() {
		return despesaParaRemover;
	}

	public void setDespesaParaRemover(List<Despesa> despesaParaRemover) {
		this.despesaParaRemover = despesaParaRemover;
	}

}
