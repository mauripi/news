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
import org.primefaces.event.SelectEvent;
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


@ManagedBean(name="solicitacaoabertaMB")
@ViewScoped
public class SolicitacaoAbertaBean  implements Serializable{
	
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
	private String titulo;	
	private Date dataabertura;
	private Date horaabertura;
	private HistoricoSolicitacao novaInteracao = new HistoricoSolicitacao();
	private Boolean eAtendente=false;
	private Boolean estaEmAtendimento=false;
	private Boolean podeAtender=false;
	private Boolean podeTransferir=false;
	private Boolean podeInteragir=false;
	private Boolean podeConcluir=false;
	private Boolean deshabilitarBotaoAnexo=true;
	private HistoricoSolicitacao ultimoHistorico;
	private StreamedContent fileDownload;
	private int filtro=1;
	private boolean filial1;  
    private boolean filial2;
    private boolean filial3;  
	
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
		filial1=true;
		filial2=true;
		filial3=true;
		listar();
	}
    
	private void verificaSeQuemEstaLogadoEAtendente(){
 		ln = new SolicitacaoLN();
 		Atendente a = ln.getAtendente(usuarioLogado);
 		if(a.getLogin()!=null)
 			eAtendente=true;
 		else
 			eAtendente=false;
	}

	public void atender(){
		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
		ln = new SolicitacaoLN();		
		novaInteracao = new HistoricoSolicitacao();
		
		novaInteracao.setData(new Date());
		novaInteracao.setHora(new Date());
		novaInteracao.setAtendente(ln.getAtendente(usuarioLogado));
		novaInteracao.setSolicitacao(solicitacao);
		novaInteracao.setUser_interacao(usuarioLogado);
		novaInteracao.setDescricao("Solicitação entrou em atendimento.");
		
		if(!estaEmAtendimento){
			novaInteracao.setStatus(StatusSolicitacao.EM_ATENDIMENTO);
			adicionarInteracao();
			verificaStatusDaSolicitacao();
			msg="Solicitação entrou em atendimento.";
			mensagens();			
		}else{
			if(hln.lastInteraction(solicitacao.getHistoricos()).getStatus().equals(StatusSolicitacao.EM_TRANSFERENCIA)){
				novaInteracao.setStatus(StatusSolicitacao.EM_ATENDIMENTO);	
				adicionarInteracao();
				verificaStatusDaSolicitacao();
				msg="Solicitação entrou em atendimento.";
				mensagens();				
			}else{
				msg="Esta Solicitação já encontra-se em atendimento.";
				mensagens();
			}
		}						
	}

	public void transferir(){
		if(podeTransferir){	
			novaInteracao = new HistoricoSolicitacao();
			titulo="Transferência de Atendente";
			novaInteracao.setData(new Date());
			novaInteracao.setHora(new Date());
			novaInteracao.setAtendente(ln.getAtendente(usuarioLogado));
			novaInteracao.setSolicitacao(solicitacao);
			novaInteracao.setUser_interacao(usuarioLogado);
			novaInteracao.setStatus(StatusSolicitacao.EM_TRANSFERENCIA);
			deshabilitarBotaoAnexo=true;
		}
	}

	public void concluir(){
		if(podeConcluir){	
			novaInteracao = new HistoricoSolicitacao();
			titulo="Resolver a Solicitação";
			novaInteracao.setData(new Date());
			novaInteracao.setHora(new Date());
			novaInteracao.setAtendente(ln.getAtendente(usuarioLogado));
			novaInteracao.setSolicitacao(solicitacao);
			novaInteracao.setUser_interacao(usuarioLogado);
			novaInteracao.setStatus(StatusSolicitacao.RESOLVIDA);
			deshabilitarBotaoAnexo=true;
		}
	}

	public void listar(){
		ln = new SolicitacaoLN();
		switch (filtro) {
			case 1:				
				solicitacoes = ln.getListAbertas();			
				break;
			case 2:
				solicitacoes = ln.getResolvidas();			
				break;
			default:
				solicitacoes = ln.getListAbertas();
				break;
			}
	}

	public void listarPorFilial(){
		List<Solicitacao> list = new ArrayList<Solicitacao>();
		listar();
		if(filial1)
			for(Solicitacao s:solicitacoes)
				if(s.getFavorecido().getFilial().getId()==1)
					list.add(s);
		if(filial2)
			for(Solicitacao s:solicitacoes)
				if(s.getFavorecido().getFilial().getId()==2)
					list.add(s);
		if(filial3)
			for(Solicitacao s:solicitacoes)
				if(s.getFavorecido().getFilial().getId()==3)
					list.add(s);
		solicitacoes = new ArrayList<Solicitacao>();
		solicitacoes = list;
	}
	
	private void buscaUltimoHistorico(){
		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
		ultimoHistorico = hln.lastInteraction(solicitacao.getHistoricos());
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
				titulo="Interação na Solicitação";
				novaInteracao = new HistoricoSolicitacao();
				novaInteracao.setData(new Date());
				novaInteracao.setHora(new Date());
				novaInteracao.setAtendente(ln.getAtendente(usuarioLogado));
				novaInteracao.setUser_interacao(usuarioLogado);
				novaInteracao.setSolicitacao(solicitacao);
				novaInteracao.setStatus(StatusSolicitacao.EM_ATENDIMENTO);
				deshabilitarBotaoAnexo=false;
			}
		}
	}

	public void adicionarInteracao(){
		if(novaInteracao.getDescricao().length()>0){
			if(novaInteracao.getStatus().equals(StatusSolicitacao.RESOLVIDA)){
				ln = new SolicitacaoLN();
				msg=ln.resolved(novaInteracao);
			}else{
				String arquivo="";
				if(salvarArquivo())
					arquivo=nomeArquivo;
				novaInteracao.setArquivo(arquivo);
				HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();				
				msg=(hln.create(novaInteracao));
				ln.enviarEmail(solicitacao,ln.getAtendente(usuarioLogado));
			}
			novaInteracao = new HistoricoSolicitacao();
			solicitacao = ln.getById(solicitacao.getId());//atualiza solicitação
			listar();
		}else{
			msg="Informe os detalhes.";
		}
		verificaStatusDaSolicitacao();
		mensagens();
	}

	public void limpaCadastro(){
		solicitacao=new Solicitacao();
		novaInteracao=new HistoricoSolicitacao();
		dataabertura = new Date();
		horaabertura = new Date();
		favorecido = new Login();
		categoriaSelecionada = CategoriaSolicitacao.HELPDESK;
		titulo="";
		area = new AreaSolicitacao();
	}
	
  	public SelectItem[] getCategorias() {
  		SelectItem[] items=null;
  		try{
	 		ln = new SolicitacaoLN();
	 		Atendente a = ln.getAtendente(usuarioLogado);
	 		int i = 0;
	 		
	 		if(!a.equals(null)&&usuarioLogado.getId()!=a.getLogin().getId()){//verifica se quem esta logoado não é o atendente
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
		 	    	staus="Resolvida";
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
		 	    	staus="Trasferindo";
		 	        break;	
		 	    case EM_ATENDIMENTO_TERCEIRO:
		 	    	staus="Atendimento de Terceiro";
		 	        break;
		 	    case CANCELADA:
		 	    	staus="Cancelada";
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
 	
    public boolean naoPodeEditar(List<HistoricoSolicitacao> hs){
    	HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
    	HistoricoSolicitacao h = hln.lastInteraction(hs);
    	if(h.getStatus().equals(StatusSolicitacao.CANCELADA) || h.getStatus().equals(StatusSolicitacao.FINALIZADA))
    		return false;
    	else
    		return true;
     }

    public void onRowSelect(SelectEvent event) {
        solicitacao = (Solicitacao) event.getObject(); 
        verificaStatusDaSolicitacao();
    }
 
    private void verificaStatusDaSolicitacao(){
        HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
        ln = new SolicitacaoLN();
        Atendente a = ln.getAtendente(usuarioLogado);
        buscaUltimoHistorico();
        
        estaEmAtendimento = hln.verificaSeJaEntrouEmAtendimento(solicitacao.getHistoricos());
 		
 		switch (ultimoHistorico.getStatus().ordinal()) {
			case 0: //ABERTA
				podeAtender=true;
				podeConcluir=false;
				podeTransferir=false;
				if(!estaEmAtendimento)
	 				podeInteragir=false;  
				else
					podeInteragir=true;
				break;
				
			case 1: //EM_ATENDIMENTO
				podeAtender=false;
				podeConcluir=true;
				podeTransferir=true;
				podeInteragir=true;
				break;
				
			case 2: //RESOLVIDA
				podeAtender=false;
				podeConcluir=false;
				podeInteragir=false;
				podeTransferir=false;
				break;
				
			case 3: //FINALIZADA
				podeAtender=false;
				podeConcluir=false;
				podeInteragir=false;
				podeTransferir=false;
				break;
				
			case 4: //CANCELADA
				podeAtender=false;
				podeConcluir=false;
				podeTransferir=false;
				podeInteragir=false;
				break;
				
			case 5: //FEEDBACK
				podeAtender=false;
				podeConcluir=false;
				podeTransferir=true;
				podeInteragir=true;
				break;
				
			case 6: //EM_TRANSFERENCIA
				podeAtender=true;
				if(ultimoHistorico.getAtendente().getId().equals(a.getId())){
					podeConcluir=false; 	
					podeTransferir=false;
					podeInteragir=true;
				}else{
					podeConcluir=false;
					podeTransferir=false;
					podeInteragir=false;
				}
				break;
			case 7: //EM_ATENDIMENTO_TERCEIRO
				podeAtender=true;
				podeConcluir=true;
				podeTransferir=true;
				podeInteragir=true;
				break;
			case 8: //PAUSADA
				podeAtender=true;
				podeConcluir=false;
				podeTransferir=false;
				podeInteragir=true;
				break;				
			case 9: //NOVA_INTERACAO
				podeConcluir=true;
				podeTransferir=true;
				podeInteragir=true;
				break;					
			default:
				podeConcluir=false;
				podeInteragir=false;
				podeTransferir=false;
				break;
		}

    }

    public List<HistoricoSolicitacao> ordenarHistorico(){
    	List<HistoricoSolicitacao> hs = solicitacao.getHistoricos();
    	if(hs!=null)
	    	Collections.sort(hs);
		return hs;
    }	

    public void alterarTipo(){
    	categoriaSelecionada=solicitacao.getCategoria();
    	atualizaAreas();
    	area = solicitacao.getArea();
    }
 
    public void atualizarSolicitacao(){
    	solicitacao.setArea(area);
    	solicitacao.setCategoria(categoriaSelecionada);
		ln = new SolicitacaoLN();
		msg=ln.update(solicitacao);
		mensagens();
		ln.enviarEmailAlteracao(solicitacao,ln.getAtendente(usuarioLogado));
    }
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
	public Date getToday() {
        return new Date();
    }	
	
    public void handleFileUpload(FileUploadEvent event) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        nomeArquivo =  fmt.format(new Date())+"_"+event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
		} catch (IOException e) {
			msg = "Ocorreu erro ao importar arquivo.";
			System.out.println("SolicitacaoBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
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

    public String primeiroNome(String nome){
    	String n[] = nome.split(" ");
    	return n[0];
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

	public Boolean getEstaEmAtendimento() {
		return estaEmAtendimento;
	}

	public void setEstaEmAtendimento(Boolean estaEmAtendimento) {
		this.estaEmAtendimento = estaEmAtendimento;
	}

	public Boolean getPodeTransferir() {
		return podeTransferir;
	}

	public void setPodeTransferir(Boolean podeTransferir) {
		this.podeTransferir = podeTransferir;
	}

	public Boolean getPodeInteragir() {
		return podeInteragir;
	}

	public void setPodeInteragir(Boolean podeInteragir) {
		this.podeInteragir = podeInteragir;
	}

	public Boolean getPodeConcluir() {
		return podeConcluir;
	}

	public void setPodeConcluir(Boolean podeConcluir) {
		this.podeConcluir = podeConcluir;
	}

	public HistoricoSolicitacao getUltimoHistorico() {
		return ultimoHistorico;
	}

	public void setUltimoHistorico(HistoricoSolicitacao ultimoHistorico) {
		this.ultimoHistorico = ultimoHistorico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}

	public Boolean getDeshabilitarBotaoAnexo() {
		return deshabilitarBotaoAnexo;
	}

	public void setDeshabilitarBotaoAnexo(Boolean deshabilitarBotaoAnexo) {
		this.deshabilitarBotaoAnexo = deshabilitarBotaoAnexo;
	}

	public int getFiltro() {
		return filtro;
	}

	public void setFiltro(int filtro) {
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

	public boolean isFilial3() {
		return filial3;
	}

	public void setFilial3(boolean filial3) {
		this.filial3 = filial3;
	}

	public Boolean getPodeAtender() {
		return podeAtender;
	}

	public void setPodeAtender(Boolean podeAtender) {
		this.podeAtender = podeAtender;
	}

}
