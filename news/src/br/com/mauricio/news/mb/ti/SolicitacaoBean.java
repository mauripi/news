package br.com.mauricio.news.mb.ti;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.ln.ti.AreaSolicitacaoLN;
import br.com.mauricio.news.ln.ti.HistoricoSolicitacaoLN;
import br.com.mauricio.news.ln.ti.SolicitacaoLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.ti.AreaSolicitacao;
import br.com.mauricio.news.model.ti.Atendente;
import br.com.mauricio.news.model.ti.CategoriaSolicitacao;
import br.com.mauricio.news.model.ti.HistoricoSolicitacao;
import br.com.mauricio.news.model.ti.Solicitacao;
import br.com.mauricio.news.model.ti.StatusSolicitacao;


@ManagedBean(name="solicitacaoMB")
@ViewScoped
public class SolicitacaoBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Solicitacao solicitacao = new Solicitacao();
	private CategoriaSolicitacao categoriaSelecionada=CategoriaSolicitacao.HELPDESK;
	private List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
	private SolicitacaoLN ln;
	private String msg;
	private Login usuarioLogado = new Login();
	private Login favorecido = new Login();
	private List<Login> todosUsuarios = new ArrayList<Login>();
    private LoginLN loginLN;
    private AreaSolicitacao area = new AreaSolicitacao();
	private List<AreaSolicitacao> areas = new ArrayList<AreaSolicitacao>();
	private List<AreaSolicitacao> todaAreas = new ArrayList<AreaSolicitacao>();
	private InputStream is;
	private String nomeArquivo;
	private Date dataabertura;
	private Date horaabertura;
	private HistoricoSolicitacao novaInteracao = new HistoricoSolicitacao();
	private Boolean eAtendente=false;
	private StreamedContent fileDownload;
	
	
	@PostConstruct
  	public void init(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuarioLogado = (Login) sessao.getAttribute("login");
		verificaSeQuemEstaLogadoEAtendente();
		loginLN=new LoginLN();
		todosUsuarios=loginLN.listaUsuarios("nome");
		AreaSolicitacaoLN aln = new AreaSolicitacaoLN();
		todaAreas = aln.getList();
		solicitacao = new Solicitacao();
		listar();
	}

	private void verificaSeQuemEstaLogadoEAtendente(){
 		ln = new SolicitacaoLN();
 		Atendente a = ln.getAtendente(usuarioLogado);
 		if(a.getLogin()!=null)
 			eAtendente=true;
	}
	
	public void listar(){
		ln = new SolicitacaoLN();
		solicitacoes = ln.listByUsuario(usuarioLogado);
	}

	public void novo(){
		solicitacao = new Solicitacao();
		solicitacao.setDataabertura(new Date());
		solicitacao.setHoraabertura(new Date());
		favorecido = usuarioLogado;
		atualizaAreas();
	}

	public void edita(){
		dataabertura = solicitacao.getDataabertura();
		horaabertura = solicitacao.getHoraabertura();
		favorecido = solicitacao.getFavorecido();
		categoriaSelecionada = solicitacao.getCategoria();
		atualizaAreas();
		area = solicitacao.getArea();
	}

	public void cancelar(){
		if(solicitacao.getHistoricos().get(solicitacao.getHistoricos().size()-1).getStatus().equals(StatusSolicitacao.CANCELADA)){
			msg="A solicitação já encontra-se cancelada. Não pode sofrer interações.";
			mensagens();
		}else{
			if(solicitacao.getHistoricos().get(solicitacao.getHistoricos().size()-1).getStatus().equals(StatusSolicitacao.FINALIZADA)){
				msg="A solicitação já encontra-se finalizada. Não pode sofrer interações.";
				mensagens();
			}else{
				novaInteracao = new HistoricoSolicitacao();
				novaInteracao.setData(new Date());
				novaInteracao.setHora(new Date());
				novaInteracao.setUser_interacao(usuarioLogado);
				novaInteracao.setSolicitacao(solicitacao);
				novaInteracao.setStatus(StatusSolicitacao.CANCELADA);
				
			}
		}
	}

	public void novaInteracao(){
		if(solicitacao.getHistoricos().get(solicitacao.getHistoricos().size()-1).getStatus().equals(StatusSolicitacao.CANCELADA)){
			msg="A solicitação já encontra-se cancelada. Não pode sofrer interações.";
			mensagens();
		}else{
			if(solicitacao.getHistoricos().get(solicitacao.getHistoricos().size()-1).getStatus().equals(StatusSolicitacao.FINALIZADA)){
				msg="A solicitação já encontra-se finalizada. Não pode sofrer interações.";
				mensagens();
			}else{
					
				novaInteracao = new HistoricoSolicitacao();
				novaInteracao.setData(new Date());
				novaInteracao.setHora(new Date());
				novaInteracao.setUser_interacao(usuarioLogado);
				novaInteracao.setSolicitacao(solicitacao);
				novaInteracao.setStatus(StatusSolicitacao.NOVA_INTERACAO);
			}
		}
	}

	public void exclui(){


	}
	
	public void gravaNova(){
		ln = new SolicitacaoLN();
		String arquivo="";
		if(salvarArquivo())
			arquivo=nomeArquivo;	
		solicitacao.setSolicitante(usuarioLogado);
		solicitacao.setFavorecido(favorecido);
		solicitacao.setCategoria(categoriaSelecionada);
		solicitacao.setArea(area);		
		msg = ln.create(solicitacao,arquivo);
		ln.enviarEmail(solicitacao);
		limpaCadastro();
		listar();
		mensagens();
	}

	public void limpaCadastro(){
		solicitacao=new Solicitacao();
		novaInteracao=new HistoricoSolicitacao();
		dataabertura = new Date();
		horaabertura = new Date();
		favorecido = new Login();
		categoriaSelecionada = CategoriaSolicitacao.HELPDESK;
		area = new AreaSolicitacao();
	}

	public void adicionarInteracao(){
		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
		if(salvarArquivo())
			novaInteracao.setArquivo(nomeArquivo);		
		msg=(hln.create(novaInteracao));
		ln = new SolicitacaoLN();
		solicitacao=ln.getById(solicitacao.getId());
		ln.enviarEmail(solicitacao);
		listar();
		mensagens();
	}

	public void solicitacaoFeedback(){
		if(solicitacao.getHistoricos().get(solicitacao.getHistoricos().size()-1).getStatus().equals(StatusSolicitacao.CANCELADA)){
			msg="A solicitação encontra-se cancelada. Não pode sofrer interações.";
		}else{
			if(solicitacao.getHistoricos().get(solicitacao.getHistoricos().size()-1).getStatus().equals(StatusSolicitacao.FINALIZADA)){
				msg="A solicitação encontra-se finalizada. Não pode sofrer interações.";
			}else{
				HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
				novaInteracao = new HistoricoSolicitacao();
				novaInteracao.setData(new Date());
				novaInteracao.setHora(new Date());
				novaInteracao.setUser_interacao(usuarioLogado);
				novaInteracao.setSolicitacao(solicitacao);
				novaInteracao.setStatus(StatusSolicitacao.FEEDBACK);
				novaInteracao.setDescricao("Solicitado retorno sobre a solicitação");
				msg=(hln.create(novaInteracao));
				ln = new SolicitacaoLN();
				ln.enviarEmail(ln.getById(solicitacao.getId()));
				listar();			
			}
		}
		mensagens();
	}

	public void solicitacaoCancelar(){
		ln = new SolicitacaoLN();
		msg=ln.cancel(solicitacao, novaInteracao);
		ln.enviarEmail(ln.getById(solicitacao.getId()));
		mensagens();
		listar();
	}
	
  	public SelectItem[] getCategorias() {
  		SelectItem[] items=null;
  		try{
	 		ln = new SolicitacaoLN();
	 		Atendente a = ln.getAtendente(usuarioLogado);
	 		int i = 0;

	 		if(a.getId()!=null){//verifica se quem esta logoado não é o atendente
				items = new SelectItem[CategoriaSolicitacao.values().length];
				for(CategoriaSolicitacao c: CategoriaSolicitacao.values()) 
					items[i++] = new SelectItem(c, c.name());
	 		}else{ //quem está logado é o atendente
	 			items = new SelectItem[CategoriaSolicitacao.values().length-1];
				for(CategoriaSolicitacao c: CategoriaSolicitacao.values()) 
					if(c!=CategoriaSolicitacao.INTERNO)
						items[i++] = new SelectItem(c, c.name());
	 		}
	 		return items;
  		}catch(Exception e){
  			return items;
  		}
 	}
 	
 	public void atualizaAreas(){
 		areas = new ArrayList<AreaSolicitacao>();
  		for(AreaSolicitacao a:todaAreas)
 			if(a.getCategoria()==categoriaSelecionada)
 				areas.add(a);
  		if(areas.size()>0)
  			area = areas.get(0);
 	}

 	public String buscaStatus(List<HistoricoSolicitacao> hs){
 		ln = new SolicitacaoLN();
 		String staus=ln.buscaStatus(hs);
		return staus;	
 	}
 	
	public String buscaStatusHistorico(HistoricoSolicitacao h){
 		String staus="";
 		if(h!=null){
	 		switch(h.getStatus()) {
		 	    case ABERTA:
		 	    	staus="Aberta";
		 	        break;
		 	    case EM_ATENDIMENTO:
		 	    	staus="Em Atendimento";
		 	        break;
		 	    case RESOLVIDA:
		 	    	staus="Resolvida pelo Atendente";
		 	        break;
		 	    case FINALIZADA:
		 	    	staus="Finalizada";
		 	        break;	
		 	    case FEEDBACK:
		 	    	staus="Solicitado FeedBack";
		 	        break;
		 	    case PAUSADA:
		 	    	staus="Parada";
		 	        break;		 	        
		 	    case EM_TRANSFERENCIA:
		 	    	staus="Trasferindo de Atendente";
		 	        break;	
		 	    case EM_ATENDIMENTO_TERCEIRO:
		 	    	staus="Em Atendimento de Terceiro";
		 	        break;
		 	    case CANCELADA:
		 	    	staus="Solicitação Cancelada.";
		 	        break;	 	
		 	    case NOVA_INTERACAO:
		 	    	staus="Nova Interação.";
		 	        break;			 	        
			default:
				break;	
		 		}
	 		}		
		return staus;	
 	}
 	
    public List<Login> completeText(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:todosUsuarios) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(l);     
        return results;
    }

    public boolean naoPodeEditar(List<HistoricoSolicitacao> hs){
    	HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
    	HistoricoSolicitacao h = hln.lastInteraction(hs);
    	if(h.getStatus().equals(StatusSolicitacao.CANCELADA) || h.getStatus().equals(StatusSolicitacao.FINALIZADA))
    		return false;
    	else
    		return true;
     }
    
    public void handleFileUpload(FileUploadEvent event) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        nomeArquivo =  fmt.format(new Date())+"_"+event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
			msg = "Arquivo recebido!";
		} catch (IOException e) {
			msg = "Ocorreu erro ao importar arquivo.";
			System.out.println("SolicitacaoBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    	mensagens();
    }
 
    public void baixarArquivo(String nomeArquivo) {
    	ln = new SolicitacaoLN();
        InputStream stream = ln.enviaArquivoDownload(nomeArquivo);
        fileDownload = new DefaultStreamedContent(stream, "application/zip", "solicitação_"+solicitacao.getId()+".zip");
  
    }
    
    public Boolean habilitaDownload(String nomeArquivo){
    	if(nomeArquivo.length()==0)
    		return true;
    	else
    		return false;
    }
    
    public boolean salvarArquivo(){
    	boolean check=false;
    	if(is!=null){
	    	ln = new SolicitacaoLN();    
	    	msg = ln.recebeArquivoUpload(is,nomeArquivo);
	    	check=true;
	    	if(msg!=""){
	    		msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
	    		mensagens();
	    		check=false;
	    	}
    	}
		return check;
    }

    public List<HistoricoSolicitacao> ordenarHistorico(){
    	List<HistoricoSolicitacao> hs = solicitacao.getHistoricos();
    	if(hs!=null)
	    	Collections.sort(hs);
		return hs;
    }	
     
    public String primeiroNome(String nome){
    	String n[] = nome.split(" ");
    	return n[0];
    }
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
	public Date getToday() {
        return new Date();
    }	
	
	
	//=======================================================================================================================================================//
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public List<Login> getTodosUsuarios() {
		return todosUsuarios;
	}

	public void setTodosUsuarios(List<Login> todosUsuarios) {
		this.todosUsuarios = todosUsuarios;
	}

	public List<AreaSolicitacao> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaSolicitacao> areas) {
		this.areas = areas;
	}

	public List<AreaSolicitacao> getTodaAreas() {
		return todaAreas;
	}

	public void setTodaAreas(List<AreaSolicitacao> todaAreas) {
		this.todaAreas = todaAreas;
	}

	public CategoriaSolicitacao getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(CategoriaSolicitacao categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public AreaSolicitacao getArea() {
		return area;
	}

	public void setArea(AreaSolicitacao area) {
		this.area = area;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
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

	public LoginLN getLoginLN() {
		return loginLN;
	}

	public void setLoginLN(LoginLN loginLN) {
		this.loginLN = loginLN;
	}

	public HistoricoSolicitacao getNovaInteracao() {
		return novaInteracao;
	}

	public void setNovaInteracao(HistoricoSolicitacao novaInteracao) {
		this.novaInteracao = novaInteracao;
	}

	public Boolean geteAtendente() {
		return eAtendente;
	}

	public void seteAtendente(Boolean eAtendente) {
		this.eAtendente = eAtendente;
	}

	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}


}
