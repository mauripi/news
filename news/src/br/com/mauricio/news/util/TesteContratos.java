package br.com.mauricio.news.util;

import org.quartz.JobExecutionException;

import br.com.mauricio.news.agendamento.PrimeiroAvisoContratoJob;

public class TesteContratos {

	public static void main(String[] args) throws JobExecutionException {
		PrimeiroAvisoContratoJob job = new PrimeiroAvisoContratoJob();
		job.execute(null);
	}

}
