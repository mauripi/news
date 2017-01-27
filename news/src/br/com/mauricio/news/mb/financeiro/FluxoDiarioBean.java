package br.com.mauricio.news.mb.financeiro;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;





import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.mauricio.news.ln.financeiro.FluxoDiarioLN;
import br.com.mauricio.news.model.financeiro.CampoPlanilha;
import br.com.mauricio.news.model.financeiro.Custo;
import br.com.mauricio.news.model.financeiro.FluxoDiario;

@ManagedBean(name="fluxodiarioMB")
@SessionScoped
public class FluxoDiarioBean implements Serializable {

	private static final long serialVersionUID = -5434138924149662559L;
	private Integer mes;
	private Integer ano=2017;
	private String msg;
	private String descricaoTipo;
	private String nomeArquivo;
	private InputStream is;
	private List<Custo> custos = new ArrayList<Custo>();
	private Custo custoSelecionado = new Custo();
	private LineChartModel areaModel;
	private List<FluxoDiario> detalhes = new ArrayList<FluxoDiario>();
	private List<String> campos = new ArrayList<String>();
	private String campoData="";
	private String campoDoc="";
	private String campohis="";
	private String campoEnt="";
	private String campoSai="";
	private String campoSal="";
	private String campoTipo="";	
	
	@PostConstruct
	public void init(){
		custos = new ArrayList<Custo>();
		criarGrafico();
		carregaListaDeCampos();
	}
		
	private void carregaListaDeCampos() {
		campos = new ArrayList<String>();
		campos.add("A");
		campos.add("B");
		campos.add("C");
		campos.add("D");
		campos.add("E");
		campos.add("F");
		campos.add("G");
		campos.add("H");
		campos.add("I");		
		campos.add("J");
		campos.add("K");
		campos.add("L");
		campos.add("M");
		campos.add("N");
		campos.add("O");
		campos.add("P");
		campos.add("Q");
		campos.add("R");
		campos.add("S");		
		campos.add("T");
		campos.add("V");
		campos.add("W");		
		campos.add("X");
		campos.add("Y");
		campos.add("Z");		
	}

	public void removeCampoJaSelecionado(){
		carregaListaDeCampos();		
		if(campoData!=null||!campoData.equals(""))
			campos.remove(campoData);		
		if(campoDoc!=null||!campoDoc.equals(""))
			campos.remove(campoDoc);		
		if(campohis!=null||!campohis.equals(""))
			campos.remove(campohis);
		if(campoEnt!=null||!campoEnt.equals(""))
			campos.remove(campoEnt);
		if(campoSai!=null||!campoSai.equals(""))
			campos.remove(campoSai);		
		if(campoSal!=null||!campoSal.equals(""))
			campos.remove(campoSal);			
		if(campoTipo!=null||!campoTipo.equals(""))
			campos.remove(campoTipo);			
		
	}
	
 	public String nomeMes(int i){
		String nome="";
	       switch (i) {
	           case 1:
	        	   nome= "Janeiro";
	               break;
	           case 2:
	        	   nome= "Fevereiro";
	               break;
	           case 3:
	        	   nome= "Março";
	               break;
	           case 4:
	        	   nome= "Abril";
	               break;
	           case 5:
	        	   nome= "Maio"; 
	               break;
	           case 6:
	        	   nome= "Junho";
	               break;
	            case 7:
	               nome= "Julho";
	               break;
	            case 8:
	            	nome= "Agosto";
	                break;
	            case 9:
	            	nome= "Setembro";
	                break;
	            case 10:
	            	nome= "Outubro";
	                break;
	            case 11:
	            	 nome= "Novembro";
	                 break;
	            case 12:
	            	 nome= "Dezembro";
	                 break; 
	       }
		return nome;	       
    }
	
    public void exibir(){
    	FluxoDiarioLN ln = new FluxoDiarioLN();
    	custos = ln.getCustos(ano);
    	criarGrafico();
    }
    
    public void celulaSelecionada(int m, String tipo){
    	FluxoDiarioLN ln = new FluxoDiarioLN();
    	detalhes= ln.listaPorMesAnoTipo(m,ano,tipo);
    	if(tipo.equals("f"))
    		descricaoTipo = "Custo Fixo - "+nomeMes(m) + "/"+ano;
    	if(tipo.equals("v"))
    		descricaoTipo = "Custo Variável - "+nomeMes(m) + "/"+ano; 
    	if(tipo.equals("i"))
    		descricaoTipo = "Custo com Investimento - "+nomeMes(m) + "/"+ano;    	
    }    
            
    private void criarGrafico() {
        areaModel = new LineChartModel();
        
        LineChartSeries investimentos = new LineChartSeries();
        //investimentos.setFill(true);
        investimentos.setLabel("Investimentos");
        
        LineChartSeries fixos = new LineChartSeries();
       // fixos.setFill(true);
        fixos.setLabel("Fixos");
        
        LineChartSeries variaveis = new LineChartSeries();
        //variaveis.setFill(true);
        variaveis.setLabel("Variáveis");
        if(custos.size()>0){
 	        for(Custo c:custos){
	        	fixos.set(nomeMes(c.getMes()), Math.round(c.getFixo()));
	        	variaveis.set(nomeMes(c.getMes()), Math.round(c.getVariavel()));
	        	investimentos.set(nomeMes(c.getMes()), Math.round(c.getInvestimento()));
	        }
        }else{
        	fixos.set(0,0);
        	variaveis.set(0,0);
        	investimentos.set(0,0);
        }
        areaModel.addSeries(fixos);
        areaModel.addSeries(variaveis);
        areaModel.addSeries(investimentos);
        
        areaModel.setTitle("Custos Mensais");
        areaModel.setLegendPosition("ne");
       // areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);		
               
        Axis xAxis = new CategoryAxis("Mês");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("Valor");
        yAxis.setMin(0);
        yAxis.setMax(5000000);
	}

	public void handleFileUpload(FileUploadEvent event) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        nomeArquivo =  fmt.format(new Date())+"_"+event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
		} catch (IOException e) {
			msg = "Ocorreu erro ao importar arquivo.";
			System.out.println("FluxoDiarioBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    }
    
    public void importar(){
    	CampoPlanilha c = carregaCampos();
    	if(is!=null){
	    	if(mes!=0&&ano!=0){
	    		FluxoDiarioLN ln = new FluxoDiarioLN();    
		    	msg = ln.recebeArquivoUpload(is,nomeArquivo);
		    	if(msg!=""){
		    		msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
		    		mensagens();
		    	}else{
		    		msg = ln.importarArquivoBD(nomeArquivo,c,mes,ano);
		        	if(msg!="")
		        		mensagens();
		        	limpaCampos();
		    	}
	    	}else{
	    		msg="Informe todos os campos!";
	    		mensagens();
	    	}
    	}else{
    		msg="Selecione o arquivo a ser importado!";
    		mensagens();
    	}
    }
    
    private void limpaCampos(){
    	campoData="";
    	campoDoc="";
    	campohis="";
    	campoEnt="";
    	campoSai="";
    	campoSal="";
    	campoTipo="";	
    }

    private CampoPlanilha carregaCampos() {
    	CampoPlanilha c = new CampoPlanilha();
    	c.setCampoData(campoData);
    	c.setCampoDoc(campoDoc);
    	c.setCampoEnt(campoEnt);
    	c.setCampohis(campohis);
    	c.setCampoSai(campoSai);
    	c.setCampoSal(campoSal);
    	c.setCampoTipo(campoTipo);    	
		return c;
	}

	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}       
    
	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Custo> getCustos() {
		return custos;
	}

	public void setCustos(List<Custo> custos) {
		this.custos = custos;
	}

	public Custo getCustoSelecionado() {
		return custoSelecionado;
	}

	public void setCustoSelecionado(Custo custoSelecionado) {
		this.custoSelecionado = custoSelecionado;
	}
    public LineChartModel getAreaModel() {
        if (areaModel == null) {
        	areaModel = new LineChartModel();
            }
        return areaModel;
    }

	public List<FluxoDiario> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(List<FluxoDiario> detalhes) {
		this.detalhes = detalhes;
	}

	public String getDescricaoTipo() {
		return descricaoTipo;
	}

	public void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}

	public List<String> getCampos() {
		return campos;
	}

	public void setCampos(List<String> campos) {
		this.campos = campos;
	}

	public String getCampoData() {
		return campoData;
	}

	public void setCampoData(String campoData) {
		this.campoData = campoData;
	}

	public String getCampoDoc() {
		return campoDoc;
	}

	public void setCampoDoc(String campoDoc) {
		this.campoDoc = campoDoc;
	}

	public String getCampohis() {
		return campohis;
	}

	public void setCampohis(String campohis) {
		this.campohis = campohis;
	}

	public String getCampoEnt() {
		return campoEnt;
	}

	public void setCampoEnt(String campoEnt) {
		this.campoEnt = campoEnt;
	}

	public String getCampoSai() {
		return campoSai;
	}

	public void setCampoSai(String campoSai) {
		this.campoSai = campoSai;
	}

	public String getCampoSal() {
		return campoSal;
	}

	public void setCampoSal(String campoSal) {
		this.campoSal = campoSal;
	}

	public String getCampoTipo() {
		return campoTipo;
	}

	public void setCampoTipo(String campoTipo) {
		this.campoTipo = campoTipo;
	}
    
}
