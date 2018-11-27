package br.com.mauricio.news.test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.mauricio.news.util.SaveFile;

public class CriarEstruturaPastaRede {
	private static String CAMINHO = "\\\\RMS06\\D$\\DocClientesFornecedores\\";
	
	public static void main(String[] args) {
		Integer mes = 11;
		Integer ano = 2018;
		String path = CAMINHO+ano.toString()+"\\"+nomeMes(mes)+"\\";
		
		List<LocalDate> listaDosDiasUteisDoMes = diasUteis(11,2018);
		listaDosDiasUteisDoMes.forEach(d -> {
			SaveFile.criarPasta(path + d.getDayOfMonth() + "\\PAG");
			SaveFile.criarPasta(path + d.getDayOfMonth() + "\\REC");
		});
	}
	
	private static List<LocalDate> diasUteis(Integer mes,Integer ano){
		YearMonth anoMes = YearMonth.of(ano, mes);
		return Stream.iterate(anoMes.atDay(1),  data -> data.plusDays(1)).limit(anoMes.lengthOfMonth())
		        .filter(data -> !data.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !data.getDayOfWeek().equals(DayOfWeek.SUNDAY)).collect(Collectors.toList());

	}
	
	private static String nomeMes(Integer mes){
		switch (mes) {
			case 1:
				return "Jan";
			case 2:
				return "Fev";
			case 3:
				return "Mar";
			case 4:
				return "Abr";
			case 5:
				return "Mai";
			case 6:
				return "Jun";
			case 7:
				return "Jul";
			case 8:
				return "Ago";
			case 9:
				return "Set";
			case 10:
				return "Out";
			case 11:
				return "Nov";
			case 12:
				return "Dez";				
		}
		return mes.toString();
	}
}
