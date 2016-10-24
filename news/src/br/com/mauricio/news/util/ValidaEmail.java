package br.com.mauricio.news.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaEmail {
	 public static final boolean validar(String email) {
		 if(!email.equals("")){
		    Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"); 
		    Matcher m = p.matcher(email); 
		    if (m.find())
		      return true;
		    else
		      return false;		
		 }else{
			 return false;
		 }
	}

}
