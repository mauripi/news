package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.financeiro.FluxoCaixaLN;
import br.com.mauricio.news.model.financeiro.ClassificacaoFluxoCaixa;

@FacesConverter("classificacaofluxocaixaConverter")
public class ClassificacaoFluxoCaixaConverter  implements Converter{

	   public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	            	ClassificacaoFluxoCaixa selecionado = new ClassificacaoFluxoCaixa();
	            	FluxoCaixaLN ln = new FluxoCaixaLN();
	            	List<ClassificacaoFluxoCaixa> movimentos = ln.listarClassificacao();
	            	for(ClassificacaoFluxoCaixa t:movimentos)
	            		if(t.getCtared() ==Integer.parseInt(value))
	            			selecionado=t;
	                return selecionado;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do ClassificacaoFluxoCaixa", "Classificacao não válida."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((ClassificacaoFluxoCaixa) object).getCtared());
	        }
	        else {
	            return null;
	        }
	    } 
}
