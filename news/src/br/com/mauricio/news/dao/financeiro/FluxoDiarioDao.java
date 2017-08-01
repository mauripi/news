package br.com.mauricio.news.dao.financeiro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

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

	public List<FluxoDiario> lerArquivo(String excelFilePath, CampoPlanilha c) throws IOException{
		
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

	    Workbook workbook = new XSSFWorkbook(inputStream);
	    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	    
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	    
	    int itipo = numeroDaColuna(c.getCampoTipo());
	    int dat = numeroDaColuna(c.getCampoData());
	    int doc = numeroDaColuna(c.getCampoDoc());
	    int his = numeroDaColuna(c.getCampohis());		   
	    int ent = numeroDaColuna(c.getCampoEnt());
	    int sai = numeroDaColuna(c.getCampoSai());
	    int sal = numeroDaColuna(c.getCampoSal());
		List<FluxoDiario> fs = new ArrayList<FluxoDiario>();	    

	    while (iterator.hasNext()) {

			Row nextRow = iterator.next();
			
			if(nextRow.getCell(itipo)!=null && nextRow.getCell(itipo).toString().length()>0){
				FluxoDiario f = new FluxoDiario();

				Object oDat = nextRow.getCell(dat);
				if(oDat!=null){
			       	if(oDat.toString().length()!=0){
			       		try{
			       			f.setData(nextRow.getCell(dat).getDateCellValue());
			       		}catch(Exception e){
			       			f.setData(null);
			       		}
			       	}
				}
	
				if(nextRow.getCell(doc)!=null)
					f.setDocumento(nextRow.getCell(doc).toString());
				if(nextRow.getCell(his)!=null)
					f.setHistorico(nextRow.getCell(his).toString());
				if(nextRow.getCell(itipo)!=null)
					f.setTipo(nextRow.getCell(itipo).toString().replaceAll(" ","").replaceAll("  ", ""));
		        
				Object oEnt = nextRow.getCell(ent);
				if(oEnt!=null){
			       	if(oEnt.toString().length()>0){
			       		Cell entCell =nextRow.getCell(ent); 	       		
			       		f.setEntrada(evaluator.evaluate(entCell).getNumberValue());
			       	}else{
			       		f.setEntrada(new Double(0));
			       	}
				}
				Object oSai = nextRow.getCell(sai);
				if(oSai!=null){
			       	if(oSai.toString().length()>0){
			       		Cell saiCell =nextRow.getCell(sai); 
			       		f.setSaida(evaluator.evaluate(saiCell).getNumberValue());
			       	}else{
			       		f.setSaida(new Double(0));
			       	}				
				}
				Object oSal = nextRow.getCell(sal);
				if(oSal!=null){
			       	if(oSal.toString().length()>0){
			       		try{
				       		Cell salCell =nextRow.getCell(sal); 
				       		f.setSaldo(evaluator.evaluate(salCell).getNumberValue());
			       		}catch(Exception e){
			       			f.setSaldo(new Double(0));
			       		}
			       	}else{
			       		f.setSaldo(new Double(0));
			       	}					
				}
				
				fs.add(f);
			}
	    }
	    workbook.close();
	    inputStream.close();
	    
		return fs;
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
