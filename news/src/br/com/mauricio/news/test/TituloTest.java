package br.com.mauricio.news.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.mauricio.news.dao.financeiro.TítuloDao;
import br.com.mauricio.news.model.financeiro.Titulo;

public class TituloTest {

	public static void main(String[] args) throws ParseException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date d1=null;
		Date d2=null;
		d1 = df.parse("05/12/2017");
		d2 = df.parse("30/12/2017");
		
		TítuloDao dao = new TítuloDao();
		List<Titulo> titulos = dao.buscarTitulos(d1, d2);
		
		titulos.forEach(t -> System.out.println(t.getOritit() + " - " + t.getCodtns() + " => " + t.getNomclifor()));

	}

}
