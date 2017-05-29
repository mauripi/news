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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.mauricio.news.dao.ContratoDao;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.util.DateUtil;
import br.com.mauricio.news.util.EmailUtil;
import br.com.mauricio.news.util.FormataNumero;
import br.com.mauricio.news.util.SendMail;


public class ProximosAvisoContratoJob  implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(new DateTime() +" => Iniciando processo automático para envio email contrato Proximos Aviso..."); 
		String CAMINHO_PASTA_CONTRATOS = "C:\\ARQUIVOS_CONTRATOS\\";
		List<Contrato> list = getList();
		if(list.size()==0){
			System.out.println("Não há emails a serem enviados.");
		}else{	
			System.out.println(list.size() + " emails a serem enviados.");
			for(Contrato c:list){
		        HtmlEmail email = new HtmlEmail();  		
		        String cid;
				try {
					cid = email.embed(new File(CAMINHO_PASTA_CONTRATOS+"img//img-not-logo.png"));
			        email.setHtmlMsg(emailAviso(cid,c));   
			        List<String> listTo = EmailUtil.stringToListSeparadoPorVirgula(c.getEmailsAviso());
			        for(int i=0;i<listTo.size();i++)
			        	email.addTo(listTo.get(i));  
			        email.setSubject("Aviso de término de contrato"); 		
			        SendMail.sendHtml(email);
				} catch (EmailException e) {
					System.out.println(e.getLocalizedMessage());
					e.printStackTrace();
				}    
			}
		}
        System.out.println(new DateTime() +" => Finalizando processo automático para envio email contrato Proximos Aviso..."); 
	}
	
	private static List<Contrato> getList(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
		EntityManager manager = factory.createEntityManager();

		List<Contrato> list = new ArrayList<Contrato>();
		List<Contrato> listAviso = new ArrayList<Contrato>();
		ContratoDao dao = new ContratoDao(manager);
		list = dao.listaContratosAtivos();
        manager.close();
        factory.close();		
		for(Contrato c : list){
			if(c.getDiasAviso()!=null){
				if(c.getRepetirAviso()!=null){
					LocalDate data = new LocalDate(c.getFim()).minusDays(c.getDiasAviso()).plusDays(c.getRepetirAviso());
					if(data.compareTo(new LocalDate())==0) listAviso.add(c);					
				}
			}
		}
		
        return listAviso;
   }


	
	private static String emailAviso(String cidLogo, Contrato c){
	    
		String style1 = "style='font-weight: bold; text-transform: uppercase; color:#012257; font-size: 14px;'";
		String style2 = "style='padding-left: 30px; line-height: 22px;'";
		String style3 = "style='font-weight: bold; text-transform: uppercase; color:#17609a'";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<html><head><META http-equiv=Content-Type content='text/html; charset=utf-8'></head><body>");
		sb.append("<table width='550' border='0' align='center' cellpadding='0' cellspacing='0' style='font-family: Calibri, Gotham, 'Helvetica Neue', Helvetica, Arial, 'sans-serif';width: 550px;'>");
		sb.append("<tr><td bgcolor='#0c183c'><img style='width: 550px; height: 153px;' src=\"cid:"+cidLogo+"\"/></td></tr>");
		sb.append("<tr><td height='50' bgcolor='#cce1ef'><table width='500' border='0' cellpadding='0' cellspacing='0' style=' width: 500px;'>");

		sb.append("<tr ><td style='padding-left: 30px; font-weight: bold; font-size: 20px; color:#0c183c'>"+c.getObjeto()+"</td></tr>");
		sb.append("<tr><td style='padding-left: 30px; font-weight: bold; font-size: 14px; color:#17609a; text-transform: uppercase;' >Contrato de "+c.getTipocontrato().getDescricao()+" n° "+c.getId()+"</td>");

		sb.append("</tr></table></td></tr><tr><td valign='middle' style='border: 1px solid #cbcbcb; border-top-width: 0px;'>");
		sb.append("<table width='500' border='0' cellpadding='0' cellspacing='0' style='width: 500px; font-size: 16px;'>");
		sb.append("<tr><td height='20'>&nbsp;</td></tr><tr><td "+style2+">");

		sb.append("<span "+style3+">INFORMAÇÕES</span><br>");
		sb.append("<span "+style1+">Empresa:</span>    "+ c.getMclifor().getNomfan()+"<br>");
		sb.append("<span "+style1+">Contato:</span>    "+ c.getMclifor().getNomcon()+"<br>");
		sb.append("<span "+style1+">Telefone:</span>    "+ c.getMclifor().getFoncon()+"<br>");
		sb.append("<span "+style1+">E-mail:</span>    "+ c.getMclifor().getEmacon()+"<br>");		
		sb.append("<span "+style1+">Responsável do Contrato:</span>    "+ c.getResponsavel()+"</td>");
		
		sb.append("</tr><tr><td height='20'>&nbsp;</td></tr><tr>");
		sb.append("<td "+style2+"><span "+style3+">DADOS DO CONTRATO</span><br>");		
		sb.append("<span "+style1+">Vigência:</span>    "+ DateUtil.formatDataFromBanco(c.getInicio())+ " a "+DateUtil.formatDataFromBanco(c.getFim())+"<br>");		
		sb.append("<span "+style1+">Dias para TÉRMINO:</span>   "+DateUtil.diferencaEmDias(new Date(),c.getFim())+"<br>");
		sb.append("<span "+style1+">Valor mensal:</span> R$   "+FormataNumero.doubleTOMoedaReal(c.getValorMensal())+"<br>");
		sb.append("<span "+style1+">Valor Total:</span>    "+ FormataNumero.doubleTOMoedaReal(c.getValorTotal())+"</td>  </tr>");		

		sb.append("<tr><td height='20'>&nbsp;</td></tr>");
		sb.append("<tr><td "+style2+"><span "+style3+">Observação:</span><br>");		
		sb.append("<span>"+c.getObservacao()+"</span></td></tr>");
		sb.append("<tr><td height='20'>&nbsp;</td></tr><tr><td></td></tr></table></td></tr></table></body></html>");
		
		
		return sb.toString();		
	}
}
