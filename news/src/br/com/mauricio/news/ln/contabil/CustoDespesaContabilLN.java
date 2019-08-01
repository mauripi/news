package br.com.mauricio.news.ln.contabil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.mauricio.news.dao.contabil.CustoContabilDao;
import br.com.mauricio.news.model.contabil.CustoContabil;
import br.com.mauricio.news.model.contabil.CustoDespesaContabil;
import br.com.mauricio.news.util.SaveFile;


public class CustoDespesaContabilLN implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private CustoContabilDao dao;
	private CellStyle cellStyle;
	@SuppressWarnings("unused")
	private CreationHelper createHelper;
	private static final String FILE_NAME = "C:\\windows\\temp\\contabil\\custodespesa.xlsx";
	private List<CustoDespesaContabil> consolidado = new ArrayList<CustoDespesaContabil>();
	private List<CustoDespesaContabil> administrativo = new ArrayList<CustoDespesaContabil>();
	private List<CustoDespesaContabil> jornalismo = new ArrayList<CustoDespesaContabil>();
	private List<CustoDespesaContabil> tecnica = new ArrayList<CustoDespesaContabil>();
	private List<CustoDespesaContabil> marketing = new ArrayList<CustoDespesaContabil>();
	private List<CustoDespesaContabil> comercial = new ArrayList<CustoDespesaContabil>();
	private List<CustoDespesaContabil> operacao = new ArrayList<CustoDespesaContabil>();
	private List<CustoDespesaContabil> receita = new ArrayList<CustoDespesaContabil>();
	private List<CustoDespesaContabil> contabilfin = new ArrayList<CustoDespesaContabil>();
	private static Map<Integer,String> abas = new HashMap<Integer,String>();
	private Set<Integer> contasSig = new HashSet<Integer>();	
	private Set<CustoDespesaContabil> administrativoSet = new HashSet<CustoDespesaContabil>();
	private Set<CustoDespesaContabil> jornalismoSet = new HashSet<CustoDespesaContabil>();
	private Set<CustoDespesaContabil> tecnicaSet = new HashSet<CustoDespesaContabil>();
	private Set<CustoDespesaContabil> marketingSet = new HashSet<CustoDespesaContabil>();
	private Set<CustoDespesaContabil> comercialSet = new HashSet<CustoDespesaContabil>();
	private Set<CustoDespesaContabil> operacaoSet = new HashSet<CustoDespesaContabil>();
	private Set<CustoDespesaContabil> receitaSet = new HashSet<CustoDespesaContabil>();
	private Set<CustoDespesaContabil> contabilfinSet = new HashSet<CustoDespesaContabil>();
	private Set<CustoDespesaContabil> consolidadoSet = new HashSet<CustoDespesaContabil>();
	private int posicao = 0;
	private int totreg = 0;
	
	public void gerarPlanilha(Integer ano) throws IOException{
		dao = new CustoContabilDao();
		List<CustoContabil> lista = dao.buscarERP(ano);
		carregaContasSig(lista);
		separaPorCustoSig(lista);
		criaListas();
		ordenarLista();		
		somaContasDosGrupos(lista);		
		criaPlanilha();
		retornarArquivo();
	}
	
	private void criaListas() {
		consolidado.addAll(consolidadoSet);
		administrativo.addAll(administrativoSet);
		jornalismo.addAll(jornalismoSet);
		tecnica.addAll(tecnicaSet);
		marketing.addAll(marketingSet);
		comercial.addAll(comercialSet);
		operacao.addAll(operacaoSet);
		receita.addAll(receitaSet);
		contabilfin.addAll(contabilfinSet);
		
	}

	private void criaPlanilha(){
		preencheAsAbas();
		try {
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        abas.forEach((k,v) -> criarSheet(workbook,v));
	        cellStyle = workbook.createCellStyle();
	        createHelper = workbook.getCreationHelper();

	        posicao =0;
	        abas.forEach((k,v) -> {  
	        	posicao++;
	        	criaCabecalho(workbook.getSheetAt(posicao-1));
	        });               

	        abas.forEach((k,v) -> { 
	        	preencheValoresDasCelulas(workbook.getSheetAt(0),consolidado);
	        	preencheValoresDasCelulas(workbook.getSheetAt(1),receita);
	        	preencheValoresDasCelulas(workbook.getSheetAt(2),administrativo);
	        	preencheValoresDasCelulas(workbook.getSheetAt(3),marketing);
	        	preencheValoresDasCelulas(workbook.getSheetAt(4),comercial);
	        	preencheValoresDasCelulas(workbook.getSheetAt(5),operacao);
	        	preencheValoresDasCelulas(workbook.getSheetAt(6),tecnica);
	        	preencheValoresDasCelulas(workbook.getSheetAt(7),jornalismo);
	        	preencheValoresDasCelulas(workbook.getSheetAt(8),contabilfin);
	        });
        
            FileOutputStream outputStream = new FileOutputStream(new File(FILE_NAME));
            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void preencheValoresDasCelulas(XSSFSheet sheet,List<CustoDespesaContabil> list){
		int rowNum = 1;
		Row row = sheet.getRow(rowNum++);

		for(CustoDespesaContabil c: list){
			cellStyle.getDataFormatString();				
			Cell cell = row.createCell(0);				
			cell.setCellValue(c.getCtasig());	
			cell.setCellStyle(cellStyle);	
			cell = row.createCell(1);				
			cell.setCellValue(c.getDescta());	
			cell.setCellStyle(cellStyle);			
			cell = row.createCell(2);				
			cell.setCellValue(c.getJan().doubleValue());	
			cell.setCellStyle(cellStyle);
			cell = row.createCell(3);				
			cell.setCellValue(c.getFev().doubleValue());	
			cell.setCellStyle(cellStyle);
			cell = row.createCell(4);				
			cell.setCellValue(c.getMar().doubleValue());	
			cell.setCellStyle(cellStyle);			
			cell = row.createCell(5);				
			cell.setCellValue(c.getAbr().doubleValue());	
			cell.setCellStyle(cellStyle);			
			cell = row.createCell(6);				
			cell.setCellValue(c.getMai().doubleValue());	
			cell.setCellStyle(cellStyle);			
			cell = row.createCell(7);				
			cell.setCellValue(c.getJun().doubleValue());	
			cell.setCellStyle(cellStyle);			
			cell = row.createCell(8);				
			cell.setCellValue(c.getJul().doubleValue());	
			cell.setCellStyle(cellStyle);
			cell = row.createCell(9);				
			cell.setCellValue(c.getAgo().doubleValue());	
			cell.setCellStyle(cellStyle);
			cell = row.createCell(10);				
			cell.setCellValue(c.getSet().doubleValue());	
			cell.setCellStyle(cellStyle);
			cell = row.createCell(11);				
			cell.setCellValue(c.getOut().doubleValue());	
			cell.setCellStyle(cellStyle);			
			cell = row.createCell(12);				
			cell.setCellValue(c.getNov().doubleValue());	
			cell.setCellStyle(cellStyle);			
			cell = row.createCell(13);				
			cell.setCellValue(c.getDec().doubleValue());	
			cell.setCellStyle(cellStyle);			
			
			row = sheet.getRow(rowNum++);
		}
	}	
	
	private void criarSheet(XSSFWorkbook workbook, String v) {
		XSSFSheet sheet = workbook.createSheet(v);		
        for(int i=1;i<totreg;i++)
        	sheet.createRow(i);        
	}	
	
	private static void criaCabecalho(XSSFSheet sheet){
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("Conta");
		row.createCell(1).setCellValue("Descrição");
		row.createCell(2).setCellValue("Janeiro");
		row.createCell(3).setCellValue("Fevereiro");
		row.createCell(4).setCellValue("Março");
		row.createCell(5).setCellValue("Abril");
		row.createCell(6).setCellValue("Maio");
		row.createCell(7).setCellValue("Junho");
		row.createCell(8).setCellValue("Julho");
		row.createCell(9).setCellValue("Agosto");
		row.createCell(10).setCellValue("Setembro");
		row.createCell(11).setCellValue("Outubro");
		row.createCell(12).setCellValue("Novembro");
		row.createCell(13).setCellValue("Dezembro");
		row.createCell(14).setCellValue("Acumulado");
	}
	
	private void preencheAsAbas() {
 		abas.put(1,"Consolidado");
 		abas.put(2,"Receitas");
 		abas.put(3,"Administrativo");
 		abas.put(4,"Marketing");
 		abas.put(5,"Comercial");
 		abas.put(6,"Operações");
 		abas.put(7,"Técnica");
 		abas.put(8,"Jornalismo");
 		abas.put(9,"Contábil-Financeiro");
 		totreg = consolidado.size()+50;
	}

	private void somaContasDosGrupos(List<CustoContabil> list){
		list.forEach(c -> {
			somar(consolidado,c);
			if(c.getCtasig()>=1000 && c.getCtasig()<3000){
				somar(receita, c);
			}else{
				if((c.getCtasig()>=7000 && c.getCtasig()<8000) || (c.getCtasig()>=9000 && c.getCtasig()<10000) || (c.getCtasig()>=14000 && c.getCtasig()<16000) ){
					somar(contabilfin, c);
				}else{
					if(c.getCcusig().equals("ADMINISTRATIVO"))
						somar(administrativo, c);
					if(c.getCcusig().equals("JORNALISMO"))
						somar(jornalismo, c);			
					if(c.getCcusig().equals("TECNICA"))
						somar(tecnica, c);
					if(c.getCcusig().equals("MARKETING"))
						somar(marketing, c);
					if(c.getCcusig().equals("COMERCIAL"))
						somar(comercial, c);
					if(c.getCcusig().equals("OPERACOES"))
						somar(operacao, c);
					
				}
			}
		});	
	}
	
	private void somar(List<CustoDespesaContabil> grupo, CustoContabil custo) {		
		for(CustoDespesaContabil c : grupo)
			if(c.getCtasig().equals(custo.getCtasig())) 
				if(custo.getDebcre().equals("C")&&(c.getCtasig()>=3000&&c.getCtasig()<6000)){
					c.setJan(c.getJan().add(custo.getJan().multiply(new BigDecimal("-1"))));	
					c.setFev(c.getFev().add(custo.getFev().multiply(new BigDecimal("-1"))));
					c.setMar(c.getMar().add(custo.getMar().multiply(new BigDecimal("-1"))));	
					c.setAbr(c.getAbr().add(custo.getAbr().multiply(new BigDecimal("-1"))));
					c.setMai(c.getMai().add(custo.getMai().multiply(new BigDecimal("-1"))));	
					c.setJun(c.getJun().add(custo.getJun().multiply(new BigDecimal("-1"))));
					c.setJul(c.getJul().add(custo.getJul().multiply(new BigDecimal("-1"))));	
					c.setAgo(c.getAgo().add(custo.getAgo().multiply(new BigDecimal("-1"))));
					c.setSet(c.getSet().add(custo.getSet().multiply(new BigDecimal("-1"))));	
					c.setOut(c.getOut().add(custo.getOut().multiply(new BigDecimal("-1"))));
					c.setNov(c.getNov().add(custo.getNov().multiply(new BigDecimal("-1"))));	
					c.setDec(c.getDec().add(custo.getDec().multiply(new BigDecimal("-1"))));
				}else{
					c.setJan(c.getJan().add(custo.getJan()));	
					c.setFev(c.getFev().add(custo.getFev()));
					c.setMar(c.getMar().add(custo.getMar()));	
					c.setAbr(c.getAbr().add(custo.getAbr()));
					c.setMai(c.getMai().add(custo.getMai()));	
					c.setJun(c.getJun().add(custo.getJun()));
					c.setJul(c.getJul().add(custo.getJul()));	
					c.setAgo(c.getAgo().add(custo.getAgo()));
					c.setSet(c.getSet().add(custo.getSet()));	
					c.setOut(c.getOut().add(custo.getOut()));
					c.setNov(c.getNov().add(custo.getNov()));	
					c.setDec(c.getDec().add(custo.getDec()));
				}
	}

	private void separaPorCustoSig(List<CustoContabil> list){		
		list.forEach(c -> {
			consolidadoSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));
			if(c.getCtasig()>=1000 && c.getCtasig()<3000){
				receitaSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));
			}else{
				if((c.getCtasig()>=7000 && c.getCtasig()<8000) || (c.getCtasig()>=9000 && c.getCtasig()<10000) || (c.getCtasig()>=14000 && c.getCtasig()<16000) ){
					contabilfinSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));
				}else{
					if(c.getCcusig().equals("ADMINISTRATIVO"))
						administrativoSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));
					if(c.getCcusig().equals("JORNALISMO"))
						jornalismoSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));
					if(c.getCcusig().equals("TECNICA"))
						tecnicaSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));
					if(c.getCcusig().equals("MARKETING"))
						marketingSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));
					if(c.getCcusig().equals("COMERCIAL"))
						comercialSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));	
					if(c.getCcusig().equals("OPERACOES"))
						operacaoSet.add(new CustoDespesaContabil(c.getCtasig(),c.getDescta()));					
				}
			}
		});	
	}

	private void ordenarLista() {
		consolidado = consolidado.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));  
		administrativo = administrativo.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));
		jornalismo = jornalismo.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));
		tecnica = tecnica.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));
		marketing = marketing.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));
		comercial = comercial.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));
		operacao = operacao.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));
		receita = receita.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));
		contabilfin = contabilfin.stream().sorted(Comparator.comparing(CustoDespesaContabil::getCtasig)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	private void carregaContasSig(List<CustoContabil> list) {
		list.forEach(c->contasSig.add(c.getCtasig()));
	}

	public void retornarArquivo() throws IOException{
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();	
		File folder = new File(sContext.getRealPath("/resource/contabil"));
        if (!folder.exists()) folder.mkdirs();
		String destino= sContext.getRealPath("/resource/contabil") + File.separator + "Acompanhamento_de_Custos_Despesas.xlsx";
        File origem = new File(FILE_NAME);
        SaveFile.criaArquivo(origem, destino);
	}
}
