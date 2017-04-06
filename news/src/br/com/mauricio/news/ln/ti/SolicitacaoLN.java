package br.com.mauricio.news.ln.ti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.ti.AtendenteDao;
import br.com.mauricio.news.dao.ti.SolicitacaoDao;
import br.com.mauricio.news.dao.ti.SolicitacaoResolvidaDao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.ti.Atendente;
import br.com.mauricio.news.model.ti.HistoricoSolicitacao;
import br.com.mauricio.news.model.ti.Solicitacao;
import br.com.mauricio.news.model.ti.SolicitacaoResolvida;
import br.com.mauricio.news.model.ti.StatusSolicitacao;
import br.com.mauricio.news.util.Email;
import br.com.mauricio.news.util.ValidaEmail;


public class SolicitacaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Solicitacao> dao;
	private EntityManager manager;
	private String CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO = "C:\\ARQUIVOS_INTRANET\\TI_HELPDESK\\";
	
	public SolicitacaoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public SolicitacaoLN(EntityManager manager){
		this.manager = manager;
	}
	
	public Solicitacao getById(int id) {
		dao = new GenericDao<Solicitacao>();
		return (Solicitacao) dao.findById(Solicitacao.class,id);
	}

	public Solicitacao getById(int id, EntityManager manager2) {
		dao = new GenericDao<Solicitacao>(manager2);
		return (Solicitacao) dao.findById(Solicitacao.class,id);
	}
	
	public List<Solicitacao> getList() {
		dao = new GenericDao<Solicitacao>();
		return dao.listWithoutRemoved("solicitacao", "id");
	}

	public List<Solicitacao> getListAbertas() {
		SolicitacaoDao sdao = new SolicitacaoDao();
		return sdao.listAbertas();
	}

	public List<Solicitacao> getResolvidas() {
		SolicitacaoDao sdao = new SolicitacaoDao();
		return sdao.listResolvidas();
	}

	public List<Solicitacao> getFechadas() {
		SolicitacaoDao sdao = new SolicitacaoDao();
		return sdao.listFechadas();
	}
	
	public List<Solicitacao> listByUsuario(Login usuario){
		SolicitacaoDao sdao = new SolicitacaoDao();
		return sdao.listByUsuario(usuario);			
	}

	public String create(Solicitacao solicitacao,String nomeArquivo) {
		HistoricoSolicitacao hist = new HistoricoSolicitacao(); //cria um novo histórico
		List<HistoricoSolicitacao> hists = new ArrayList<HistoricoSolicitacao>();
				
		hist.setData(solicitacao.getDataabertura());
		hist.setHora(solicitacao.getHoraabertura());
		hist.setDescricao(solicitacao.getDescricao());
		hist.setStatus(StatusSolicitacao.ABERTA);
		hist.setSolicitacao(solicitacao);	
		hist.setUser_interacao(solicitacao.getSolicitante());
		hist.setArquivo(nomeArquivo);
		hists.add(hist);
		solicitacao.setHistoricos(hists);
		
		dao = new GenericDao<Solicitacao>();
		dao.save(solicitacao);
		return "Castrado com sucesso.";
	}
		
	public String update(Solicitacao solicitacao) {
		dao = new GenericDao<Solicitacao>();
		dao.update(solicitacao);		
		return "Atualizado com sucesso.";
	}

	public String update(Solicitacao solicitacao, EntityManager manager2) {
		dao = new GenericDao<Solicitacao>(manager2);
		dao.update(solicitacao);		
		return "Atualizado com sucesso.";
	}
	
	public String delete(Solicitacao solicitacao) { 
		
		String msg="";
		dao = new GenericDao<Solicitacao>();
		if(solicitacao==null||solicitacao.getId()==null){
			msg="Solicitação não encontrada para deleção.";
			return msg;
		}else{
			HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
			List<HistoricoSolicitacao> hs = hln.findyBySolicitacao(solicitacao);			
			try{
				if(hs.size()<2){
					for(HistoricoSolicitacao h:hs)
						hln.delete(h);
					dao.delete(dao.findById(solicitacao.getClass(), solicitacao.getId()));	
					msg="Removido com sucesso.";
				}else{
					msg="Solicitação não pode ser excluida.";
				}											
			}catch(Exception e){
				System.out.println(e.getLocalizedMessage() + "--> em: SolicitacaoLN.delete(Solicitacao solicitacao) + solicitaçao id= "+solicitacao.getId());			
			}
			return msg;		
		}
	}
		
	public String cancel(Solicitacao solicitacao, HistoricoSolicitacao h1){	//OK
		if(solicitacao!=null){
			HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
			hln.create(h1);	
			//verifica se o solicitante ou favorecido são os mesmos que abriram a solicitação.
			List<HistoricoSolicitacao> hs = hln.findyBySolicitacao(solicitacao);

			if(hs.size()==1){
				/* não houve interação na solicitação */
				if(h1.getUser_interacao().getId()==solicitacao.getSolicitante().getId() || h1.getUser_interacao().getId()==solicitacao.getFavorecido().getId()){
					
					solicitacao.setDatafechamento(h1.getData());
					solicitacao.setHorafechamento(h1.getHora());
					return update(solicitacao);
				}else{
					return "Somente o Solicitante ou Favorecido podem cancelar a solicitação.";
				}		
			}else{
				HistoricoSolicitacao h=hln.lastInteraction(hs);
				if(h.getStatus()==StatusSolicitacao.EM_ATENDIMENTO||h.getStatus()==StatusSolicitacao.EM_ATENDIMENTO_TERCEIRO||h.getStatus()==StatusSolicitacao.EM_TRANSFERENCIA)
					return "Solicitação não pode ser cancelada, pois está em atendimento. Envie um Feedback.";
				else
					return "Solicitação não pode ser cancelada.";
			}			
			
		}else{
			return  "Solicitação não encontrada.";
		}
		
	}
	
	public String resolved(HistoricoSolicitacao h1){//ok
		
		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
		HistoricoSolicitacao ultimoHistorico = hln.lastInteraction(h1.getSolicitacao().getHistoricos());
		if(ultimoHistorico.getStatus()==StatusSolicitacao.CANCELADA){
			return "Solicitação já concluida, não pode receber interações.";
		}else{
			// usuario logado é o mesmo que atendeu por ultimo
			if(h1.getUser_interacao().getId().equals(ultimoHistorico.getAtendente().getLogin().getId())){
				if(ultimoHistorico.getStatus()==StatusSolicitacao.FINALIZADA||ultimoHistorico.getStatus()==StatusSolicitacao.CANCELADA||ultimoHistorico.getStatus()==StatusSolicitacao.EM_TRANSFERENCIA||ultimoHistorico.getStatus()==StatusSolicitacao.RESOLVIDA){
					if(ultimoHistorico.getStatus()==StatusSolicitacao.EM_TRANSFERENCIA){
						return "Solicitação em processo de transferência, deve estar em atenimento para poder ser resolvida.";
					}else{
						if(ultimoHistorico.getStatus()==StatusSolicitacao.RESOLVIDA){
							return "Solicitação já está resolvida.";
						}else{
							return "Solicitação já concluida, não pode receber interações.";
						}
					}
				}else{
					GenericDao<SolicitacaoResolvida> gRdao = new GenericDao<SolicitacaoResolvida>();
					hln.create(h1);
					Solicitacao solicitacao = getById(h1.getSolicitacao().getId());//aualiza solicitação
					SolicitacaoResolvida resolvida = new SolicitacaoResolvida();
					resolvida.setAtendente(h1.getAtendente());
					resolvida.setData(h1.getData());
					resolvida.setHora(h1.getHora());
					//System.out.println("Hora atribuida antes de gravar resolvida: "+resolvida.getHora());
					resolvida.setSolicitacao(h1.getSolicitacao());
					gRdao.save(resolvida);
					enviarEmail(solicitacao,h1.getAtendente());
					return "Solicitação resolvida.";
				}						
			}else{
				return "Somente quem está atendendo a solicitação pode finalizá-la.";
			}
		}		
	}
	
	public String close(Solicitacao solicitacao, Login usuarioLogado, boolean fechamentoAutimatico, EntityManager manager2){//ok
		HistoricoSolicitacaoLN hln;
		SolicitacaoResolvidaDao srdao;
		
		if(fechamentoAutimatico){
			hln = new HistoricoSolicitacaoLN(manager2);
			srdao = new SolicitacaoResolvidaDao(manager2);	
		}else{
			hln = new HistoricoSolicitacaoLN();
			srdao = new SolicitacaoResolvidaDao();	
		}
						
		SolicitacaoResolvida sr = srdao.findBySolicitacao(solicitacao);		
		HistoricoSolicitacao h1 = new HistoricoSolicitacao();
		 
		h1.setStatus(StatusSolicitacao.FINALIZADA);
		h1.setSolicitacao(solicitacao);
		solicitacao.setDatafechamento(new Date());
		solicitacao.setHorafechamento(new Date());
		h1.setData(new Date());
		h1.setHora(new Date());
		
		if(sr==null){
			return "A solicitação não pode ser finalizada. Status da solicitação é "+hln.lastInteraction(solicitacao.getHistoricos()).getStatus();
		}else{
			if(fechamentoAutimatico){
				h1.setDescricao("Solicitação finalizada automaticamente pelo sistema");
				hln.create(h1,manager2);
				update(solicitacao,manager2);
				srdao.excluiSolicitacaoResolvida(sr);
				/*enviarEmail(getById(solicitacao.getId(),manager2),manager2);*/
				return	"Solicitação finalizada.";
			}else{
				if(usuarioLogado.getId().equals(solicitacao.getSolicitante().getId())||usuarioLogado.getId().equals(solicitacao.getFavorecido().getId())){
					h1.setDescricao("Solicitação finalizada pelo usuário "+usuarioLogado.getNome());
					hln.create(h1);
					update(solicitacao);
					srdao.excluiSolicitacaoResolvida(sr);
					enviarEmail(getById(solicitacao.getId()));
					return	"Solicitação finalizada.";
				}else{
					return	"Solicitação somente pode ser finalizada pelo Solicitante ou Favorecido.";
				}
			}
		}
	}
	
	public void enviarEmail(Solicitacao solicitacao, Atendente atendente){
		String corpo = montaCorpodoEmail(solicitacao);
		String assunto = "Solicitação nro. "+solicitacao.getId();
		List<String> dests = new ArrayList<String>();
		
		if(atendente!=null)
			if(ValidaEmail.validar(atendente.getLogin().getEmail()))
				dests.add(atendente.getLogin().getEmail());
		
		if(solicitacao.getSolicitante().getId()!=solicitacao.getFavorecido().getId()){
			if(ValidaEmail.validar(solicitacao.getFavorecido().getEmail()))
				dests.add(solicitacao.getFavorecido().getEmail());
			if(ValidaEmail.validar(solicitacao.getSolicitante().getEmail()))
				dests.add(solicitacao.getSolicitante().getEmail());
		}else{
			if(ValidaEmail.validar(solicitacao.getSolicitante().getEmail()))
				dests.add(solicitacao.getSolicitante().getEmail());
		}
		
		if(solicitacao.getEmailsadicionais()!=null){
			String[] adicionais = solicitacao.getEmailsadicionais().split(";");
			for(String s:adicionais)
				dests.add(s);
		}
		if(dests.size()>0){
			Email email = new Email("INTRANET - HELP DESK", dests, assunto, corpo, new ArrayList<File>());
		email.start();
		}
	}

	public void enviarEmailAlteracao(Solicitacao solicitacao,Atendente atendente){
		String corpo = montaCorpodoEmailAlteracao(solicitacao);
		String assunto = "Alteração da Solicitação nro. "+solicitacao.getId();
		List<String> dests = new ArrayList<String>();

		if(atendente!=null)
			if(ValidaEmail.validar(atendente.getLogin().getEmail()))
				dests.add(atendente.getLogin().getEmail());
		
		if(solicitacao.getSolicitante().getId()!=solicitacao.getFavorecido().getId()){
			if(ValidaEmail.validar(solicitacao.getFavorecido().getEmail()))
				dests.add(solicitacao.getFavorecido().getEmail());
			if(ValidaEmail.validar(solicitacao.getSolicitante().getEmail()))
				dests.add(solicitacao.getSolicitante().getEmail());
		}else{
			if(ValidaEmail.validar(solicitacao.getSolicitante().getEmail()))
				dests.add(solicitacao.getSolicitante().getEmail());
		}
		if(solicitacao.getEmailsadicionais().length()>0){
			String[] adicionais = solicitacao.getEmailsadicionais().split(";");
			for(String s:adicionais)
				dests.add(s);
		}		
		if(dests.size()>0){
			Email email = new Email("INTRANET - HELP DESK", dests, assunto, corpo, new ArrayList<File>());
			email.start();
		}
	}

	public void enviarEmail(Solicitacao solicitacao){
		String corpo = montaCorpodoEmail(solicitacao);
		String assunto = "Solicitação nro. "+solicitacao.getId();
		List<String> dests = new ArrayList<String>();
		
		dests.add("afigueredo@recordnews.com.br");
		dests.add("cmarques@recordnews.com.br");
		dests.add("jfranca@recordnews.com.br");
		dests.add("maucruz@news.rederecord.com.br");
		
		if(solicitacao.getSolicitante().getId()!=solicitacao.getFavorecido().getId()){
			if(ValidaEmail.validar(solicitacao.getFavorecido().getEmail()))
				dests.add(solicitacao.getFavorecido().getEmail());
			if(ValidaEmail.validar(solicitacao.getSolicitante().getEmail()))
				dests.add(solicitacao.getSolicitante().getEmail());
		}else{
			if(ValidaEmail.validar(solicitacao.getSolicitante().getEmail()))
				dests.add(solicitacao.getSolicitante().getEmail());
		}
		if(solicitacao.getEmailsadicionais().length()>0){
			String[] adicionais = solicitacao.getEmailsadicionais().split(";");
			for(String s:adicionais)
				dests.add(s);
		}		
		if(dests.size()>0){
			Email email = new Email("INTRANET - HELP DESK", dests, assunto, corpo, new ArrayList<File>());
			email.start();
		}
	}

	public void enviarEmail(Solicitacao solicitacao, EntityManager manager2){
		String corpo = montaCorpodoEmail(solicitacao,manager2);
		String assunto = "Solicitação nro. "+solicitacao.getId();
		List<String> dests = new ArrayList<String>();	
		
		if(solicitacao.getSolicitante().getId()!=solicitacao.getFavorecido().getId()){
			if(ValidaEmail.validar(solicitacao.getFavorecido().getEmail()))
				dests.add(solicitacao.getFavorecido().getEmail());
			if(ValidaEmail.validar(solicitacao.getSolicitante().getEmail()))
				dests.add(solicitacao.getSolicitante().getEmail());
		}else{
			if(ValidaEmail.validar(solicitacao.getSolicitante().getEmail()))
				dests.add(solicitacao.getSolicitante().getEmail());
		}
		if(!solicitacao.getEmailsadicionais().equals("")||solicitacao.getEmailsadicionais()!=null){
			String[] adicionais = solicitacao.getEmailsadicionais().split(";");
			for(String s:adicionais)
				dests.add(s);
		}		
		if(dests.size()>0){
			Email email = new Email("INTRANET - HELP DESK", dests, assunto, corpo, new ArrayList<File>());
			email.start();
		}
	}
	
	private String montaCorpodoEmail(Solicitacao solicitacao){
		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
		HistoricoSolicitacao h = hln.lastInteraction(getById(solicitacao.getId()).getHistoricos());

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Sistema de Solicita&ccedil;&atilde;o - TI </span>");
		sb.append("</h1>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt';>Este email foi enviado automaticamente do Sistema de Solicitações TI.</span></p>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt;'>Abaixo segue detalhes da solicita&ccedil;&atilde;o :</span></p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Solicita&ccedil;&atilde;o no.:</strong> "+ solicitacao.getId() +"</p>");
		String status = buscaStatus(h);  
		if(!status.equals("Aberta"));
			sb.append("<p><strong>Solicita&ccedil;&atilde;o:</strong> "+ solicitacao.getDescricao() +"</p>");
		sb.append("<p><strong>Status:</strong> "+ status +"</p>");
		sb.append("<p><strong>Descri&ccedil;&atilde;o:</strong> "+ h.getDescricao() +"</p>");
		sb.append("<p><strong>Solicitante:</strong> "+ h.getSolicitacao().getSolicitante().getNome() +"</p>");
		sb.append("<p><strong>Favorecido:</strong> "+ h.getSolicitacao().getFavorecido().getNome() +"</p>");
		if(h.getAtendente()!=null)
			sb.append("<p><strong>Atendente:</strong> "+ h.getAtendente().getLogin().getNome() +"</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>&nbsp;</p>");
	
		sb.append("<p>Favor, n&atilde;o responder.</p><p>Atenciosamente.</p>");		
		sb.append("</body>");		
		sb.append("</html>");	
		return sb.toString();		
	}

	private String montaCorpodoEmail(Solicitacao solicitacao, EntityManager manager2){
		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN(manager2);
		HistoricoSolicitacao h = hln.lastInteraction(getById(solicitacao.getId(),manager2).getHistoricos());

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Sistema de Solicita&ccedil;&atilde;o - TI </span>");
		sb.append("</h1>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt';>Este email foi enviado automaticamente do Sistema de Solicitações TI.</span></p>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt;'>Abaixo segue detalhes da solicita&ccedil;&atilde;o :</span></p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Solicita&ccedil;&atilde;o no.:</strong> "+ solicitacao.getId() +"</p>");
		String status = buscaStatus(h);  
		if(!status.equals("Aberta"));
			sb.append("<p><strong>Solicita&ccedil;&atilde;o:</strong> "+ solicitacao.getDescricao() +"</p>");
		sb.append("<p><strong>Status:</strong> "+ status +"</p>");
		sb.append("<p><strong>Descri&ccedil;&atilde;o:</strong> "+ h.getDescricao() +"</p>");
		sb.append("<p><strong>Solicitante:</strong> "+ h.getSolicitacao().getSolicitante().getNome() +"</p>");
		sb.append("<p><strong>Favorecido:</strong> "+ h.getSolicitacao().getFavorecido().getNome() +"</p>");
		if(h.getAtendente()!=null)
			sb.append("<p><strong>Atendente:</strong> "+ h.getAtendente().getLogin().getNome() +"</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>&nbsp;</p>");
	
		sb.append("<p>Favor, n&atilde;o responder.</p><p>Atenciosamente.</p>");		
		sb.append("</body>");		
		sb.append("</html>");	
		return sb.toString();		
	}

	private String montaCorpodoEmailAlteracao(Solicitacao solicitacao){
		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
		HistoricoSolicitacao h = hln.lastInteraction(getById(solicitacao.getId()).getHistoricos());

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Sistema de Solicita&ccedil;&atilde;o - TI </span>");
		sb.append("</h1>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt';>Este email foi enviado automaticamente do Sistema de Solicitações TI.</span></p>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt;'>Abaixo segue detalhes da altera&ccedil;&atilde;o :</span></p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Solicita&ccedil;&atilde;o no.:</strong> "+ solicitacao.getId() +"</p>");
		String status = buscaStatus(h);  
		if(!status.equals("Aberta"));
			sb.append("<p><strong>Solicita&ccedil;&atilde;o:</strong> "+ solicitacao.getDescricao() +"</p>");
		sb.append("<p><strong>Status:</strong> "+ status +"</p>");
		sb.append("<p><strong>Descri&ccedil;&atilde;o:</strong> "+ h.getDescricao() +"</p>");
		sb.append("<p><strong>Altera&ccedil;&atilde;o na classifica&ccedil;&atilde;o:</strong> </p>");
		sb.append("<p><strong>Processo:</strong> "+ h.getSolicitacao().getCategoria() +"</p>");
		sb.append("<p><strong>Categoria:</strong> "+ h.getSolicitacao().getArea().getDescricao() +"</p>");
		if(h.getAtendente()!=null)
			sb.append("<p><strong>Atendente:</strong> "+ h.getAtendente().getLogin().getNome() +"</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p>&nbsp;</p>");
	
		sb.append("<p>Favor, n&atilde;o responder.</p><p>Atenciosamente.</p>");		
		sb.append("</body>");		
		sb.append("</html>");	
		return sb.toString();		
	}
	
	public String firstInteraction(Solicitacao solicitacao,Date data,Date hora){
		return null;		
	}	
	
	public Atendente getAtendente(Login l){
		AtendenteDao adao = new AtendenteDao();
		return adao.findyByLogin(l);
	}
	
	public String recebeArquivoUpload(InputStream is, String nome){
		
        FileOutputStream os = null;
        String erro="";
        try {
        	criarPasta(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO); 
            File file = new File(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO + nome);        	
            os = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
        	erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: SolicitacaoLN.recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: SolicitacaoLN.recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: SolicitacaoLN.recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}
 
	private void criarPasta(String caminho) {
        File folder = new File(caminho);
        if (!folder.exists())
            folder.mkdirs();	
	}	
	
	public InputStream enviaArquivoDownload(String nomeArquivo){
		try {
			return new FileInputStream(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO +nomeArquivo);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
 	public String buscaStatus(HistoricoSolicitacao h){
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
		 	    	staus="Trasferindo de Atendente";
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

 	public String buscaStatus(List<HistoricoSolicitacao> hs){
 		String staus="";
 		if(hs!=null){
	 		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
	 		HistoricoSolicitacao h = hln.lastInteraction(hs);
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
	
	public List<Integer> atualizaEstatistica() {
		List<Integer> estatisticas = new ArrayList<Integer>();
		SolicitacaoDao sdao = new SolicitacaoDao();
		List<Solicitacao> list = sdao.listAbertasEResolvidas();
		Integer quantidadeAbertoMatriz=0;
		Integer quantidadeAbertoAraraquara=0;
		Integer quantidadePausadoMatriz=0;
		Integer quantidadePausadoAraraquara=0;
		Integer quantidadeEmTransferenciaMatriz=0;
		Integer quantidadeEmTransferenciaAraraquara=0;	
		Integer quantidadeEmAndamentoMatriz=0;
		Integer quantidadeEmAndamentoAraraquara=0;	
		Integer quantidadeResolvidaMatriz=0;
		Integer quantidadeResolvidaAraraquara=0;	
		
 		HistoricoSolicitacaoLN hln = new HistoricoSolicitacaoLN();
		for(Solicitacao s:list){
			HistoricoSolicitacao h = hln.lastInteraction(s.getHistoricos());	
			
			if(h.getStatus().equals(StatusSolicitacao.ABERTA)||h.getStatus().equals(StatusSolicitacao.FEEDBACK)){
				if(s.getFavorecido().getFilial().getId()==1)
					quantidadeAbertoMatriz++;			
				if(s.getFavorecido().getFilial().getId()==2)
					quantidadeAbertoAraraquara++;
			}
			
			if(h.getStatus().equals(StatusSolicitacao.PAUSADA)){
				if(s.getFavorecido().getFilial().getId()==1)
					quantidadePausadoMatriz++;				
				if(s.getFavorecido().getFilial().getId()==2)
					quantidadePausadoAraraquara++;					
			}	

			if(h.getStatus().equals(StatusSolicitacao.EM_TRANSFERENCIA)){
				if(s.getFavorecido().getFilial().getId()==1)
					quantidadeEmTransferenciaMatriz++;				
				if(s.getFavorecido().getFilial().getId()==2)
					quantidadeEmTransferenciaAraraquara++;					
			}	

			if(h.getStatus().equals(StatusSolicitacao.EM_ATENDIMENTO)||h.getStatus().equals(StatusSolicitacao.EM_ATENDIMENTO_TERCEIRO)){
				if(s.getFavorecido().getFilial().getId()==1)
					quantidadeEmAndamentoMatriz++;				
				if(s.getFavorecido().getFilial().getId()==2)
					quantidadeEmAndamentoAraraquara++;					
			}	

			if(h.getStatus().equals(StatusSolicitacao.RESOLVIDA)){
				if(s.getFavorecido().getFilial().getId()==1)
					quantidadeResolvidaMatriz++;				
				if(s.getFavorecido().getFilial().getId()==2)
					quantidadeResolvidaAraraquara++;					
			}			
			
		}
		estatisticas.add(quantidadeAbertoMatriz);
		estatisticas.add(quantidadeAbertoAraraquara);
		estatisticas.add(quantidadePausadoMatriz);
		estatisticas.add(quantidadePausadoAraraquara);
		estatisticas.add(quantidadeEmTransferenciaMatriz);
		estatisticas.add(quantidadeEmTransferenciaAraraquara);
		estatisticas.add(quantidadeEmAndamentoMatriz);		
		estatisticas.add(quantidadeEmAndamentoAraraquara);
		estatisticas.add(quantidadeResolvidaMatriz);
		estatisticas.add(quantidadeResolvidaAraraquara);
		return estatisticas;		
	}
		
	public GenericDao<Solicitacao> getDao() {
		return dao;
	}

	public void setDao(GenericDao<Solicitacao> dao) {
		this.dao = dao;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
