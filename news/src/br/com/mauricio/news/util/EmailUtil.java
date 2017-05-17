package br.com.mauricio.news.util;

import java.util.ArrayList;
import java.util.List;

public class EmailUtil {

	public static List<String> stringToListSeparadoPorVirgula(String s){
		List<String> list = new ArrayList<>();
		String [] emails = s.split(",");
		for(int i=0; i<emails.length;i++)
			if(ValidaEmail.validar(emails[i]))
				list.add(emails[i]);			
		return list;
	}
	
	
	public static String listToString(List<String> list){
		StringBuffer sb = new StringBuffer();
		for (String s : list)
			sb.append(s +",");
		return sb.toString();
	}
	
	public static String formataParaEnvio(String s){
		String [] emails = s.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<emails.length;i++)
			sb.append(emails[i]+",");
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
}
