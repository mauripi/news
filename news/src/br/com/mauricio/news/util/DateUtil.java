package br.com.mauricio.news.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Days;

public class DateUtil {
	
	public static int diferencaEmDias(Date d1, Date d2){
		return Days.daysBetween(new DateTime(d1),new DateTime(d2)).getDays();
	}

	public static boolean isMenor(Date d1, Date d2){
		int n = DateTimeComparator.getDateOnlyInstance().compare(new DateTime(d1),new DateTime(d2));
		if(n>0)
			return false;
		return true;
	}
	
	public static boolean isMaior(Date d1, Date d2){
		int n = DateTimeComparator.getDateOnlyInstance().compare(new DateTime(d1),new DateTime(d2));
		if(n<0)
			return false;
		return true;
	}	
	
	public static Date hoje(){
		DateTime hoje = new DateTime();
		return new DateTime(hoje.getYear(),hoje.getMonthOfYear(),hoje.getDayOfMonth(), 0, 0).toDate();	
	}

	public static String formatDataFromBanco(Date d){
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		return formato.format(d);
	}

}
