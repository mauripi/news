package br.com.mauricio.news.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.mauricio.news.model.Login;

public class LoginDataModel extends ListDataModel<Login> implements SelectableDataModel<Login>,Serializable{

	private static final long serialVersionUID = 1L;

	public LoginDataModel(){}
	
	public LoginDataModel(List<Login> data){
		super(data);
	}
	
	@Override
	public Login getRowData(String chapa) {
		@SuppressWarnings("unchecked")
		List<Login> logins = (List<Login>) getWrappedData();
		for (Login login : logins) {
			if(login.getChapa().equals(chapa))
				return login;
		}
		return null;
	}

	@Override
	public Object getRowKey(Login l) {
		return l.getChapa();
	}

}
