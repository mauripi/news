package br.com.mauricio.news.mb.compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.compra.ContratoLN;
import br.com.mauricio.news.model.compra.Cliente;
import br.com.mauricio.news.model.compra.Contrato;
import br.com.mauricio.news.model.compra.DocContrato;
import br.com.mauricio.news.model.compra.Fornecedor;
import br.com.mauricio.news.model.compra.TipoContrato;



@ManagedBean(name="visaogerencialcontratoMB")
@ViewScoped
public class VisaoGerencialContratoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Contrato contratoSelecionado;
	private TipoContrato tipoSelecionado;
	private List<TipoContrato> tipos = new ArrayList<TipoContrato>();	
	private List<Cliente> clientes = new ArrayList<Cliente>();	
	private List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();	
	private List<Contrato> contratos = new ArrayList<Contrato>();	
	private List<Contrato> contratosSelecionados = new ArrayList<Contrato>();
	private int especie=0;
	private int pnl = 1;
	private List<DocContrato> documentos = new ArrayList<DocContrato>();
	private DocContrato documento = new DocContrato();	
	private String CAMINHO_DO_ARQUIVO = "C:\\ARQUIVOS_INTRANET\\COMPRAS\\CONTRATOS\\";
	private String nomeArquivo;
	
	@PostConstruct
	public void init(){
		ContratoLN cln = new ContratoLN();
		contratos = cln.listaContratosAtivos();
	}

	public void filtraTipoDeContratoPorEspecie(int i){
		contratosSelecionados = new ArrayList<Contrato>();
		especie=i;
		Set<TipoContrato> list = new HashSet<TipoContrato>();	
		tipos = new ArrayList<TipoContrato>();
		for(Contrato c:contratos)
			if(c.getEspecie()==especie)
				list.add(c.getTipo());

		tipos.addAll(list);
		pnl = 2;
	}

	public void filtraTipoDeContratoPorEspecie(){
		contratosSelecionados = new ArrayList<Contrato>();
		Set<TipoContrato> list = new HashSet<TipoContrato>();	
		tipos = new ArrayList<TipoContrato>();
		for(Contrato c:contratos)
			if(c.getEspecie()==especie)
				list.add(c.getTipo());

		tipos.addAll(list);
		pnl = 2;
	}
	
	public void filtraPorEspecieTipo(SelectEvent event){
		contratosSelecionados = new ArrayList<Contrato>();
		tipoSelecionado = (TipoContrato) event.getObject();
		Set<Fornecedor> listForn = new HashSet<Fornecedor>();
		Set<Cliente> listCli = new HashSet<Cliente>();
		fornecedores = new ArrayList<Fornecedor>();
		clientes = new ArrayList<Cliente>();	
		
		for(Contrato c:contratos){
			if((c.getEspecie()==especie)&&(tipoSelecionado.getId()==c.getTipo().getId())){
				if(c.getFornecedor()!=null)
					listForn.add(c.getFornecedor());
				if(c.getCliente()!=null)
					listCli.add(c.getCliente());
			}
		}
		fornecedores.addAll(listForn);
		clientes.addAll(listCli);
		pnl = 3;
	}
	
	public void filtraPorEspecieTipoCliFor(SelectEvent event){
		contratosSelecionados = new ArrayList<Contrato>();
		if(especie==1){
			Fornecedor f = (Fornecedor) event.getObject();
			for(Contrato c:contratos)
				if(c.getFornecedor()!=null)
					if(c.getFornecedor().getId()==f.getId() && c.getEspecie()==especie && c.getTipo().getId()==tipoSelecionado.getId())
						contratosSelecionados.add(c);
		}else{
			Cliente cli = (Cliente) event.getObject();
			for(Contrato c:contratos)
				if(c.getCliente()!=null)
					if(c.getCliente().getId()==cli.getId() && c.getEspecie()==especie && c.getTipo().getId()==tipoSelecionado.getId())
						contratosSelecionados.add(c);
		}
		pnl = 4;
	}
	
	public void voltarPainel(){
		if(pnl>1)
			pnl--;
		else
			pnl=1;
	}

	public void avancarPainel(){
		if(pnl<2)
			pnl=1;
		else
			pnl++;
	}
	
	public void tipoEscepice(int i){
		switch(i) {
			case 1:
				filtraTipoDeContratoPorEspecie(i);
				pnl = 2;
				break;
			case 2:
				pnl = 3;
				break;
			case 3:
				pnl = 4;
				break;
			case 4:
				pnl = 5;
				break;
			default: 
				pnl = 1;
				i=0;
				break;
		}
	}
	
	public void selecao(){
		exibeArquivo();		
	}

	private void exibeArquivo(){
		ContratoLN cln = new ContratoLN();
		cln.carregaArquivo(contratoSelecionado,caminho());		
	}
	
	private String caminho(){
		return CAMINHO_DO_ARQUIVO +contratoSelecionado.getId()+"\\";
	}	
	
	
	
	public Contrato getContratoSelecionado() {
		return contratoSelecionado;
	}

	public void setContratoSelecionado(Contrato contratoSelecionado) {
		this.contratoSelecionado = contratoSelecionado;
	}

	public int getPnl() {
		return pnl;
	}

	public void setPnl(int pnl) {
		this.pnl = pnl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Contrato> getContratos() {
		return contratos;
	}
	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public TipoContrato getTipoSelecionado() {
		return tipoSelecionado;
	}

	public void setTipoSelecionado(TipoContrato tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

	public int getEspecie() {
		return especie;
	}

	public void setEspecie(int especie) {
		this.especie = especie;
	}

	public List<TipoContrato> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoContrato> tipos) {
		this.tipos = tipos;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Contrato> getContratosSelecionados() {
		return contratosSelecionados;
	}

	public void setContratosSelecionados(List<Contrato> contratosSelecionados) {
		this.contratosSelecionados = contratosSelecionados;
	}

	public List<DocContrato> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocContrato> documentos) {
		this.documentos = documentos;
	}

	public DocContrato getDocumento() {
		return documento;
	}

	public void setDocumento(DocContrato documento) {
		this.documento = documento;
	}

	public String getCAMINHO_DO_ARQUIVO() {
		return CAMINHO_DO_ARQUIVO;
	}

	public void setCAMINHO_DO_ARQUIVO(String cAMINHO_DO_ARQUIVO) {
		CAMINHO_DO_ARQUIVO = cAMINHO_DO_ARQUIVO;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}
