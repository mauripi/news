package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.ti.AreaSolicitacao;

@FacesConverter("areaSolicitacaoConverter")
public class AreaSolicitacaoConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof AreaSolicitacao) {
        	AreaSolicitacao entity = (AreaSolicitacao) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getDescricao());
            return sb.toString();
        }
        return null;
    }
}
