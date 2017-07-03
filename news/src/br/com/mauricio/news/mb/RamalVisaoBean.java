package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.EmpresaRamal;


/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/

@ManagedBean (name="ramalvisaoMB")
@ViewScoped
public class RamalVisaoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<EmpresaRamal> empresas;
	
	@PostConstruct
	public void init(){
		listar();
	}

	private void listar() {
		GenericLN<EmpresaRamal> gln = new GenericLN<EmpresaRamal>();		
		empresas = gln.listWithoutRemoved("empresaramal", "id");	
	}

	public List<EmpresaRamal> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<EmpresaRamal> empresas) {
		this.empresas = empresas;
	}
}
