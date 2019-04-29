package br.com.mauricio.news.dao.ti;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.ti.ConsumoEmbratel;
import br.com.mauricio.news.model.ti.ConsumoEmbratelDesconto;
import br.com.mauricio.news.model.ti.RateioFinalEmbratel;

public class ConsumoEmbratelDao implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;

	public ConsumoEmbratelDao(){}
	public ConsumoEmbratelDao(EntityManager manager){
		this.manager = manager;
	}
	
	public List<ConsumoEmbratel> carregaArquivo(String arquivo,String fatura){
		String linha="";
		List<ConsumoEmbratel> consumos = new ArrayList<ConsumoEmbratel>();
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(arquivo));
			
			while ((linha = br.readLine()) != null) {
				
				String tipoDoRegistro = linha.substring(95, 141);

				ConsumoEmbratel consumo = new ConsumoEmbratel();
				consumo.setFatura(fatura);
				consumo.setServico(linha.substring(2, 6));
				consumo.setSequencia(pegaSequencia(linha));
				consumo.setRamal(pegaRamal(linha));
				if(tipoDoRegistro.contains("DESCONTO")) 					
					consumo.setValor(pegaValor(linha).multiply(new BigDecimal("-1")));
				else				
					consumo.setValor(pegaValor(linha));
				consumos.add(consumo);				
			
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("");
		System.out.println("FIM DO ARQUIVO");		
		return consumos;		
	}

	private Integer pegaSequencia(String linha){		
		return Integer.parseInt(linha.substring(7, 14));
	}
	
	private Integer pegaRamal(String linha){		
		Integer ddd=0;
		Integer ramal=0;
		@SuppressWarnings("unused")
		int x = 0;
		String telefone = linha.substring(26, 48);					
		try {
			x = Integer.parseInt(telefone.substring(0, 1));
		}catch (NumberFormatException e) {
			telefone = telefone.substring(1, telefone.length());
		}
		
		try {
			x = Integer.parseInt(telefone.substring(12, 13));
			ddd = Integer.parseInt(telefone.substring(12, 14));
			if(ddd==16)
				ramal = Integer.parseInt(telefone.substring(18, telefone.length()));			
			if(ddd==11)
				ramal = Integer.parseInt(telefone.substring(19, telefone.length()));
			
		}catch (NumberFormatException e) {
			ddd = Integer.parseInt(telefone.substring(0, 2));
			if(ddd==16)
				ramal = Integer.parseInt(telefone.substring(6, 10));			
			if(ddd==11)
				ramal = Integer.parseInt(telefone.substring(7, 10));									
		}
		return ramal;
	}	
	
	private BigDecimal pegaValor(String linha) {
		String valor = linha.substring(163, 174);
		return new BigDecimal(valor).setScale(2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(100));
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,BigDecimal> rateio(String fatura){
		if(!manager.isOpen())
			abreConexao();
		Map<String,BigDecimal> rateio = new HashMap<String,BigDecimal>();
		String sql = "select ccusto.codigo,sum(consumoembratel.valor) as valor from ramal,ccusto,consumoembratel "
				+ " where ramal.ccusto_id=ccusto.id and consumoembratel.ramal=ramal.ramalint and "
				+ " consumoembratel.fatura= :fatura group by ccusto.codigo";
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("fatura", fatura).getResultList();
		
		for(Object[] o:list){
			rateio.put(o[0].toString(), new BigDecimal(o[1].toString()));
		}
		return rateio;		
	}
	
	@SuppressWarnings("unchecked")
	public List<RateioFinalEmbratel> rateioFinal(String fatura){
		if(!manager.isOpen())
			abreConexao();

		String sql = "SELECT codigo,valor,tipo,servico,filial_id FROM rateioembratel where fatura= :fatura order by tipo,filial_id";
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("fatura", fatura).getResultList();
		List<RateioFinalEmbratel> rats = new ArrayList<RateioFinalEmbratel>();
		
		for(Object[] o:list){
			RateioFinalEmbratel rat = new RateioFinalEmbratel();
			
			rat.setCcusto(o[0].toString());
			rat.setValor(new BigDecimal(o[1].toString()));
			rat.setTipo(o[2].toString());
			rat.setServico(o[3].toString());
			rat.setFilial((int) o[4]);
			rats.add(rat);
			//System.out.println(rat.getCcusto() + " ## " + rat.getValor() );
		}
		return rats;		
	}	
	
	private void abreConexao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public boolean faturaJaExiste(String fatura) {
	    List<ConsumoEmbratel> list = this.manager.createQuery(" From consumoembratel Where fatura= :fatura ").setParameter("fatura", fatura).getResultList();
	    return !list.isEmpty();
	}
	
	public List<ConsumoEmbratelDesconto> carregaDescontoArquivo(String arquivo, String fatura) {
		String linha="";
		List<ConsumoEmbratelDesconto> descontos = new ArrayList<ConsumoEmbratelDesconto>();
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(arquivo));
			
			while ((linha = br.readLine()) != null) {
				
				String tipoDoRegistro = linha.substring(95, 141);
				
				if(tipoDoRegistro.contains("DESCONTO")) {					
					ConsumoEmbratelDesconto desconto = new ConsumoEmbratelDesconto();
					desconto.setFatura(fatura);
					desconto.setSequencia(pegaSequencia(linha));
					desconto.setRamal(pegaRamal(linha));
					desconto.setValor(pegaValor(linha));
					descontos.add(desconto);
				}
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("");

		return descontos;		

	}
}
