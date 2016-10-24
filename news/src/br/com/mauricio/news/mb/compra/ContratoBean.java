package br.com.mauricio.news.mb.compra;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.compra.ContratoLN;
import br.com.mauricio.news.model.compra.Cliente;
import br.com.mauricio.news.model.compra.Contrato;
import br.com.mauricio.news.model.compra.DocContrato;
import br.com.mauricio.news.model.compra.Fornecedor;
import br.com.mauricio.news.model.compra.TipoContrato;
import br.com.mauricio.news.util.UsuarioLogado;

@ManagedBean(name="contratoMB")
@ViewScoped
public class ContratoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Contrato contrato = new Contrato();
	private Contrato contratoSel = new Contrato();
	private ContratoLN ln = new ContratoLN();
	private List<Contrato> contratos = new ArrayList<Contrato>();
	private int controlaCadastro = 0;
	private String msg;
	private Cliente cliente = null;
	private Fornecedor fornecedor = null;
	private List<Fornecedor> todosFornecedores = new ArrayList<Fornecedor>();
	private List<Cliente> todosClientes = new ArrayList<Cliente>();
	private List<DocContrato> documentos = new ArrayList<DocContrato>();
	private DocContrato documento = new DocContrato();
	private List<TipoContrato> tipoContratos = new ArrayList<TipoContrato>();
	private TipoContrato tipoContrato = new TipoContrato();
	private Date dataInicioVigencia;
	private Date dataFimVigencia;
	private Date dataReajuste;
	private Double valor;
	private List<String> erroValidacoes;
	private String CAMINHO_DO_ARQUIVO = "C:\\ARQUIVOS_INTRANET\\COMPRAS\\CONTRATOS\\";
	private String nomeArquivo;
	private InputStream is;
	private String descricao="";
	
	
	@PostConstruct
	public void init(){
		ln = new ContratoLN();
		contratos = ln.getList();
		GenericLN<TipoContrato> gtln = new GenericLN<TipoContrato>();
		GenericLN<Fornecedor> gfln = new GenericLN<Fornecedor>();
		GenericLN<Cliente> gcln = new GenericLN<Cliente>();		
		todosClientes = gcln.listWithoutRemoved("cliente", "nome");
		todosFornecedores = gfln.listWithoutRemoved("fornecedor", "nome");		
		tipoContratos = gtln.listWithoutRemoved("tipocontrato", "descricao");
		limpaCadastro();
	}

	public void listar(){
		ln = new ContratoLN();
		contratos = ln.getList();
	}

	public void novo(){
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(contrato.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			ln = new ContratoLN();
			msg = ln.delete(contrato);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			ln = new ContratoLN();
			contrato.setValor(valor);
			
			if(controlaCadastro==2)	{	
				contrato.setDataAlteracao(new Date(System.currentTimeMillis()));
				contrato.setUsuarioAlteracao(UsuarioLogado.getUser());	
				msg = ln.update(contrato);
			}
			if(controlaCadastro==1){
				contrato.setDataCriacao(new Date(System.currentTimeMillis()));			
				contrato.setUsuarioCriacao(UsuarioLogado.getUser());
				msg = ln.create(contrato);
				controlaCadastro=2;
			}
			exibeArquivo();
			mensagens(); 
			listar();
			//limpaCadastro();	
		}else{
	        FacesContext context = FacesContext.getCurrentInstance();  	          
			for(String s:erroValidacoes)
				context.addMessage(null, new FacesMessage("",s));  
		}
	}
	
	private boolean validaCampos(){
		erroValidacoes = new ArrayList<String>();
		if (contrato.getEspecie()==0)
			erroValidacoes.add("Selecione a espécie do contrato.");
		if (contrato.getEspecie()==1){
			contrato.setFornecedor(fornecedor);
			contrato.setCliente(new Cliente());
		}
		if (contrato.getEspecie()==2){
			contrato.setFornecedor(new Fornecedor());
			contrato.setCliente(cliente);			
		}		
		if(!dataInicioVigencia.equals(null))
			contrato.setInicio(dataInicioVigencia);
		if(!dataFimVigencia.equals(null))
			contrato.setFim(dataFimVigencia);
		contrato.setTipo(tipoContrato);
		return true;	
	}
	
	public void limpaCadastro(){
		contratoSel = contrato = new Contrato();
		fornecedor = null;
		cliente = null;
		erroValidacoes = new ArrayList<String>();
		controlaCadastro=0;
		dataFimVigencia = dataInicioVigencia = null;
		valor = null;
		if(tipoContratos.size()>0)
			tipoContrato=tipoContratos.get(0);
		documentos = new ArrayList<DocContrato>();
		descricao="";
	}

    public List<Cliente> completeCliente(String query) {
        List<Cliente> filtered = new ArrayList<Cliente>(); 
        for (int i = 0; i < todosClientes.size(); i++) 
            if(todosClientes.get(i).getNome().toLowerCase().contains(query.toLowerCase()))
                filtered.add(todosClientes.get(i));              
        return filtered;
    }
    
    public List<Fornecedor> completeFornecedor(String query) {
        List<Fornecedor> filtered = new ArrayList<Fornecedor>(); 
        for (int i = 0; i < todosFornecedores.size(); i++) 
            if(todosFornecedores.get(i).getNome().toLowerCase().contains(query.toLowerCase()))
                filtered.add(todosFornecedores.get(i));              
        return filtered;
    }

    public void atualizaEspecie(){
    	if (contrato.getEspecie()==1)
    		cliente = new Cliente();
    	else
    		fornecedor = new Fornecedor();
     }

	public void selecao(){
		contrato = contratoSel;
		dataFimVigencia=contrato.getFim();
		dataInicioVigencia=contrato.getInicio();
		fornecedor=contrato.getFornecedor();
		cliente=contrato.getCliente();
		valor=contrato.getValor();
		tipoContrato=contrato.getTipo();
		controlaCadastro=0;
		exibeArquivo();
		edita();		
	}

	private void exibeArquivo(){
		ln = new ContratoLN();
		ln.carregaArquivo(contrato,caminho());		
	}
	
	private String caminho(){
		return CAMINHO_DO_ARQUIVO +contrato.getId()+"\\";
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		is=event.getFile().getInputstream();
    	if(is!=null){
    		nomeArquivo =  ((Long) System.currentTimeMillis()).toString();
			ln = new ContratoLN();    
			msg = ln.recebeArquivoUpload(is,nomeArquivo,caminho());				
			if(msg!=""){
				msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
				mensagens();
			}
		} else{
			System.out.println("is é null em ContratoBean.handleFileUpload(FileUploadEvent event)");
		}
    }

	public void salvarArquivo(){
		if(descricao.length()>1){		
	    	ln = new ContratoLN();    
			documento = new DocContrato();
			documento.setArquivo(nomeArquivo);
			documento.setContrato(contrato);
			documento.setDescricao(descricao);
	    	msg = ln.salvarDocumeto(documento);	
	    	documentos.add(documento);
	    	if(contrato.getDocumentos()!=null){	    		
	    		contrato.getDocumentos().add(documento);
	    	}else{
	    		contrato.setDocumentos(new ArrayList<DocContrato>());
	    		contrato.getDocumentos().add(documento);
	    	}
	    	exibeArquivo();
	    	mensagens();   	
	    	descricao="";
		}else{
			msg="Informe a descrição do arquivo.";
			mensagens();
		}
    }
	
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
    //--------------------------GETTERS E SETTERS ----------------------------------
	public Date getToday() {
        return new Date();
    }

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Contrato getContratoSel() {
		return contratoSel;
	}

	public void setContratoSel(Contrato contratoSel) {
		this.contratoSel = contratoSel;
	}

	public ContratoLN getLn() {
		return ln;
	}

	public void setLn(ContratoLN ln) {
		this.ln = ln;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getTodosFornecedores() {
		return todosFornecedores;
	}

	public void setTodosFornecedores(List<Fornecedor> todosFornecedores) {
		this.todosFornecedores = todosFornecedores;
	}

	public List<Cliente> getTodosClientes() {
		return todosClientes;
	}

	public void setTodosClientes(List<Cliente> todosClientes) {
		this.todosClientes = todosClientes;
	}

	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public Date getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(Date dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}

	public Date getDataReajuste() {
		return dataReajuste;
	}

	public void setDataReajuste(Date dataReajuste) {
		this.dataReajuste = dataReajuste;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public List<TipoContrato> getTipoContratos() {
		return tipoContratos;
	}

	public void setTipoContratos(List<TipoContrato> tipoContratos) {
		this.tipoContratos = tipoContratos;
	}

	public TipoContrato getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public List<String> getErroValidacoes() {
		return erroValidacoes;
	}

	public void setErroValidacoes(List<String> erroValidacoes) {
		this.erroValidacoes = erroValidacoes;
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

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public List<DocContrato> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocContrato> documentos) {
		this.documentos = documentos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public DocContrato getDocumento() {
		return documento;
	}

	public void setDocumento(DocContrato documento) {
		this.documento = documento;
	}

}
