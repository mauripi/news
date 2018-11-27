package br.com.mauricio.news.ln.financeiro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.mauricio.news.dao.financeiro.ProjetadoRealizadoDao;
import br.com.mauricio.news.model.financeiro.Previsto;
import br.com.mauricio.news.model.financeiro.Realizado;
import br.com.mauricio.news.util.SaveFile;

public class ProjetadoRealizadoLN implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ProjetadoRealizadoDao dao;
	private static final String FILE_NAME = "C://ARQUIVOS_INTRANET//FINANCEIRO//ProjetadoRealizado.xlsx";
	private static Map<Integer,String> cls = new HashMap<Integer,String>();
	private CellStyle cellStyle;
	private CreationHelper createHelper;
	private int totreg = 0;
	private int posicao = 0;
	
	
	public void gerarArquivoProjetadoRealizado(Date d1,Date d2) throws IOException{
		dao = new ProjetadoRealizadoDao();
		List<Previsto> previstos = dao.buscarPrevisto(d1, d2);
		List<Realizado> realizados = dao.buscarRealizado(d1, d2);

		cls = dao.buscarClassificacao();
        totreg = previstos.size();
        if(realizados.size()>totreg) totreg = realizados.size();
        criaPlanilha(previstos,realizados);
        retornarArquivo();
	}
	
	private void criaPlanilha(List<Previsto> previstos,List<Realizado> realizados){
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        cellStyle = workbook.createCellStyle();
        createHelper = workbook.getCreationHelper();

        cls.forEach((k,v) -> criarSheet(workbook,v));
        posicao =0;
        cls.forEach((k,v) -> {  posicao++;
        						criaCabecalho(k,workbook.getSheetAt(posicao-1),previstos.stream().filter(p -> p.getCtared().equals(k)).collect(Collectors.toList()),realizados.stream().filter(p -> p.getCtared()==k).collect(Collectors.toList()),posicao);
        					});               
        posicao =0;
        cls.forEach((k,v) -> {
        	 					posicao++;
        						criaPrevisto(workbook.getSheetAt(posicao-1),previstos.stream().filter(p -> p.getCtared().equals(k)).collect(Collectors.toList()));
        					});
        posicao =0;
         
        cls.forEach((k,v) -> {
        						posicao++;
        						criaRealizado(workbook.getSheetAt(posicao-1),realizados.stream().filter(p -> p.getCtared().equals(k)).collect(Collectors.toList()));
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
	}
	
	private void criarSheet(XSSFWorkbook workbook, String v) {
		XSSFSheet sheet = workbook.createSheet(v);		
        for(int i=1;i<totreg;i++)
        	sheet.createRow(i);        
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
		if(posicao < 101){			
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
	
	
	/*
	private void criaCabecalho(Integer k, XSSFSheet sheet,List<Previsto> previstos, List<Realizado> realizados){
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
		if(k < 9){			
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

*/
	private void criaPrevisto(XSSFSheet sheet,List<Previsto> previstos){
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

	private void criaRealizado(XSSFSheet sheet,List<Realizado> realizados){
		int rowNum = 12;
		Row row = sheet.getRow(rowNum++);	

		if(realizados.size()>0){
			int c = realizados.get(0).getCtared();
			
			if(c < 101){
				
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

	public void retornarArquivo() throws IOException{
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();	
		File folder = new File(sContext.getRealPath("/resource/financ"));
        if (!folder.exists()) folder.mkdirs();
		String destino= sContext.getRealPath("/resource/financ") + File.separator + "ProjetadoRealizado.xlsx";
        File origem = new File(FILE_NAME);
        SaveFile.criaArquivo(origem, destino);
	}
}
