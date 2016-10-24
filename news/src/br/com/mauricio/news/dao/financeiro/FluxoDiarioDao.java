package br.com.mauricio.news.dao.financeiro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import br.com.mauricio.news.model.financeiro.CampoPlanilha;
import br.com.mauricio.news.model.financeiro.FluxoDiario;
import br.com.mauricio.news.model.financeiro.Custo;

/**
*
* @author MAURICIO CRUZ
*/
public class FluxoDiarioDao {
	private EntityManager manager;
	
	public FluxoDiarioDao(EntityManager manager){
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	public List<FluxoDiario> listaPorMesAnoTipo(int mes, int ano,String tipo) {
		DateTime d = new DateTime(ano,mes,8,0,0);
		return this.manager.createQuery(" from fluxodiario where data BETWEEN :startDate AND :endDate and tipo= :tipo order by data")
				.setParameter("startDate", d.dayOfMonth().withMinimumValue().toDate()).setParameter("endDate", d.dayOfMonth().withMaximumValue().toDate()).setParameter("tipo", tipo).getResultList();
	}

	public void removePeriodoDoBanco(Integer mes, Integer ano){
		manager.createNativeQuery("delete from fluxodiario where YEAR(data)= :ano and MONTH(data)= :mes ")
			.setParameter("ano", ano).setParameter("mes", mes).executeUpdate();
	}
	
 	public List<Custo> custos(int ano){
		List<Custo> custos = new ArrayList<Custo>();
		String sql = "select year(f.data) as ano,MONTH(f.data) as mes, (select sum(saida) from fluxodiario x where tipo='f' and year(f.data)=year(x.data) and MONTH(f.data)=MONTH(x.data)) as fixo, "
				+ "(select sum(saida) from fluxodiario w where tipo='v' and year(f.data)=year(w.data) and MONTH(f.data)=MONTH(w.data))  as variavel, "
				+ "(select sum(saida) from fluxodiario z where tipo='i' and year(f.data)=year(z.data) and MONTH(f.data)=MONTH(z.data)) as investimento "
				+ "from fluxodiario f  where year(f.data)= :ano "
				+ "group by year(f.data),MONTH(f.data) "
				+ "order  by year(f.data),MONTH(f.data)";

		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", ano).getResultList();
				
		for(Object[] o:list){
			Custo c = new Custo();
			c.setAno((int) o[0]);
			c.setMes((int) o[1]);
			
			
			if(o[2]==null){
				c.setFixo(new Double("0.0"));
			}else{
				c.setFixo(new Double(o[2].toString()));
			}	
			
			if(o[3]==null){
				c.setVariavel(new Double("0.0"));
			}else{
				c.setVariavel(new Double(o[3].toString()));
			}	
			
			if(o[4]==null){
				c.setInvestimento(new Double("0.0"));
			}else{
				c.setInvestimento(new Double(o[4].toString()));
			}
			custos.add(c);
		}		
		return custos;	
	}
	
	@SuppressWarnings("static-access")
	public List<FluxoDiario> lerArquivo(String excelFilePath, CampoPlanilha c) throws IOException{
		
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	    @SuppressWarnings("unused")
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	    
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	     
	    List<FluxoDiario> fluxos = new ArrayList<FluxoDiario>();
	    List<Integer> linhas = new ArrayList<Integer>();
	    
	    int itipo = numeroDaColuna(c.getCampoTipo());
	    
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();            
	        @SuppressWarnings("unused")
			String data="";
	        if(nextRow.getCell(itipo)!=null && nextRow.getCell(itipo).getCellType()!=nextRow.getCell(itipo).CELL_TYPE_BLANK ){
	        	String tipo=nextRow.getCell(itipo).getStringCellValue();
	        	
	        	if(tipo.equals("F")|| tipo.equals("I")|| tipo.equals("V")){
	        		linhas.add(nextRow.getRowNum()+1);
	        	}
	        }else{
	        	data="";
	        }
	    }
		
	   for(Integer i:linhas){
		   Date data=null;
		   String doc="";
		   String his="";
		   
		   Double ent=null;
		   Double sai=null;
		   Double sal=null;
		   
		   String tipo="";
		   
	       CellReference dataRef = new CellReference(c.getCampoData()+i); 
	       Row dataRow = firstSheet.getRow(dataRef.getRow());
	       Cell dataCell = dataRow.getCell(dataRef.getCol()); 
	       data=dataCell.getDateCellValue();
	       
	       CellReference docRef = new CellReference(c.getCampoDoc()+i); 
	       Row docRow = firstSheet.getRow(docRef.getRow());
	       Cell docCell = docRow.getCell(docRef.getCol());          
	       doc=getCellValue(docCell)+"";
	
	       CellReference hisRef = new CellReference(c.getCampohis()+i); 
	       Row hisRow = firstSheet.getRow(hisRef.getRow());
	       Cell hisCell = hisRow.getCell(hisRef.getCol()); 
	       his=hisCell.getStringCellValue()+""; 
	       
	       CellReference entRef = new CellReference(c.getCampoEnt()+i); 
	       Row entRow = firstSheet.getRow(entRef.getRow());
	       Cell entCell = entRow.getCell(entRef.getCol()); 

	       if((Double) getCellValue(entCell)!=null)
	    	   ent=(Double) getCellValue(entCell);
	       else
	    	   ent=new Double(0);
	       
	       CellReference saiRef = new CellReference(c.getCampoSai()+i); 
	       Row saiRow = firstSheet.getRow(saiRef.getRow());
	       Cell saiCell = saiRow.getCell(saiRef.getCol());
	       if(evaluator.evaluate(saiCell)!=null)
	    	   sai=(Double) evaluator.evaluate(saiCell).getNumberValue();
	       else
	    	   sai=new Double(0);
	       
	  /*    CellReference salRef = new CellReference(c.getCampoSal()+i); 
	       Row salRow = firstSheet.getRow(salRef.getRow());
	       Cell salCell = salRow.getCell(salRef.getCol()); 
	       if((Double) getCellValue(salCell)!=null)
	    	   sal=(Double) getCellValue(salCell); 
	       else
	    	   sal=new Double(0);
	*/
	       CellReference tipoRef = new CellReference(c.getCampoTipo()+i); 
	       Row tipoRow = firstSheet.getRow(tipoRef.getRow());
	       Cell tipoCell = tipoRow.getCell(tipoRef.getCol()); 
	   	   tipo=(String) getCellValue(tipoCell);            
	
	   	   FluxoDiario f =new FluxoDiario();
	   	   f.setData(data);
	   	   f.setDocumento(doc);
	   	   f.setEntrada(ent);
	   	   f.setHistorico(his);
	   	   f.setSaida(sai);
	   	   f.setSaldo(sal);
	   	   f.setTipo(tipo);
	   	   fluxos.add(f);
	   }
	    workbook.close();
	    inputStream.close();
		return fluxos;
	 }

	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue(); 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    case Cell.CELL_TYPE_FORMULA:
	    	return cell.getCellFormula();
	    }	 
	    return null;
	}

	private int numeroDaColuna(String s){
        switch (s) {
	        case "A":
	        	return 0;
	        case "B":
	        	return 1;
	        case "C":
	        	return 2;
	        case "D":
	        	return 3;
	        case "E":
	        	return 4;
	        case "F":
	        	return 5;	        	
	        case "G":
	        	return 6;
	        case "H":
	        	return 7;	        	
	        case "I":
	        	return 8;
	        case "J":
	        	return 9;	
	        case "K":
	        	return 10;	        	
	        case "L":
	        	return 11;
	        case "M":
	        	return 12;	        	
	        case "N":
	        	return 13;
	        case "O":
	        	return 14;
	        case "P":
	        	return 15;
	        case "Q":
	        	return 16;	        	
	        case "R":
	        	return 17;
	        case "S":
	        	return 18;
	        case "T":
	        	return 19;
	        case "U":
	        	return 20;	        	
	        case "V":
	        	return 21;
	        case "W":
	        	return 22;	        	
	        case "X":
	        	return 23;
	        case "Y":
	        	return 24;	        	
	        case "Z":
	        	return 25;   
	        default: return 999;
	        
        }
	}
	
	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}


	
}
