package br.com.mauricio.news.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.mauricio.news.model.ti.ConsumoEmbratel;

public class TesteConsumo {
	static List<String> erros = new ArrayList<String>();
	static List<ConsumoEmbratel> consumos = new ArrayList<ConsumoEmbratel>();
	static String linha="";
	static String fatura="000158985";
	static BufferedReader br = null;
	
	public static void main(String[] args) {
		try {
			br = new BufferedReader(new FileReader("C:\\FAT_181121000124.txt"));
			while ((linha = br.readLine()) != null) {

				
				String tipoDoRegistro = linha.substring(95, 141);
				
				if(!tipoDoRegistro.contains("DESCONTO")) {					
					ConsumoEmbratel consumo = new ConsumoEmbratel();
					consumo.setFatura(fatura);
					consumo.setSequencia(pegaSequencia(linha));
					consumo.setRamal(pegaRamal(linha));
					consumo.setValor(pegaValor(linha));
					consumos.add(consumo);
				}
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("");
		System.out.println("FIM DO ARQUIVO");
	}


	private static Integer pegaSequencia(String linha){		
		return Integer.parseInt(linha.substring(7, 14));
	}
	
	private static Integer pegaRamal(String linha){		
		Integer ddd=0;
		Integer ramal=0;
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
	
	private static BigDecimal pegaValor(String linha2) {
		String valor = linha.substring(163, 174);
		return new BigDecimal(valor).setScale(2, BigDecimal.ROUND_DOWN).divide(new BigDecimal(100));
	}
}
