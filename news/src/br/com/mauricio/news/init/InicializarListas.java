package br.com.mauricio.news.init;

import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InicializarListas extends HttpServlet implements Serializable{

	private static final long serialVersionUID = 1L;

	@Override  
    public void init(ServletConfig config) throws ServletException {  
        super.init(config);  
        
        System.out.println("****  Mauricio Cruz - Aplicação iniciada. ****");  
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
    
}
