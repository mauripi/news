package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.EmpresaRamal;

@FacesConverter("empresaRamalConverter")
public class EmpresaRamalConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof EmpresaRamal) {
        	EmpresaRamal entity = (EmpresaRamal) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getNome());
            return sb.toString();
        }
        return null;
    }
}
