package br.com.mauricio.news.agendamento;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.mauricio.news.dao.ti.SolicitacaoResolvidaDao;
import br.com.mauricio.news.ln.ti.SolicitacaoLN;
import br.com.mauricio.news.model.ti.Solicitacao;
import br.com.mauricio.news.model.ti.SolicitacaoResolvida;


public class FinalizarSolicitacaoResolvidaJob  implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LogManager.getLogManager().reset();
		System.out.println(new DateTime() + " - Iniciando processo automático para finalizar solicitações....");
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		List<SolicitacaoResolvida> list = new ArrayList<SolicitacaoResolvida>();
		SolicitacaoResolvidaDao dao = new SolicitacaoResolvidaDao(manager);
		list = dao.findResolvidas();
		List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		int i=0;

		for(SolicitacaoResolvida s:list){
			DateTime diasAntes = new DateTime().minusDays(30);  //seta quantos dias antes
			DateTime agora = new DateTime();  
			DateTime data = new DateTime(s.getData());
			DateTimeZone zoneSaoPaulo = DateTimeZone.forID( "America/Sao_Paulo" );
			Interval intervalo = new Interval(diasAntes, agora);
			
			@SuppressWarnings("deprecation")
			DateTime dataHoraSolicitacao = new DateTime(data.getYear(),data.getMonthOfYear(),data.getDayOfMonth(),s.getHora().getHours(),s.getHora().getMinutes(),s.getHora().getSeconds(),zoneSaoPaulo);  
		
			//Duration dur = new Duration(dataHoraSolicitacao, agora);

			if (intervalo.contains(dataHoraSolicitacao))	{
				solicitacoes.add(s.getSolicitacao());
				SolicitacaoLN ln = new SolicitacaoLN(manager);
				ln.close(s.getSolicitacao(), null, true,manager);
				i++;
			}
		}

        manager.getTransaction().commit();
        manager.close();
        factory.close();
        System.out.println("Finalizando processo automático, "+ i +" solicitações finalizadas.");
     
	}
}
