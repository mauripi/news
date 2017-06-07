package br.com.mauricio.news.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormataNumero {
	
	public static String doubleTOMoedaReal(Double valor){
		DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
		formatoDois.setMinimumFractionDigits(2); 
		formatoDois.setParseBigDecimal (true);
		return formatoDois.format(valor);
	}

	public static String cpfToString(String cpf){
		return cpf.substring(0, 3) +"."+cpf.substring(3, 6) +"."+cpf.substring(6, 9) +"-"+cpf.substring(9, 11);
	}
}
