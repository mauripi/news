package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.MCLIFOR;

@ManagedBean(name="mcliforService", eager = true)
@ApplicationScoped
public class MCLIFORService implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<MCLIFOR> allCliFor;
	
	@PostConstruct
	public void init(){
		allCliFor = new ArrayList<MCLIFOR>();
		GenericLN<MCLIFOR> gln = new GenericLN<MCLIFOR>();
		allCliFor=gln.listWithoutRemoved("mclifor", "nomfan");
	}

	

	public List<MCLIFOR> getAllCliFor() {
		return allCliFor;
	}


}
