package br.com.mauricio.news.filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class FiltroCorrecaoIE9 implements Filter {
 
   public void init(FilterConfig filterConfig) throws ServletException {
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 
	   if (!((HttpServletRequest) request).getRequestURI().startsWith(((HttpServletRequest) request).getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
		   ((HttpServletResponse) response).setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		   ((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=EmulateIE8");		  
		   ((HttpServletResponse) response).setHeader("Pragma", "no-cache"); // HTTP 1.0.
		   ((HttpServletResponse) response).setDateHeader("Expires", 0); // Proxies.
       } 
	   ((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=EmulateIE8");
     
      chain.doFilter(request, response);
  
   }
 
   public void destroy() {
   }
}