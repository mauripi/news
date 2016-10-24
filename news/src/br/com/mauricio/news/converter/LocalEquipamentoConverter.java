package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.ti.LocalEquipamento;

@FacesConverter("localequipamentoConverter")
public class LocalEquipamentoConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof LocalEquipamento) {
        	LocalEquipamento entity = (LocalEquipamento) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getDescricao());
            return sb.toString();
        }
        return null;
    }
}
