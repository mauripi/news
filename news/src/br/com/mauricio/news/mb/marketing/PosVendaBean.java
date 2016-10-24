package br.com.mauricio.news.mb.marketing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import br.com.mauricio.news.ln.marketing.PosVendaLN;
import br.com.mauricio.news.model.marketing.PosVenda;

/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="posvendaMB")
@ViewScoped
public class PosVendaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private PosVendaLN ln;	
	private List<PosVenda> posvendas = new ArrayList<PosVenda>();	
	private int mesi;
	private int anoi;
	private int mesf;
	private int anof;
	
	@PostConstruct
	public void init(){
		DateTime d = new DateTime();
		mesi=mesf=d.getMonthOfYear();
		anoi=anof=d.getYear();
		listar();
	}

	public void listar(){
		ln = new PosVendaLN();
		posvendas = ln.listaPosVenda(getDataInicial(mesi,anoi),getDataFinal(mesf,anof));		
	}
	
	private Date getDataInicial(int mesi2, int anoi2) {
		LocalDate d = new LocalDate(anoi,mesi,5);
		return d.dayOfMonth().withMinimumValue().toDate();
	}

 	private Date getDataFinal(int mesf2, int anof2) {
 		LocalDate d = new LocalDate(anof,mesf,5);
		return d.dayOfMonth().withMaximumValue().toDate();
	}


	public String nomeMes(int i){
		String nome="";
	       switch (i) {
	           case 1:
	        	   nome= "Jan";
	               break;
	           case 2:
	        	   nome= "Fev";
	               break;
	           case 3:
	        	   nome= "Mar";
	               break;
	           case 4:
	        	   nome= "Abr";
	               break;
	           case 5:
	        	   nome= "Mai"; 
	               break;
	           case 6:
	        	   nome= "Jun";
	               break;
	            case 7:
	               nome= "Jul";
	               break;
	            case 8:
	            	nome= "Ago";
	                break;
	            case 9:
	            	nome= "Set";
	                break;
	            case 10:
	            	nome= "Out";
	                break;
	            case 11:
	            	 nome= "Nov";
	                 break;
	            case 12:
	            	 nome= "Dez";
	                 break; 
	       }
		return nome;	       
    }
    
	//===========================================================================
	public PosVendaLN getLn() {
		return ln;
	}
	public void setLn(PosVendaLN ln) {
		this.ln = ln;
	}
	public List<PosVenda> getPosvendas() {
		return posvendas;
	}
	public void setPosvendas(List<PosVenda> posvendas) {
		this.posvendas = posvendas;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getMesi() {
		return mesi;
	}
	public void setMesi(int mesi) {
		this.mesi = mesi;
	}
	public int getAnoi() {
		return anoi;
	}
	public void setAnoi(int anoi) {
		this.anoi = anoi;
	}
	public int getMesf() {
		return mesf;
	}
	public void setMesf(int mesf) {
		this.mesf = mesf;
	}
	public int getAnof() {
		return anof;
	}
	public void setAnof(int anof) {
		this.anof = anof;
	}

}
