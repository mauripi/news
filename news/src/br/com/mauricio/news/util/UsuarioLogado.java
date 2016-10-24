package br.com.mauricio.news.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.mauricio.news.model.Login;


public class UsuarioLogado {

	
    public static Login getUser(){
    	FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
		Login l = (Login) sessao.getAttribute("login");
		return l;
	}
	
	public static String getUserNome(){
    	FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
        if(sessao.getAttribute("login")==null){
        	return "ANONIMO";
		}else{
			Login l = (Login) sessao.getAttribute("login");
			return l.getNome();
		}
	}
}
