package br.com.mauricio.news.test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import br.com.mauricio.news.model.opec.Vetrix;

public class LerPDFTest {

	public static void main(String[] args) {
		System.out.println("Inicio");
        File file = new File("C:\\ESD\\ROTEIRO.pdf");
        List<Vetrix> lista = new ArrayList<Vetrix>();

        
        try {
            PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
            parser.parse();
            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setSuppressDuplicateOverlappingText(false);
            pdfStripper.setSortByPosition(true);
            PDDocument pdDoc = new PDDocument(cosDoc);
            
            String horaFinalDoProgramaAnterior = "";
            int programaAtual = 0;
            
            for (int i = 1; i <= pdDoc.getNumberOfPages(); i++) {
            	
                pdfStripper.setStartPage(i);
                pdfStripper.setEndPage(i);
                String parsedText = pdfStripper.getText(pdDoc);
                String linhas[] = parsedText.split("\\r?\\n");
              //  System.out.println("Página " + i + ": ");
                Vetrix vetrix = new Vetrix();
                String tipo = "";
                for(int j = 0; j < linhas.length; j++) {
                	vetrix.setId(i);
                	String linha = linhas[j].replace("","").replace("_", "").replace("''", "").replace("'", ":");
                	
                	if(j==0 && linha.contains("ROTEIRO COMERCIAL")) {
                		Pattern p = Pattern.compile("(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/[0-9]{4}");
                		Matcher m = p.matcher(linha);
                        while(m.find()) {
                        	vetrix.setDataInicio(m.group());
                            vetrix.setDataFim(m.group());
                        }
                	}
                	
                	/*
                	if(j==2 && linha.contains("Interprograma Horário no Ar:")) {
                		horaFinalDoProgramaAnterior = linha.substring(linha.indexOf(": ")+2, linha.length()); // armazena hora final do programa anterior
                		vetrix.setTipo("Programa-Inicio");
                		if(programaAtual>0){
                			updatePrograma(programaAtual,lista,horaFinalDoProgramaAnterior);
                		}
                		programaAtual = i; //armazena o programa atual
                		tipo = "Programa";
                	}
                	
                	if(tipo == "Programa") {
                		if(linha.contains("Programa: ")) {
                			vetrix.setPrograma(linha.substring(linha.indexOf(":")+2, linha.length()));
                		}
                		if(linha.contains("Horário:")) {
                			String inicio = linha.substring(linha.indexOf("=")+1, linha.length()).replace(" ", "");
                			vetrix.setHoraInicio(inicio);
                		}
                	}
                	
                	if(tipo != "Programa") {//break
	                	if(j==2 && linha.contains("Programa:") && linha.contains("Break")) { //break
	                		vetrix.setHoraInicio(linha.substring(linha.indexOf("Real: ")+6, linha.length()));
	                		vetrix.setPrograma(linha.substring(linha.indexOf("ma:")+4, linha.indexOf("Break")-4));
	                		vetrix.setTipo(linha.substring(linha.indexOf("Break")-3, linha.indexOf("Break")+5));
	                		tipo = "Break";
	                	}          
	                	
	                	if(linha.contains("Término Real  ")) {
	                		vetrix.setHoraFim(linha.substring(linha.indexOf(": ")+2, linha.length())); 
	                	}                	
                	}*/
                }
                lista.add(vetrix);
            }
        } catch (IOException e) {
            // Tratar a exceção adequadamente.
            e.printStackTrace();
        }
        FileWriter arq;
		try {
			arq = new FileWriter("C:\\ESD\\ROTEIRO.txt");
			PrintWriter gravarArq = new PrintWriter(arq);
			lista.forEach(x->gravarArq.printf(x.toString()+"%n"));
			arq.close();
			System.out.println("Fim");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updatePrograma(Integer id, List<Vetrix> list, String horaFim) {	 
	    for (Vetrix v : list) 
	        if (v.getId().equals(id)) 
	        	v.setHoraFim(horaFim);
	}
	
}

