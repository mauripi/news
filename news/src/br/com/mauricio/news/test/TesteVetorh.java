package br.com.mauricio.news.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.dao.ContratoDao;
import br.com.mauricio.news.model.Contrato;

public class TesteVetorh {

	public static void main(String[] args) {
		System.out.println("");
		System.out.println("::::: INICIO ::::");
		System.out.println("");
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
		EntityManager manager = factory.createEntityManager();

		List<Contrato> listAll = new ArrayList<Contrato>();
		List<Contrato> listVlrZerados = new ArrayList<Contrato>();
		List<Contrato> listVlrZeradosReceita = new ArrayList<Contrato>();
		List<Contrato> listVlrZeradosDespesa = new ArrayList<Contrato>();
		List<Contrato> listComVlr = new ArrayList<Contrato>();
		List<Contrato> listComVlrReceita = new ArrayList<Contrato>();
		List<Contrato> listComVlrDespesa = new ArrayList<Contrato>();		
		
		ContratoDao dao = new ContratoDao(manager);
		listAll = dao.listaContratosAtivos(true);
        manager.close();
        factory.close();		
       
		for(Contrato c : listAll){
			if(c.getValorMensal()==0 || c.getValorTotal()==0){
				if (c.getOrigem().equals("V")) listVlrZeradosReceita.add(c);
				else listVlrZeradosDespesa.add(c);
				listVlrZerados.add(c);
			}else{
				if (c.getOrigem().equals("V")) listComVlrReceita.add(c);
				else listComVlrDespesa.add(c);
				listComVlr.add(c);
			}
		}
		Map<Integer,Contrato> contratos = new HashMap<>();
		listAll.forEach(c -> contratos.put(c.getId(), c));

		
		//listAll.forEach(System.out::println);
		System.out.println("");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Resultado filtro");
		System.out.println("");
		String consulta = "marco".toLowerCase();
		
		List<Contrato> filtrados = listAll.stream()
        .filter(c -> c.toString().toLowerCase().contains(consulta))
        .collect(Collectors.toList());
/*
		Map<Integer,Contrato> result = contratos.entrySet().stream()
                .filter(map -> map.getValue().toString().toLowerCase().contains(consulta))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue)); */
		filtrados.forEach(System.out::println);
		//System.out.println(result);
		System.out.println(":::: FIM ::::");
		System.out.println("");
		/*
		System.out.println("Total de Contratos Ativos: " + listAll.size());
		System.out.println("Total de Contratos de Receita: " + (listComVlrReceita.size()  +  listVlrZeradosReceita.size()));
		System.out.println("Total de Contratos de Receita sem Valores: " + listVlrZeradosReceita.size());
		System.out.println("Total de Contratos de Receita com Valores: " + listComVlrReceita.size());		
		System.out.println("Total de Contratos de Despesa: " + (listComVlrDespesa.size()  +  listVlrZeradosDespesa.size()));
		System.out.println("Total de Contratos de Despesa sem Valores: " + listVlrZeradosDespesa.size());
		System.out.println("Total de Contratos de Despesa com Valores: " + listComVlrDespesa.size());
		System.out.println("");
		System.out.println("");*/
	}

}
