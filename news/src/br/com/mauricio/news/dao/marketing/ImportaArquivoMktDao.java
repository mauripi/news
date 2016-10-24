package br.com.mauricio.news.dao.marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import br.com.mauricio.news.model.marketing.MidiaMais;
import br.com.mauricio.news.model.marketing.Ibope;

/**
*
* @author MAURICIO CRUZ
*/
public class ImportaArquivoMktDao {

	public List<MidiaMais> importarMidiaMais(String excelFilePath) throws EncryptedDocumentException, InvalidFormatException, IOException{
		List<MidiaMais> list = new ArrayList<MidiaMais>();		
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        DataFormatter objDefaultFormat = new DataFormatter();
        FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        
       for(int i=8;i<firstSheet.getLastRowNum();i++){
    	   MidiaMais m = new MidiaMais();
    	   CellReference aRef = new CellReference("A"+i); //cliente
           Row aRow = firstSheet.getRow(aRef.getRow());
           Cell aCell = aRow.getCell(aRef.getCol()); 
 
           if(!getCellValue(aCell).equals("CLIENTE")&&!getCellValue(aCell).toString().contains("TOTAL DE ")){
        	   m.setCliente(getCellValue(aCell)+"");

	           CellReference bRef = new CellReference("B"+i); //titulo
	           Row bRow = firstSheet.getRow(bRef.getRow());
	           Cell bCell = bRow.getCell(bRef.getCol());          
	           m.setTitulo(getCellValue(bCell)+""); 

	           CellReference cRef = new CellReference("C"+i);  //programa
	           Row cRow = firstSheet.getRow(cRef.getRow());
	           Cell cCell = cRow.getCell(cRef.getCol()); 
	           m.setPrograma(getCellValue(cCell)+"");  

	           CellReference dRef = new CellReference("D"+i); // praca
	           Row dRow = firstSheet.getRow(dRef.getRow());
	           Cell dCell = dRow.getCell(dRef.getCol()); 
	           m.setPraca(getCellValue(dCell)+""); 
	           
	           CellReference eRef = new CellReference("E"+i); //duracao
	           Row eRow = firstSheet.getRow(eRef.getRow());
	           Cell eCell = eRow.getCell(eRef.getCol()); 
        	   m.setDuracao(getCellValue(eCell)+""); 
	
	           CellReference fRef = new CellReference("F"+i); //data
	           Row fRow = firstSheet.getRow(fRef.getRow());
	           Cell fCell = fRow.getCell(fRef.getCol()); 
	           m.setData(fCell.getDateCellValue());          
	
	           CellReference gRef = new CellReference("G"+i); //horario
	           Row gRow = firstSheet.getRow(gRef.getRow());
	           Cell gCell = gRow.getCell(gRef.getCol()); 
	           String sTmp = objDefaultFormat.formatCellValue(gCell,objFormulaEvaluator);
	           m.setHorario(sTmp);            

	           CellReference hRef = new CellReference("H"+i); //situação
	           Row hRow = firstSheet.getRow(hRef.getRow());
	           Cell hCell = hRow.getCell(hRef.getCol()); 
	           m.setSituacao(getCellValue(hCell)+"");  
	  
	           CellReference iRef = new CellReference("I"+i); //audiencia
	           Row iRow = firstSheet.getRow(iRef.getRow());
	           Cell iCell = iRow.getCell(iRef.getCol()); 
	           String sTmp2 = iCell.getNumericCellValue()+"";	
	           if(isDoubleTry(sTmp2))
	        	   m.setAudiencia(Double.parseDouble(sTmp2));            
	           else
	               m.setAudiencia(new Double("0.0"));              
	           list.add(m);
           }
       }	
		return list;		
	}
	
	public List<Ibope> importarIbope(String excelFilePath) throws EncryptedDocumentException, InvalidFormatException, IOException{
		List<Ibope> list = new ArrayList<Ibope>();		
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        Workbook workbook = WorkbookFactory.create(inputStream);
        DataFormatter objDefaultFormat = new DataFormatter();
        FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        Sheet firstSheet = workbook.getSheetAt(0);
 
        for(int i=3;i<firstSheet.getLastRowNum();i++){
     	   Ibope m = new Ibope();
     	   CellReference aRef = new CellReference("A"+i); 
            Row aRow = firstSheet.getRow(aRef.getRow());
            if(aRow!=null){
 	           Cell aCell = aRow.getCell(aRef.getCol()); 
 	           m.setId(getCellValue(aCell)+""); 
 	            
 	           CellReference bRef = new CellReference("B"+i); 
 	           Row bRow = firstSheet.getRow(bRef.getRow());
 	           Cell bCell = bRow.getCell(bRef.getCol());  
 	           if(bCell!=null){
 		           m.setData(bCell.getDateCellValue()); 
 		
 	           CellReference cRef = new CellReference("C"+i); 
 	           Row cRow = firstSheet.getRow(cRef.getRow());
 	           Cell cCell = cRow.getCell(cRef.getCol()); 
 	           m.setPrograma(getCellValue(cCell)+"");  
 	           
 	           CellReference dRef = new CellReference("D"+i); 
 	           Row dRow = firstSheet.getRow(dRef.getRow());
 	           Cell dCell = dRow.getCell(dRef.getCol()); 
 	           m.setWmask(getCellValue(dCell)+"");   
 	           
 	           CellReference eRef = new CellReference("E"+i); 
 	           Row eRow = firstSheet.getRow(eRef.getRow());
 	           Cell eCell = eRow.getCell(eRef.getCol()); 
 	           String sTmp = objDefaultFormat.formatCellValue(eCell,objFormulaEvaluator);
 	           m.setInicio(sTmp.replace("\\", ""));        

 	           CellReference fRef = new CellReference("F"+i); 
 	           Row fRow = firstSheet.getRow(fRef.getRow());
 	           Cell fCell = fRow.getCell(fRef.getCol()); 
 	           m.setCodigo(getCellValue(fCell)+""); 
 	           
 	           CellReference gRef = new CellReference("G"+i); 
 	           Row gRow = firstSheet.getRow(gRef.getRow());
 	           Cell gCell = gRow.getCell(gRef.getCol()); 
 	           m.setDratper(getCellValue(gCell)+""); 
  	 
 	           CellReference hRef = new CellReference("H"+i); 
 	           Row hRow = firstSheet.getRow(hRef.getRow());
 	           Cell hCell = hRow.getCell(hRef.getCol()); 
 	           m.setDratabs(getCellValue(hCell)+"");   
 	  
 	           CellReference iRef = new CellReference("I"+i); 
 	           Row iRow = firstSheet.getRow(iRef.getRow());
 	           Cell iCell = iRow.getCell(iRef.getCol()); 
 	           m.setDcovper(getCellValue(iCell)+"");          
 	
 	           CellReference jRef = new CellReference("J"+i); 
 	           Row jRow = firstSheet.getRow(jRef.getRow());
 	           Cell jCell = jRow.getCell(jRef.getCol()); 
 	           m.setDcovabs(getCellValue(jCell)+"");            
 			 
 	           CellReference kRef = new CellReference("K"+i); 
 	           Row kRow = firstSheet.getRow(kRef.getRow());
 	           Cell kCell = kRow.getCell(kRef.getCol()); 
 	           m.setIratper(getCellValue(kCell)+"");              
 	           
 	           CellReference lRef = new CellReference("L"+i); 
 	           Row lRow = firstSheet.getRow(lRef.getRow());
 	           Cell lCell = lRow.getCell(lRef.getCol()); 
 	           m.setIratabs(getCellValue(lCell)+"");            
 			 
 	           CellReference mRef = new CellReference("M"+i); 
 	           Row mRow = firstSheet.getRow(mRef.getRow());
 	           Cell mCell = mRow.getCell(mRef.getCol()); 
 	           m.setIcovper(getCellValue(mCell)+"");              
 	           
 	           CellReference nRef = new CellReference("N"+i); 
 	           Row nRow = firstSheet.getRow(nRef.getRow());
 	           Cell nCell = nRow.getCell(nRef.getCol()); 
 	           m.setIcovabs(getCellValue(nCell)+"");              
 	           	           
 	           if(isNumeroTry(m.getId()))
 	        	   list.add(m);	           
 	           
 	           }
           }
       }
	return list;	
	}
	
	private boolean isNumeroTry(String texto) {
		try {
			Integer.parseInt(texto);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}
	
	private boolean isDoubleTry(String texto) {
		try {
			Double.parseDouble(texto);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}
	
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }	 
	    return null;
	}
}
