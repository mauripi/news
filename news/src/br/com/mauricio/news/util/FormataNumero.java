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

}
