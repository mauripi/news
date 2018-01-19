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

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.mauricio.news.ln.financeiro.TituloLN;
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
	private Date dataInicial;
	private Date dataFinal;
	private Map<Integer,String> classificacao = new HashMap<Integer,String>();
	
    @PostConstruct
    public void init() {
    	titulos = new ArrayList<Titulo>();
    	titulosFiltrados = new ArrayList<Titulo>();
    	carregaClassificacao();
    }
    
    private void carregaClassificacao() {
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

	public void buscarTitulos(){
    	TituloLN ln = new TituloLN();
    	if(dataInicial != null && dataFinal != null)
    		titulos = ln.buscarTitulos(dataInicial, dataFinal);
    	titulosFiltrados = titulos;
    }
    
    public void onRowEdit(RowEditEvent event) {
    	TituloLN ln = new TituloLN();
    	Titulo titulo = ((Titulo) event.getObject());
    	ln.atualizaTitulo(titulo);
    }
	
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        System.out.println(event.getRowKey());
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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
    
}
