package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.contabil.PlanoConsolidado;

@FacesConverter("planoConverter")
public class PlanoConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof PlanoConsolidado) {
        	PlanoConsolidado entity = (PlanoConsolidado) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getDescta());
            return sb.toString();
        }
        return null;
    }
}
