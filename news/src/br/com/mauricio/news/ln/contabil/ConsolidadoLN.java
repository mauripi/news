package br.com.mauricio.news.ln.contabil;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.contabil.ConsolidadoDao;
import br.com.mauricio.news.dao.contabil.GrupoConsolidadoDao;
import br.com.mauricio.news.dao.contabil.HistoricoConsolidadoDao;
import br.com.mauricio.news.dao.contabil.LancamentoConsolidadoDao;
import br.com.mauricio.news.dao.contabil.RazaoDao;
import br.com.mauricio.news.model.contabil.Consolidado;
import br.com.mauricio.news.model.contabil.GrupoConsolidado;
import br.com.mauricio.news.model.contabil.HistoricoConsolidado;
import br.com.mauricio.news.model.contabil.LancamentoConsolidado;
import br.com.mauricio.news.model.contabil.PlanoConsolidado;
import br.com.mauricio.news.model.contabil.Razao;
import br.com.mauricio.news.model.contabil.ViewConsolidado;


public class ConsolidadoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;

	public ConsolidadoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public ConsolidadoLN(EntityManager manager){
		this.manager = manager;
	}
	
	public List<PlanoConsolidado> getListPlanoConsolidado() {
		GenericDao<PlanoConsolidado> dao = new GenericDao<PlanoConsolidado>(manager);
		return dao.listWithoutRemoved("planoconsolidado", "clacta");
	}

	public List<Consolidado> getListConsolidado() {
		GenericDao<Consolidado> dao = new GenericDao<Consolidado>(manager);
		return dao.listWithoutRemoved("consolidado", "clacta");
	}
	
	public String criaPlano() {
		GenericDao<PlanoConsolidado> dao = new GenericDao<PlanoConsolidado>(manager);
		GrupoConsolidadoDao gcdao = new GrupoConsolidadoDao();	
		ConsolidadoDao cdao = new ConsolidadoDao(manager);
		List<PlanoConsolidado> planoSapiens = new ArrayList<PlanoConsolidado>();
		List<GrupoConsolidado> grupos = new ArrayList<GrupoConsolidado>();
		try {
			grupos=gcdao.obterGrupos();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		//Carrega o Plano de contas
		for(GrupoConsolidado g:grupos){
			PlanoConsolidado p = new PlanoConsolidado();
			p.setClacta(g.getClacta());
			p.setCtared(g.getCtared());
			p.setDescta(g.getDescta());
			p.setGructa(g.getGructa());
			p.setNatcta(g.getNatcta());
			p.setNivcta(g.getNivcta());	
			planoSapiens.add(p);
		}
		cdao.deletaPlano();
		dao.saveList(planoSapiens);
		
		return "Criado com sucesso.";
	}

	public String atualizaPlano() {
		GenericDao<PlanoConsolidado> dao = new GenericDao<PlanoConsolidado>(manager);
		GrupoConsolidadoDao gcdao = new GrupoConsolidadoDao();	
		List<PlanoConsolidado> planoSapiens = new ArrayList<PlanoConsolidado>();
		List<PlanoConsolidado> planoBD = new ArrayList<PlanoConsolidado>();
		List<GrupoConsolidado> grupos = new ArrayList<GrupoConsolidado>();
		planoBD = getListPlanoConsolidado();
		try {
			grupos=gcdao.obterGrupos();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		//Carrega o Plano de contas
		for(GrupoConsolidado g:grupos){
			PlanoConsolidado p = new PlanoConsolidado();
			p.setClacta(g.getClacta());
			p.setCtared(g.getCtared());
			p.setDescta(g.getDescta());
			p.setGructa(g.getGructa());
			p.setNatcta(g.getNatcta());
			p.setNivcta(g.getNivcta());	
			planoSapiens.add(p);
		}

		if(planoBD.size()>=planoSapiens.size()){
			for(PlanoConsolidado p: planoBD)
				for(PlanoConsolidado ps: planoSapiens)
					if(p.getClacta()!=ps.getClacta())
						dao.save(ps);
		}else{
			for(PlanoConsolidado ps: planoSapiens)
				for(PlanoConsolidado p:planoBD )
					if(!p.getClacta().equals(ps.getClacta()))
						dao.save(ps);			
		}
		return "Atualizado com sucesso.";
	}

	public String importaConsolidado(int mes, int ano){
		GenericDao<Consolidado> dao = new GenericDao<Consolidado>(manager);
		GenericDao<Razao> rdao = new GenericDao<Razao>(manager);
		RazaoDao rzdao = new RazaoDao(manager);
		GrupoConsolidadoDao gdao = new GrupoConsolidadoDao();
		LancamentoConsolidadoDao ldao = new LancamentoConsolidadoDao();
		List<GrupoConsolidado> grupos = new ArrayList<GrupoConsolidado>();
		List<LancamentoConsolidado> lancamentos = new ArrayList<LancamentoConsolidado>();
		List<Consolidado> consolidados = new ArrayList<Consolidado>();
		List<Consolidado> conNivelQuatro = new ArrayList<Consolidado>();
		List<Razao> razoes = new ArrayList<Razao>();
		
		try {
			grupos=gdao.obterGrupos(); // busca as contas do grupo 3 e 4 do plano dee contas.
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		try {
			lancamentos = ldao.obterLancamentos(ano, mes); //busca os lançamento contábeis do periodo
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		/*
		for(LancamentoConsolidado l:lancamentos){
			if(l.getClacta().toString().equals("411200005")){
				System.out.println(l.getVlrlct());
				System.out.println();
			}
		}
		*/
		for(LancamentoConsolidado l:lancamentos){ //carrega para salvar o lançamentos importados no banco. Utilizado para 
			Razao r = new Razao();					//quando clicar na conta do relatorio exibir os lançamentos
			r.setData(l.getDatlct());
			r.setClacta(l.getClacta());
			r.setDescta(l.getDescta());
			r.setValor(l.getVlrlct());
			r.setCtacre(l.getCtacre());
			r.setCtadeb(l.getCtadeb());
			r.setHistorico(l.getHistorico());
			r.setTipo(l.getTipo());
			razoes.add(r);
		}
		
		for(GrupoConsolidado g:grupos){  //popula para totalizar as contas
			Consolidado c = new Consolidado();
			c.setAno(ano);
			c.setMes(mes);
			c.setClacta(g.getClacta());		

			for(LancamentoConsolidado l:lancamentos){
				if(g.getCtared().equals(l.getCtadeb()))
					c.setValor(c.getValor()+l.getVlrlct());
				if(g.getCtared().equals(l.getCtacre()))
					c.setValor(c.getValor()+l.getVlrlct());				
			}
			if(g.getNivcta().equals(4))
				conNivelQuatro.add(c);
			consolidados.add(c);
		}
		
		for(Consolidado c: consolidados){//subtrai as reversões das despesas
			for(LancamentoConsolidado l:lancamentos){
				if(c.getClacta().equals(l.getClacta())&&l.getTipo().equals("R")){
					BigDecimal lanc = new BigDecimal(l.getVlrlct()).setScale(2, RoundingMode.HALF_DOWN);	
					
					BigDecimal dobro = lanc.multiply(new BigDecimal("2")).setScale(2, RoundingMode.HALF_DOWN);
					BigDecimal tot = new BigDecimal(c.getValor()).setScale(2, RoundingMode.HALF_DOWN);
					c.setValor(tot.subtract(dobro).doubleValue());
				}
			}
		}
					
		//soma total do nivel 4
		for(Consolidado n:conNivelQuatro)
			for(Consolidado c: consolidados)
				if(c.getClacta().toString().contains(n.getClacta().toString()))
					n.setValor(n.getValor()+c.getValor());	
		
		//atualiza valor consolidado nivel 4
		for(Consolidado n:conNivelQuatro)
			for(Consolidado c: consolidados)
				if(c.getClacta().equals(n.getClacta()))
					c.setValor(n.getValor());

		BigDecimal custo = new BigDecimal("0.0").setScale(2, RoundingMode.HALF_DOWN);
		BigDecimal cancelamento = new BigDecimal("0.0").setScale(2, RoundingMode.HALF_DOWN);
		
		for(Consolidado c: consolidados){//subtrai as reversões das receitas
			if(c.getClacta().toString().equals("331010003"))
				custo = new BigDecimal(c.getValor()); //soma custo na alienção de bens imobilizado
			if(c.getClacta().toString().equals("312010001"))
				cancelamento = new BigDecimal(c.getValor()); //soma CANCELAMENTOS E ABATIMENTOS de receita
			
			for(LancamentoConsolidado l:lancamentos){
				if(c.getClacta().toString().equals(l.getClacta().toString().substring(0, 5))&&l.getTipo().equals("T")&&!c.getClacta().toString().substring(0, 5).equals("31202")){
					BigDecimal tot = new BigDecimal(c.getValor()).setScale(2, RoundingMode.HALF_DOWN);
					BigDecimal lanc = new BigDecimal(l.getVlrlct()).setScale(2, RoundingMode.HALF_DOWN);
					c.setValor(tot.subtract(lanc).doubleValue());
				}
			}
		}
		
		int negativo = -1;
		
		for(Consolidado c: consolidados){
			if(c.getClacta().toString().length()==5 && c.getClacta().toString().substring(0, 5).equals("33101")){
				BigDecimal tot = new BigDecimal(c.getValor()).setScale(2, RoundingMode.HALF_DOWN);
				c.setValor(tot.subtract(custo).doubleValue());
			}
			if(c.getClacta().toString().length()==5 && c.getClacta().toString().substring(0, 5).equals("31201")){
				BigDecimal tot = new BigDecimal(c.getValor()).setScale(2, RoundingMode.HALF_DOWN);
				c.setValor(tot.subtract(cancelamento).doubleValue());
			}			
			if(c.getClacta().toString().length()==5 && c.getClacta().toString().substring(0, 5).equals("31202"))
				c.setValor(negativo*c.getValor()); //passa os impostos para negativo								
		}
		
		ConsolidadoDao cdao = new ConsolidadoDao(); 
		cdao.deletaPorMesAno(mes, ano);
		dao.saveList(consolidados);
		rzdao.deletePeriodo(mes, ano);
		rdao.saveList(razoes);
		return "Importado com sucesso.";
	}

	public List<Consolidado> findByConta(Integer numeroConta, int ano) {
		ConsolidadoDao dao = new ConsolidadoDao(manager);
		return dao.findByConta(numeroConta,ano);
	}
 
	public List<HistoricoConsolidado> listaPorContaAno(Integer numeroConta, int ano) {
		HistoricoConsolidadoDao hdao = new HistoricoConsolidadoDao(manager);
		return hdao.listaPorContaAno(numeroConta,ano);
	}
	
	public Consolidado findByContaMesAno(int conta, Integer mes, Integer ano) {
		ConsolidadoDao dao = new ConsolidadoDao(manager);
		return dao.findByContaMesAno(conta,mes,ano);
	}

	public List<Razao> listRazaoContaMesAno(int conta, Integer mes, Integer ano) {
		RazaoDao dao = new RazaoDao(manager);
		return dao.findByContaMesAno(conta,mes,ano);
	}
	
    public List<ViewConsolidado> view(int ano){
    	ConsolidadoDao dao = new ConsolidadoDao();
		return dao.getConsolidados(ano);
    }   
        
	public String atualizaConsolidado(Consolidado c) {
		GenericDao<Consolidado> dao = new GenericDao<Consolidado>(manager);
		dao.update(c);		
		return "Atualizado com sucesso.";
	}

	public List<HistoricoConsolidado> listaHistoricoPorMesAno(Integer mes,Integer ano) {
		HistoricoConsolidadoDao hdao = new HistoricoConsolidadoDao();
		return hdao.listaPorMesAno(mes, ano);
	}
	
	public HistoricoConsolidado findByHistoricoContaMesAno(int contaSelecionada, Integer mes, Integer ano) {
		HistoricoConsolidadoDao hdao = new HistoricoConsolidadoDao();
		return hdao.findByHistoricoContaMesAno(contaSelecionada,mes,ano);
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
