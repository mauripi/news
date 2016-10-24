package br.com.mauricio.news.filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class FiltroAutorizacao implements Filter {
	
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void destroy() {}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    HttpSession session = request.getSession(false);

	    Object auth = (session != null) ? session.getAttribute("login") : null;
	    String loginURL = request.getContextPath() + "/login.jsf";

	    boolean loggedIn = auth != null ;
	    boolean loginRequest = request.getRequestURI().equals(loginURL);
	    boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + "/sistema" + ResourceHandler.RESOURCE_IDENTIFIER);

	    if (loggedIn || loginRequest || resourceRequest) {
	        if (!resourceRequest) {
	            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	            response.setDateHeader("Expires", 0); // Proxies.
	        }

	        chain.doFilter(request, response);
	    } else {
	        response.sendRedirect(loginURL);
	    }
	}
}
