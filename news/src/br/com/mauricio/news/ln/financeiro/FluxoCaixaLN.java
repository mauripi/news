package br.com.mauricio.news.ln.financeiro;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.financeiro.FluxoCaixaDao;
import br.com.mauricio.news.model.financeiro.ClassificacaoFluxoCaixa;
import br.com.mauricio.news.model.financeiro.FluxoCaixa;

public class FluxoCaixaLN implements Serializable{

	private static final long serialVersionUID = 1L;
	private FluxoCaixaDao dao;

	public FluxoCaixaLN(){
		this.dao = new FluxoCaixaDao();
	}
	
	public List<FluxoCaixa>  listar(){
		return dao.listar();
	}	
	
	public String adicionar(FluxoCaixa f){
		try{
			dao.adicionar(f);
			return "Registro adicionado.";
		}catch (Exception e) {			
			System.out.println(e);
			return "Erro ao adicionar registro.";
		}
	}
	
	public String alterar(FluxoCaixa f){
		try{
			dao.alterar(f);
			return "Registro alterado.";
		}catch (Exception e) {			
			System.out.println(e);
			return "Erro ao alterar registro.";
		}			
	}
	
	public String remover(Integer id){
		try{		
			dao.remover(id);
			return "Registro removido.";
		}catch (Exception e) {			
			System.out.println(e);
			return "Erro ao remover registro.";
		}
	}

	public List<ClassificacaoFluxoCaixa>  listarClassificacao(){
		return dao.listarClassificacao();
	}	


}
