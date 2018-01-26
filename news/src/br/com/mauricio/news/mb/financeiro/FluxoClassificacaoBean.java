package br.com.mauricio.news.mb.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import br.com.mauricio.news.ln.financeiro.FluxoClassificacaoLN;
import br.com.mauricio.news.model.financeiro.Movimento;
import br.com.mauricio.news.model.financeiro.Titulo;

/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="fluxoclassificaMB")
@ViewScoped
public class FluxoClassificacaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Titulo> titulos;
	private List<Titulo> titulosFiltrados;
	private List<Movimento> movimentos;
	private List<Movimento> movimentosFiltrados;
	private Date dataInicial;
	private Date dataFinal;
	private Integer opcao = 1;
	private Map<Integer,String> classificacao = new HashMap<Integer,String>();
	
    @PostConstruct
    public void init() {
    	titulos = new ArrayList<Titulo>();
    	movimentos = new ArrayList<Movimento>();
    	carregaClassificacao();
    }
    
    private void carregaClassificacao() {
    	classificacao.put(0, "NÃO CLASSIFICADO");
    	classificacao.put(1, "Rec. Local");
    	classificacao.put(2, "Rec. Antecipados");
    	classificacao.put(3, "Rec. Concessionarias PGMS");
    	classificacao.put(4, "Rec. Governo");
    	classificacao.put(5, "Rec. Concessionária Especial");
    	classificacao.put(6, "Rec. Outros");
    	classificacao.put(7, "Rec. Banco Renner");
    	classificacao.put(8, "Empréstimos Obitidos");
    	classificacao.put(9, "Pag. RH");
    	classificacao.put(10, "Pag. PJ e Terceiros");
    	classificacao.put(11, "Pag. Impostos");
    	classificacao.put(12, "Pag. Comercial");
    	classificacao.put(13, "Pag. Marketing");
    	classificacao.put(14, "Pag. Administrativo");
    	classificacao.put(15, "Pag. Operações");
    	classificacao.put(16, "Pag. Investimentos");
    	classificacao.put(17, "Pag. Empréstimos");	
	}

	public void buscar(){
    	FluxoClassificacaoLN ln = new FluxoClassificacaoLN();
    	if(dataInicial != null && dataFinal != null)
    		if(opcao==1)
    			titulos = ln.buscarTitulos(dataInicial, dataFinal);
    		else 
    			movimentos = ln.buscarMovimentos(dataInicial, dataFinal);
    	titulosFiltrados = titulos;
    }
	   
    public void onRowEditTitulo(RowEditEvent event) {
    	Titulo titulo = ((Titulo) event.getObject());
    	if(validaTitulos(titulo)) {	
    		FluxoClassificacaoLN ln = new FluxoClassificacaoLN(); 
    		ln.atualizaTitulo(titulo);	
    		mensagens("Registro atualizado!");
    	}
    }

    public void onRowEditMovimento(RowEditEvent event) {
    	Movimento movimento = ((Movimento) event.getObject());	
    	FluxoClassificacaoLN ln = new FluxoClassificacaoLN(); 
    	ln.atualizaMovimento(movimento);
    	mensagens("Registro atualizado!");
    }
    
	public Boolean validaTitulos(Titulo titulo){
    	boolean vt = true;
		if(titulo.getVencAtual().compareTo(titulo.getDatemi()) < 0){
			vt = false;
			mensagens("Data de Prorrogação menor que a Data de Emissão.");
		}
		if(titulo.getProvPagto().compareTo(titulo.getVencAtual()) < 0){
			vt = false;
			mensagens("Data provável pagamento menor que Vencimento Prorrogado.");
		}
		if(titulo.getProvPagto().compareTo(titulo.getVencAtual()) > 0){
			titulo.setProvPagto(titulo.getVencAtual());
		}		
		if(titulo.getOritit().equals("CR") && (titulo.getClsflx() >= 9 && titulo.getClsflx() <= 17)){
			vt = false;
			mensagens("A Classificação não condiz com a Origem do Título CR. A numeração correspondente é de 1-8");
		}
		if(titulo.getOritit().equals("CP") && (titulo.getClsflx() >= 1 && titulo.getClsflx() <= 8)){
			vt = false;
			mensagens("A Classificação não condiz com a Origem do Título CP. A numeração correspondente é de 9-17");
		}		
		return vt;
		
	}
		
	public void mensagens(String msg){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
    
    
    
/*=====================================================================================================================*/
	public List<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Titulo> getTitulosFiltrados() {
		return titulosFiltrados;
	}

	public void setTitulosFiltrados(List<Titulo> titulosFiltrados) {
		this.titulosFiltrados = titulosFiltrados;
	}

	public Map<Integer, String> getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Map<Integer, String> classificacao) {
		this.classificacao = classificacao;
	}

	public Integer getOpcao() {
		return opcao;
	}

	public void setOpcao(Integer opcao) {
		this.opcao = opcao;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	public List<Movimento> getMovimentosFiltrados() {
		return movimentosFiltrados;
	}

	public void setMovimentosFiltrados(List<Movimento> movimentosFiltrados) {
		this.movimentosFiltrados = movimentosFiltrados;
	}

}
