package br.com.mauricio.news.Teste;

import java.io.IOException;
import java.util.List;

import br.com.mauricio.news.dao.MCLIFORERPDao;
import br.com.mauricio.news.model.MCLIFOR;

public class Teste {

	public static void main(String[] args) throws IOException {
		MCLIFORERPDao dao = new MCLIFORERPDao();
		String s = "02344518000178";
		String filtro="2";
		String nome="TELEVISÃO";
		List<MCLIFOR> xs = dao.getCliFor(nome,filtro);
		
		for(MCLIFOR x:xs)
			if(x.getClifor().equals(1))
				System.out.println("CLIENTE:  - "+x.getCodcli()+ "  - "+x.getNomraz()+ "  - "+x.getClifor());
			else
				System.out.println("FORNECEDOR:  - "+x.getCodfor()+ "  - "+x.getNomraz()+ "  - "+x.getClifor());
			

	}

}
