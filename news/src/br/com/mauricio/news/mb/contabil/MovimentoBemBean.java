package br.com.mauricio.news.mb.contabil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.contabil.MovimentoBemLN;
import br.com.mauricio.news.ln.contabil.RelMovimentoBemLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.contabil.DocMovimentoBem;
import br.com.mauricio.news.model.contabil.ItemMovimento;
import br.com.mauricio.news.model.contabil.MovimentoBem;
import br.com.mauricio.news.model.contabil.Patrimonio;
import br.com.mauricio.news.util.ValidaCNPJ;
import br.com.mauricio.news.util.ValidaCPF;

@ManagedBean(name="movimentobemMB")
@ViewScoped
public class MovimentoBemBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private MovimentoBem movimentobem = new MovimentoBem();
	private MovimentoBem movimentobemSel = new MovimentoBem();
	private GenericLN<MovimentoBem> gln = new GenericLN<MovimentoBem>();
	private List<MovimentoBem> movimentos = new ArrayList<MovimentoBem>();
	private int controlaCadastro = 0;
	private String msg;
	private List<String> msgs;
	private List<ItemMovimento> itens = new ArrayList<ItemMovimento>();
	private List<ItemMovimento> itensRemovido = new ArrayList<ItemMovimento>();
	private Login usuario = new Login();
    private List<Patrimonio> patrimonios = new ArrayList<Patrimonio>();
    private String codigo;
    private String nomeBem;
    private Date data = new Date();
    private DocMovimentoBem documento = new DocMovimentoBem();
    private List<DocMovimentoBem> documentos = new ArrayList<DocMovimentoBem>();
	private String CAMINHO_DO_ARQUIVO = "C:\\ARQUIVOS_INTRANET\\CONTABILIDADE\\MOVIMENTOBEM\\";
	private String nomeArquivo;
	private String nomeDoArquivoAnexado;
	private InputStream is;
	private String descricao="";
	private Double qtdBem=new Double("0.0");
	private boolean skip;
       
	@PostConstruct
	public void init(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuario = (Login) sessao.getAttribute("login");
		movimentobem = new MovimentoBem();
		listar();		
	}
	
	public void listar(){		
		if(usuario.getChapa().equals("000746")||usuario.getChapa().equals("000772")||usuario.getChapa().equals("000755")||usuario.getChapa().equals("000763")){
			gln = new GenericLN<MovimentoBem>();
			movimentos =  gln.listWithoutRemoved("movimentobem", "id desc");
		}else{
			MovimentoBemLN bln = new MovimentoBemLN();
			movimentos = bln.listaPorUsuario(usuario);
		}
	}

	public void listarParaUsersContabilidade(){
		if(usuario.getCusto().getNome().equals("Contabilidade")){
			gln = new GenericLN<MovimentoBem>();
			movimentos = gln.listWithoutRemoved("movimentobem", "dataemissao desc"); 
		}
	}
	
	public void novo(){
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(movimentobemSel.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			MovimentoBemLN ln = new MovimentoBemLN();		
			msg = ln.remove(movimentobemSel);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}

	public void encontrarBem(){
		MovimentoBemLN ln = new MovimentoBemLN();
		patrimonios = ln.obterPatrimonios(codigo);
		for(Patrimonio p: patrimonios)
			if(p.getNumpla().contains("-00"))
				nomeBem=p.getDesbem();							
	}
	
	public void addItem(){
		if(qtdBem==null){
			msgs.add("Informe a quantidade de bens.");
			mensagens(msgs);
		}else{
			if(patrimonios.size()>0){
				for(Patrimonio p: patrimonios){
					ItemMovimento item = new ItemMovimento();
					item.setDescricao(p.getDesbem());
					item.setNotafiscal(p.getNumdoc());
					item.setPatrimonio(p.getCodbem());
					item.setNomeFornecedor(p.getNomfor());
					item.setNumpla(p.getNumpla());
					item.setMovimentobem(movimentobem);
					itens.add(item);
				}
				patrimonios = new ArrayList<Patrimonio>();
				codigo="";
				nomeBem="";
				qtdBem=new Double("0.0");
			}else{
				if(nomeBem.length()>0||codigo.length()>0){
					ItemMovimento item = new ItemMovimento();
					item.setDescricao(nomeBem.toUpperCase());
					item.setPatrimonio(codigo.toUpperCase());
					item.setNumpla(codigo.toUpperCase());
					item.setMovimentobem(movimentobem);
					item.setQuantidade(qtdBem);
					itens.add(item);	
					codigo="";
					nomeBem="";
					qtdBem=new Double("0.0");
				}else{
					msgs=new ArrayList<String>();
					if(codigo.length()<1)
						msgs.add("Informe o codigo do patrimonio");
					if(nomeBem.length()<1)
						msgs.add("Informe a descrição do patrimonio");
					mensagens(msgs);				
				}
			}
		}
	}

	public void addItensRemovido(ItemMovimento i){
		itensRemovido.add(i);
	}
	
 	public String grava(){
 		
		if(validaCampos()){
			MovimentoBemLN ln = new MovimentoBemLN();			
			movimentobem.setSolicitante(usuario);
			movimentobem.setDatasaida(data);
			movimentobem.setDataemissao(new Date());
			movimentobem.setItens(itens);
			if(controlaCadastro==2)						
				msg = ln.update(movimentobem,itensRemovido);			
			if(controlaCadastro==1){
				msg = ln.add(movimentobem);
				enviaEmail();
			}
					
			mensagens();
			salvarArquivo();
			ln.gerarRelatorio(movimentobem);
			
			listar();
			limpaCadastro();	
		}else{
			mensagens(msgs);
		}
		
		return "movimentobem";
	}
	
	public void enviaEmail() {
		MovimentoBemLN bln = new MovimentoBemLN();
		bln.enviarEmail(movimentobem,caminho());
	}



	public void imprimir() {
		RelMovimentoBemLN rel = new RelMovimentoBemLN();
		try {
			rel.imprimir(movimentobem);
		} catch (Exception e) {
			System.out.println("Erro ao imprimir relatório de movimentação de bens.   " + e. getMessage());
		}
	}

	private boolean validaCampos(){
		msgs = new ArrayList<String>();
		boolean v=true;
		if(usuario==null){
			msgs.add("Usuário inválido.");
			v=false;
		}	
		return v;		
	}
	
	public void limpaCadastro(){
		movimentobem = movimentobemSel = new MovimentoBem();
		patrimonios=new ArrayList<Patrimonio>();
		data = new Date();
		codigo="";
		itens = new ArrayList<ItemMovimento>();
		itensRemovido = new ArrayList<ItemMovimento>();
		controlaCadastro=0;
		nomeDoArquivoAnexado="";
		descricao="";
		is = null;
		msgs = new ArrayList<String>();
	}

	public void selecao(){
		itens = new ArrayList<ItemMovimento>();
		usuario = movimentobem.getSolicitante();
		movimentobemSel = movimentobem;
		data = movimentobem.getDatasaida();
		controlaCadastro=0;
		itensRemovido = new ArrayList<ItemMovimento>();
		itens = movimentobem.getItens();
		exibeArquivo();
		edita();
	}
	
	private void exibeArquivo(){
		MovimentoBemLN ln = new MovimentoBemLN();
		ln.carregaArquivo(movimentobem,caminho());		
	}
	
	private String caminho(){
		return CAMINHO_DO_ARQUIVO +movimentobem.getId()+"\\";
	}

    public void handleFileUpload(FileUploadEvent event) {/****/
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        nomeArquivo = "MovBem_"+ fmt.format(new Date())+".pdf";
        nomeDoArquivoAnexado = "Arquivo selecionado: "+event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
			msg = "Arquivo recebido!";
		} catch (IOException e) {
			msg = "Ocorreu erro ao receber arquivo.";
			System.out.println("MovimentoBemBean.handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    	mensagens();
    }
	
	public boolean salvarArquivo(){
		boolean check=false;
		if(is!=null){
			if(descricao.length()>1){		
				MovimentoBemLN ln = new MovimentoBemLN();    
				documento = new DocMovimentoBem();
				documento.setArquivo(nomeArquivo);
				documento.setMovimentobem(movimentobem);
				documento.setDescricao(descricao);
				ln.recebeArquivoUpload(is,nomeArquivo,caminho());
		    	msg = ln.salvarDocumeto(documento);	
		    	documentos.add(documento);
		    	if(movimentobem.getDocumentos()==null)
		    		movimentobem.setDocumentos(new ArrayList<DocMovimentoBem>());
		    	movimentobem.getDocumentos().add(documento);		    	   	
		    	descricao="";
		    	check=true;
			}else{
				msg="Informe a descrição do arquivo.";
				mensagens();
			}
		}
		return check;
    }
	
	public void selectDocumento(DocMovimentoBem doc){
		documento= doc;
	}
	
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
        	context.addMessage(null, new FacesMessage(m,m));  			
	}
     
    public String onFlowProcess(FlowEvent event) {
    	if(controlaCadastro!=0){
	        if(skip) {
	            skip = false;   //reset in case user goes back
	            return "confirm";
	        }
	        else {
	        	msgs = new ArrayList<String>();
	        	if(event.getOldStep().equals("gerais")){
	        		if(movimentobem.getLocalOrigem().length()<1){
	        			msgs.add("Informe local de Origem.");
	        		}
	        		if(movimentobem.getLocalDestino().length()<1){
	        			msgs.add("Informe local de Destino.");
	        		}
	        		if(movimentobem.getNomeProprietarioDestino().length()==0){
	        			msgs.add("Informe o Proprietario do Local de Destino.");
	        		}
	        		if(movimentobem.getCnpjProprietarioDestino().length()==0){
	        			msgs.add("Caso não haja CNPJ, favor informar o número de cadastro da Matriz (02344518000178).");
	        		}       	
	        		if(movimentobem.getCnpjProprietarioDestino().length()>0 && !ValidaCNPJ.isValidCNPJ(movimentobem.getCnpjProprietarioDestino())){
	        			msgs.add("Cnpj do Proprietario do Local de Destino inválido.");
	        		} 
	        		if(movimentobem.getNomeResponsavelRecepcao().length()<1){
	        			msgs.add("Informe o Responsável pelo recebimento.");
	        		}	
	        		if(movimentobem.getMotivo().length()<1){
	        			msgs.add("Informe o motivo da movimentação.");
	        		}	
	        		if(movimentobem.getCnpjTransportadora().length()>0 && !ValidaCNPJ.isValidCNPJ(movimentobem.getCnpjTransportadora())){
	        			msgs.add("Cnpj da Transportadora inválido.");
	        		}
	        		if(movimentobem.getCpfResponsavelRecepcao().length()>0 && !ValidaCPF.isValidCPF(movimentobem.getCpfResponsavelRecepcao())){
	        			msgs.add("CPF inválido.");
	        		}     		       		
	        		if(data==null){ 
	        			msgs.add("Data de saída inválida.");
	        		}
	        		if(msgs.size()>0){
	        			mensagens(msgs);
	        			return event.getOldStep();
	        		}
	        	}
	        	if(event.getOldStep().equals("patrimonios")){
	        		if(itens.size()<1){
	        			msgs.add("Adicione os itens a serem movimentados.");
	        		}
	        		if(msgs.size()>0){
	        			mensagens(msgs);
	        			return event.getOldStep();
	        		}       		
	        	}        			
	            return event.getNewStep();
	        }
    	}
		return "gerais";
    }	
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------


	public MovimentoBem getMovimentobem() {
		return movimentobem;
	}


	public void setMovimentobem(MovimentoBem movimentobem) {
		this.movimentobem = movimentobem;
	}


	public MovimentoBem getMovimentobemSel() {
		return movimentobemSel;
	}


	public void setMovimentobemSel(MovimentoBem movimentobemSel) {
		this.movimentobemSel = movimentobemSel;
	}


	public GenericLN<MovimentoBem> getGln() {
		return gln;
	}


	public void setGln(GenericLN<MovimentoBem> gln) {
		this.gln = gln;
	}


	public List<MovimentoBem> getMovimentos() {
		return movimentos;
	}


	public void setMovimentos(List<MovimentoBem> movimentos) {
		this.movimentos = movimentos;
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


	public List<ItemMovimento> getItens() {
		return itens;
	}


	public void setItens(List<ItemMovimento> itens) {
		this.itens = itens;
	}


	public Login getUsuario() {
		return usuario;
	}


	public void setUsuario(Login usuario) {
		this.usuario = usuario;
	}


	public List<Patrimonio> getPatrimonios() {
		return patrimonios;
	}


	public void setPatrimonios(List<Patrimonio> patrimonios) {
		this.patrimonios = patrimonios;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getNomeBem() {
		return nomeBem;
	}


	public void setNomeBem(String nomeBem) {
		this.nomeBem = nomeBem;
	}


	public List<String> getMsgs() {
		return msgs;
	}


	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}

	public List<ItemMovimento> getItensRemovido() {
		return itensRemovido;
	}

	public void setItensRemovido(List<ItemMovimento> itensRemovido) {
		this.itensRemovido = itensRemovido;
	}

	public DocMovimentoBem getDocumento() {
		return documento;
	}

	public void setDocumento(DocMovimentoBem documento) {
		this.documento = documento;
	}

	public List<DocMovimentoBem> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocMovimentoBem> documentos) {
		this.documentos = documentos;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeDoArquivoAnexado() {
		return nomeDoArquivoAnexado;
	}

	public void setNomeDoArquivoAnexado(String nomeDoArquivoAnexado) {
		this.nomeDoArquivoAnexado = nomeDoArquivoAnexado;
	}

	public Double getQtdBem() {
		return qtdBem;
	}

	public void setQtdBem(Double qtdBem) {
		this.qtdBem = qtdBem;
	}
	
    public boolean isSkip() {
        return skip;
    }
 
    public void setSkip(boolean skip) {
        this.skip = skip;
    }
}
