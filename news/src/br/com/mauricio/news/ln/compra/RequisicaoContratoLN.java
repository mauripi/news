package br.com.mauricio.news.ln.compra;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.mauricio.news.dao.compra.RequisicaoContratoDao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.compra.RequisicaoContrato;
import br.com.mauricio.news.util.Email;
import br.com.mauricio.news.util.ValidaEmail;

public class RequisicaoContratoLN implements Serializable{
	private static final long serialVersionUID = 1L;

	public List<RequisicaoContrato> listaPorUsuario(Login usuario){
		RequisicaoContratoDao dao = new RequisicaoContratoDao();
		return dao.listaPorUsuario(usuario);
	}
	
	public void enviarEmail(RequisicaoContrato requisicao){
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add("abrito@recordnews.com.br");
		destinatarios.add("mnunes@recordnews.com.br");
		destinatarios.add("glesilva@recordnews.com.br");
		if(ValidaEmail.validar(requisicao.getUsuario().getEmail()))
			destinatarios.add(requisicao.getUsuario().getEmail());
		
		File file = new File("C:\\windows\\temp\\compra\\"+"requisicaocontrato"+requisicao.getId()+".pdf");
		List<File> anexos = new ArrayList<File>();
		anexos.add(file);			
		
		Email email = new Email("Intranet Record News", destinatarios, "Requisição de Contrato", montaCorpodoEmail(requisicao),anexos);
		email.start();
	}
	
	private String montaCorpodoEmail(RequisicaoContrato requisicao){
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Controle de Contratos </span>");
		sb.append("</h1>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt';>Este email foi enviado automaticamente do TI Sistemas.</span></p>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt;'>Abaixo segue detalhes da nova solicita&ccedil;&atilde;o de contrato em anexo :</span></p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Solicitante:</strong> "+ requisicao.getUsuario().getNome() +"</p>");
		sb.append("<p><strong>Requisi&ccedil;&atilde;o :</strong> "+ requisicao.getId() +"</p>");
		sb.append("<p>&nbsp;</p>");	
		sb.append("<p>Favor, n&atilde;o responder.</p><p>Atenciosamente.</p>");		
		sb.append("</body>");		
		sb.append("</html>");	
		return sb.toString();		
	}
}
