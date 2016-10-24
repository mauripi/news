package br.com.mauricio.news.mb.engenharia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.engenharia.ControleSinalLN;
import br.com.mauricio.news.model.engenharia.ControleSinal;
import br.com.mauricio.news.model.engenharia.Posto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

@ManagedBean(name="relcontrolesinalMB")
@ViewScoped
public class RelControleSinalBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ControleSinalLN ln = new ControleSinalLN();
	private List<ControleSinal> controlesinais = new ArrayList<ControleSinal>();
	private List<ControleSinal> tx = new ArrayList<ControleSinal>();
	private List<ControleSinal> ar = new ArrayList<ControleSinal>();
	private Posto posto = new Posto();
	private List<Posto> postos = new ArrayList<Posto>();


	@PostConstruct
	public void init(){
		//listar();
		listarPosto();
	}

	public void listar(){
		controlesinais = new ArrayList<ControleSinal>();
		ar = new ArrayList<ControleSinal>();
		tx = new ArrayList<ControleSinal>();
		
		ln = new ControleSinalLN();
		List<ControleSinal> all = ln.getList();
		for(ControleSinal s:all){
			if(s.getPosto().getId().equals(posto.getId())){
				if(s.getTipoequipamento().contains("TX"))
					tx.add(s);
				else
					ar.add(s);
			}
		}
	}

	public void listarAll(){
		controlesinais = new ArrayList<ControleSinal>();	
		ln = new ControleSinalLN();
		controlesinais = ln.getList();

	}
	
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();  	
        
        for(int j=0; j < sheet.getLastRowNum()+1;j++){
        	HSSFRow r = sheet.getRow(j);
        	for(int i=0; i < r.getPhysicalNumberOfCells();i++) {
        		HSSFCell cell = r.getCell(i);                   
        		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        		cell.setCellStyle(cellStyle);
        	}      	
        }
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
        	HSSFCellStyle style = wb.createCellStyle();  
        	style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        	HSSFCell cell = header.getCell(i);
        	cell.setCellStyle(style);
        }
    }	
    
	public void listarPosto(){
		GenericLN<Posto> gln = new GenericLN<Posto>();
		postos = gln.listWithoutRemoved("posto", "id");
	}
	
    public List<Posto> completePosto(String query) {
        List<Posto> filtered = new ArrayList<Posto>(); 
        for (int i = 0; i < postos.size(); i++) 
            if(postos.get(i).getNome().toLowerCase().contains(query))
                filtered.add(postos.get(i));
        return filtered;
    }
    

 
	//--------------------------GETTERS E SETTERS ----------------------------------
	public Date getToday() {
        return new Date();
    }

	public ControleSinalLN getLn() {
		return ln;
	}

	public void setLn(ControleSinalLN ln) {
		this.ln = ln;
	}

	public List<ControleSinal> getControlesinais() {
		return controlesinais;
	}

	public void setControlesinais(List<ControleSinal> controlesinais) {
		this.controlesinais = controlesinais;
	}

	public Posto getPosto() {
		return posto;
	}

	public void setPosto(Posto posto) {
		this.posto = posto;
	}

	public List<Posto> getPostos() {
		return postos;
	}

	public void setPostos(List<Posto> postos) {
		this.postos = postos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ControleSinal> getTx() {
		return tx;
	}

	public void setTx(List<ControleSinal> tx) {
		this.tx = tx;
	}

	public List<ControleSinal> getAr() {
		return ar;
	}

	public void setAr(List<ControleSinal> ar) {
		this.ar = ar;
	}
	
}
