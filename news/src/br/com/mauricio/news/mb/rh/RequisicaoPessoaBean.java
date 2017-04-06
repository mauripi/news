package br.com.mauricio.news.mb.rh;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name="reqpessoaMB")
public class RequisicaoPessoaBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private static String[] bands = {"Arch Enemy","Blind Guardian","Children of Bodom","Dimmu Borgir","Edge of Sanity",
    "Fields of the Nephilim", "Gates of Ishtar", "Holy Moses", "Iced Earth", "Jethro Tull",
    "Kreator", "Lamb of God", "Mekong Delta", "Night in Gales", "Old Dead Tree", "Persefone",
    "Running Wild", "Skyclad", "The Dillinger Escape Plan", "Theater of Tragedy", "Unleashed", "Vanden Plas", "Within Temptation", "Xystus", "Yes",
    "Zenobia",
    };
 
   public String[] getBands() {
        return bands;
    }
     
    public List<String> getBandsList() {
        return Arrays.asList(bands);
    }	
	
	
	
	
}
