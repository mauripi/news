package br.com.mauricio.news.agendamento;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.mauricio.news.dao.ContratoDao;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.util.DateUtil;
import br.com.mauricio.news.util.FormataNumero;
import br.com.mauricio.news.util.SendMail;


public class PrimeiroAvisoContratoJob  implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {

		List<Contrato> list = getList();
		
		if(list.size()==0){
			System.out.println("Não há emails a serem enviados.");
		}else{	
			for(Contrato c:list){
		        HtmlEmail email = new HtmlEmail();  		
		        String cid;
				try {
					cid = email.embed(new File("c://tmp//logo.png"));
			        email.setHtmlMsg(emailContratoPrimeiroAviso(cid,c));   
			        email.addTo("maucruz@recordnews.com.br", "Mumu");         
			        email.setSubject("Contratos"); // assunto do e-mail  		
			        SendMail.sendHtml(email);
				} catch (EmailException e) {
					System.out.println(e.getLocalizedMessage());
					e.printStackTrace();
				}    
			}
		}
        System.out.println("Finalizando processo automático para envio email contrato...");
 
	}
	
	private static List<Contrato> getList(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		List<Contrato> list = new ArrayList<Contrato>();
		ContratoDao dao = new ContratoDao(manager);
		list = dao.listPrimeiroAviso();
		
        manager.getTransaction().commit();
        manager.close();
        factory.close();
        return list;
   }

	private static String emailContratoPrimeiroAviso(String cidLogo, Contrato c){
	    
		String linhaEmBranco = "<tr><td>&nbsp;</td><td colspan='10'></td><td>&nbsp;</td></tr>";
		String linhaComHr = "<tr><td>&nbsp;</td><td colspan='10'><hr></td><td>&nbsp;</td></tr>";
		StringBuilder sb = new StringBuilder();		

		sb.append("<html><head><META http-equiv=Content-Type content='text/html; charset=utf-8'><META content='MSHTML 6.00.2900.3020' name=GENERATOR></head><body>");
		sb.append("<div style='width: 800px; text-align: center;'>");
		sb.append("<table width='100%' cellspacing='2' cellpadding='0' border='0' align='center' bgcolor='#eff0f1'><tbody>");
		sb.append(linhaEmBranco);
		sb.append("<tr><td>&nbsp;</td><td colspan='3' rowspan='2'><img src=\"cid:"+cidLogo+"\"></td>");
		sb.append("<td colspan='7' rowspan='2' align='center' style='font-size: 46px;font-family: sans-serif,Open Sans,Arial;'><strong><b><i>Notificação de Contrato</i></b></strong></td><td>&nbsp;</td></tr>");		
		sb.append(linhaEmBranco);
		sb.append(linhaComHr);		
		sb.append("<tr><td>&nbsp;</td><td colspan='10' align='center' style='font-size: 26px;font-family: sans-serif,Open Sans,Arial;'><i>"+c.getObjeto()+"</i></td><td>&nbsp;</td></tr>");		
		sb.append(linhaEmBranco);				
		sb.append("<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>");
		sb.append("<td colspan='6' align='right' style='font-size: 18px;font-family: 'Open Sans',Arial,sans-serif;'><b><i> Contrato de "+c.getTipocontrato().getDescricao()+" n° "+c.getId()+"</i></b></td><td>&nbsp;</td></tr>");
		sb.append(linhaEmBranco);
		sb.append(linhaEmBranco);		
		sb.append("<tr><td width='1%'>&nbsp;</td><td width='1%'>&nbsp;</td>");
		sb.append("<td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'><span><b>Razão:</b></span>  "+c.getMclifor().getNomfan()+"</td>");
		sb.append("<td width='6%'>&nbsp;</td><td width='0%'>&nbsp;</td><td colspan='3'>&nbsp;</td><td width='8%'>&nbsp;</td><td width='1%'>&nbsp;</td></tr>");		
		sb.append("<tr><td width='1%'>&nbsp;</td><td width='1%'>&nbsp;</td>");
		sb.append("<td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'><span><b>Nome do Contato:</b></span>  "+c.getMclifor().getNomcon()+"</td>");
		sb.append("<td width='6%'>&nbsp;</td><td width='0%'>&nbsp;</td>");
		sb.append("<td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'><span><b>Telefone do Contato:</b></span>  "+c.getMclifor().getFoncon()+"</td>");
		sb.append("<td width='8%'>&nbsp;</td><td width='1%'>&nbsp;</td></tr>");
		sb.append("<tr><td width='1%'>&nbsp;</td><td width='1%'>&nbsp;</td>");
		sb.append("<td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'><span><b>E-mail do Contato:</b></span>  "+c.getMclifor().getEmacon()+"</td>");
		sb.append("<td width='6%'>&nbsp;</td><td width='0%'>&nbsp;</td><td colspan='3'>&nbsp;</td><td width='8%'>&nbsp;</td><td width='1%'>&nbsp;</td></tr>");
		sb.append(linhaEmBranco);
		sb.append("<tr><td width='1%'>&nbsp;</td><td width='1%'>&nbsp;</td>");
		sb.append("<td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'><span><b>Responsável do Contrato:</b></span>  "+c.getResponsavel()+"</td>");
		sb.append("<td width='6%'>&nbsp;</td><td width='0%'>&nbsp;</td><td colspan='3'>&nbsp;</td><td width='8%'>&nbsp;</td><td width='1%'>&nbsp;</td></tr>");		
		sb.append("<tr><td width='1%'>&nbsp;</td><td width='1%'>&nbsp;</td><td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'>");
		sb.append("<span><b>Vigência do Contrato:  </b></span> "+DateUtil.formatDataFromBanco(c.getInicio())+" a "+DateUtil.formatDataFromBanco(c.getFim())+"</td>");
		sb.append("<td width='6%'>&nbsp;</td><td width='0%'>&nbsp;</td><td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'>");
		sb.append("<span><b>Dias para fim do contrato:  </b></span> "+DateUtil.diferencaEmDias(new Date(),c.getFim())+" dias</td>");
		sb.append("<td width='8%'>&nbsp;</td><td width='1%'>&nbsp;</td></tr>");		
		sb.append("<tr><td width='1%'>&nbsp;</td><td width='1%'>&nbsp;</td>");
		sb.append("<td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'><span><b>Valor mensal:</b></span> R$ "+FormataNumero.doubleTOMoedaReal(c.getValorMensal())+"</td>");
		sb.append("<td width='6%'>&nbsp;</td><td width='0%'>&nbsp;</td><td colspan='3'>&nbsp;</td><td width='8%'>&nbsp;</td><td width='1%'>&nbsp;</td></tr>");
		sb.append("<tr><td width='1%'>&nbsp;</td><td width='1%'>&nbsp;</td>");
		sb.append("<td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'><span><b>Valor Total:</b></span> R$ "+FormataNumero.doubleTOMoedaReal(c.getValorTotal())+"</td>");
		sb.append("<td width='6%'>&nbsp;</td><td width='0%'>&nbsp;</td><td colspan='3'>&nbsp;</td><td width='8%'>&nbsp;</td><td width='1%'>&nbsp;</td></tr>");
		sb.append(linhaEmBranco);		  
		sb.append(linhaComHr);
		sb.append(linhaEmBranco);		
		sb.append("<tr><td width='1%'>&nbsp;</td><td width='1%'>&nbsp;</td>");
		sb.append("<td colspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'><span><b>Observação:</b></span></td>");
		sb.append("<td width='6%'>&nbsp;</td><td width='0%'>&nbsp;</td><td colspan='3'>&nbsp;</td><td width='8%'>&nbsp;</td><td width='1%'>&nbsp;</td></tr>");				
		sb.append("<tr><td>&nbsp;</td><td>&nbsp;</td><td colspan='9' rowspan='3' align='left' style='font-size: 16px;font-family: 'Open Sans',Arial,sans-serif;'>");
		sb.append(c.getObservacao());	
		sb.append("</td><td>&nbsp;</td></tr>");	
		sb.append(linhaEmBranco);
		sb.append(linhaEmBranco);
		sb.append(linhaEmBranco);
		sb.append("</tbody></table></div></body></html>");
		return sb.toString();		
	}
	


}
