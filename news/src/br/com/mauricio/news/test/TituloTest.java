package br.com.mauricio.news.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import br.com.mauricio.news.dao.financeiro.ProjetadoRealizadoDao;
import br.com.mauricio.news.model.financeiro.Previsto;
import br.com.mauricio.news.model.financeiro.Realizado;

public class TituloTest {
	
	private static final String FILE_NAME = "C://ARQUIVOS_INTRANET//FINANCEIRO//MyFirstExcel.xlsx";
	private static Map<Integer,String> cls = new HashMap<Integer,String>();
	static CellStyle cellStyle;
	static CreationHelper createHelper;
	static int totreg = 0;
	static int posicao =0;
	
	public static void main(String[] args) throws ParseException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date d1=null;
		Date d2=null;
		d1 = df.parse("01/10/2018");
		d2 = df.parse("30/11/2018");
	
		ProjetadoRealizadoDao dao = new ProjetadoRealizadoDao();
		List<Previsto> previstos = dao.buscarPrevisto(d1, d2);
		List<Realizado> realizados = dao.buscarRealizado(d1, d2);
		cls = dao.buscarClassificacao();
		
	
		
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        cellStyle = workbook.createCellStyle();
        createHelper = workbook.getCreationHelper();

        totreg = previstos.size();
        if(realizados.size()>totreg)
        	totreg = realizados.size();
        
        /*     cls.forEach((k,v) -> criarSheet(workbook,v));
       System.out.println("Total registros: "+totreg);
        
        for (Map.Entry<Integer, String> entry : cls.entrySet()) {
        	posicao++;
        	System.out.println("Key : " + entry.getKey() + " value : " + entry.getValue());
        	int k =entry.getKey();
        	criaCabecalho(k,workbook.getSheetAt(posicao-1),previstos,realizados,posicao);
        	System.out.println("");
         }
        */
        //cls.forEach((k,v) -> criaCabecalho(k,workbook.getSheetAt(k),previstos.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList()),realizados.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList())));               
     /*   cls.forEach((k,v) -> criaPrevisto(workbook.getSheetAt(k),previstos.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList())));
        cls.forEach((k,v) -> criaRealizado(workbook.getSheetAt(k),realizados.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList())));
        */
        cls.forEach((k,v) -> criarSheet(workbook,v));
        posicao =0;
        cls.forEach((k,v) -> {  posicao++;
        						criaCabecalho(k,workbook.getSheetAt(posicao-1),previstos.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList()),realizados.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList()),posicao);
        					});               
        posicao =0;
        cls.forEach((k,v) -> {
        	 					posicao++;
        						criaPrevisto(workbook.getSheetAt(posicao-1),previstos.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList()));
        					});
        posicao =0;
        cls.forEach((k,v) -> {
        						posicao++;
        						criaRealizado(workbook.getSheetAt(posicao-1),realizados.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList()));
        					});
       
        
        
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");		
	}
		
	private static void criarSheet(XSSFWorkbook workbook, String v) {
		XSSFSheet sheet = workbook.createSheet(v);		
        for(int i=1;i<totreg;i++)
        	sheet.createRow(i);        
	}


	private static void criaPrevisto(XSSFSheet sheet,List<Previsto> previstos){
		int rowNum = 12;
		Row row = sheet.getRow(rowNum++);

		if(previstos.size()>0){
			for(Previsto p: previstos){
				cellStyle.getDataFormatString();				
				Cell cell = row.createCell(0);				
				cell.setCellValue("");	
				cell.setCellStyle(cellStyle);				
				cell = row.createCell(1);
				cell.setCellValue(p.getNumtit());
				cell.setCellStyle(cellStyle);				
				cell = row.createCell(3);
				cell.setCellValue(p.getNomclifor());
				cell.setCellStyle(cellStyle);				
				cell = row.createCell(4);
				cell.setCellValue(p.getObstit());
				cell.setCellStyle(cellStyle);				
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				cell = row.createCell(2);				
				cell.setCellValue(p.getVctpro());
				cell.setCellStyle(cellStyle);
				cell = row.createCell(5);
				cell.setCellValue(p.getVlrabe().doubleValue());	
				row = sheet.getRow(rowNum++);
			}
		}
	}

	private static void criaRealizado(XSSFSheet sheet,List<Realizado> realizados){
		int rowNum = 12;
		Row row = sheet.getRow(rowNum++);	
		if(realizados.size()>0){
			int c = realizados.get(0).getCtared();
			if(c < 9){
				for(Realizado r: realizados){
					cellStyle.getDataFormatString();					
					Cell cell = row.createCell(7);				
					cell.setCellValue("");	
					cell.setCellStyle(cellStyle);				
					cell = row.createCell(8);
					cell.setCellValue(r.getNumtit());
					cell.setCellStyle(cellStyle);					
					cell = row.createCell(11);
					cell.setCellValue(r.getNomclifor());
					cell.setCellStyle(cellStyle);					
					cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
					cell = row.createCell(9);				
					cell.setCellValue(r.getDatmov());
					cell.setCellStyle(cellStyle);
					cell = row.createCell(10);				
					cell.setCellValue(r.getDatmov());
					cell.setCellStyle(cellStyle);	
					cell = row.createCell(12);
					cell.setCellValue(r.getVlrliq().doubleValue());	
					row = sheet.getRow(rowNum++);	
				}
			}else{
				for(Realizado r: realizados){
					cellStyle.getDataFormatString();					
					Cell cell = row.createCell(7);				
					cell.setCellValue("");	
					cell.setCellStyle(cellStyle);					
					cell = row.createCell(8);
					cell.setCellValue(r.getNumtit());
					cell.setCellStyle(cellStyle);					
					cell = row.createCell(11);
					cell.setCellValue(r.getNomclifor());
					cell.setCellStyle(cellStyle);
					cell = row.createCell(12);
					cell.setCellValue(r.getObstit());
					cell.setCellStyle(cellStyle);					
					cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
					cell = row.createCell(9);				
					cell.setCellValue(r.getDatmov());
					cell.setCellStyle(cellStyle);
					cell = row.createCell(10);				
					cell.setCellValue(r.getDatmov());
					cell.setCellStyle(cellStyle);	
					cell = row.createCell(13);
					cell.setCellValue(r.getVlrliq().doubleValue());	
					row = sheet.getRow(rowNum++);	
				}				
			}
		}
	}

	private static void criaCabecalho(Integer k, XSSFSheet sheet,List<Previsto> previstos, List<Realizado> realizados,int posicao){

		Row row = sheet.getRow(10);

		Cell cell = row.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(10,10,0,4));
		cell.setCellValue("Total de Recebimentos Previstos - " + cls.get(k));
		row = sheet.createRow(11);
		cell = row.createCell(0);
		cell.setCellValue("Data Emissão Fatura/N.Fiscal");	
		cell = row.createCell(1);
		cell.setCellValue("Número do Documento");
		cell = row.createCell(2);			
		cell.setCellValue("Data Vencimento");			
		cell = row.createCell(3);
		cell.setCellValue("Nome do Cliente");
		cell = row.createCell(4);
		cell.setCellValue("Histórico");
		cell = row.createCell(5);
		cell.setCellValue("Valor");
		
		row = sheet.getRow(10);
		if(posicao < 9){			
			cell = row.createCell(7);
			sheet.addMergedRegion(new CellRangeAddress(10,10,7,11));
			cell.setCellValue("Total de Recebimentos Realizado  - " + cls.get(k));
			row = sheet.getRow(11);				
			cell = row.createCell(7);
			cell.setCellValue("Data Emissão Fatura/N.Fiscal");					
			cell = row.createCell(8);
			cell.setCellValue("Número do Documento");			
			cell = row.createCell(9);			
			cell.setCellValue("Data Vencimento");	
			cell = row.createCell(10);			
			cell.setCellValue("Data  Efetivo Recebimento");				
			cell = row.createCell(11);
			cell.setCellValue("Nome do Cliente");				
			cell = row.createCell(12);
			cell.setCellValue("Valor");					
		}else{
			cell = row.createCell(7);
			sheet.addMergedRegion(new CellRangeAddress(10,10,7,12));
			cell.setCellValue("Total de Pagamentos Realizado  - " + cls.get(k));
			row = sheet.getRow(11);	
			cell = row.createCell(7);
			cell.setCellValue("Data Emissão Fatura/N.Fiscal");					
			cell = row.createCell(8);
			cell.setCellValue("Número do Documento");			
			cell = row.createCell(9);			
			cell.setCellValue("Data Vencimento");	
			cell = row.createCell(10);			
			cell.setCellValue("Data Efetivo Pagto");				
			cell = row.createCell(11);
			cell.setCellValue("Nome do Cliente");
			cell = row.createCell(12);
			cell.setCellValue("Historico");				
			cell = row.createCell(13);
			cell.setCellValue("Valor");					
		}
		
	}
}
