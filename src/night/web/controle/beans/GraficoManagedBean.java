package night.web.controle.beans;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

import night.core.aplicacao.Resultado;
import night.dominio.Chart;
import night.dominio.ChartData;
import night.dominio.ChartSerie;
import night.dominio.Comanda;
import night.dominio.GraficoClientesAno;
import night.dominio.GraficoVendasAno;
import night.web.controle.web.command.ICommand;

@ManagedBean
@ViewScoped
public class GraficoManagedBean extends CustomManagedBean<Comanda> {

	private static final long serialVersionUID = 1L;
	private Chart chart;
	private Map<Integer, Double> vendasAno;
	private String dados;
	private String faturamento;
	private String clientes;
	private String meses;
	private String drilldown;
	private boolean graficoClientes;
	private boolean graficoVendas;
	private Map<Integer, Map<Integer, Double>> vendasSemana;

	public GraficoManagedBean() {
		super();

	}

	@PostConstruct
	public void init() {
		setVendasAno(new HashMap<>());
		setVendasSemana(new HashMap<>());
		setChart(new Chart());
		graficoVendas = false;
		graficoClientes = false;
		consultar();
	}

	public void consultar() {

		// Obtém o command para executar a respectiva operação

		ICommand command = commands.get(CustomManagedBean.VENDAS_ANO);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		Resultado resultado = command.execute(chart);

		Map<String, Object> mapa = new HashMap<>();
		Map<Integer, Double> mapaRes = new HashMap<>();

		mapaRes = ((GraficoVendasAno) resultado.getEntidades().get(0)).getVendas();
		Iterator it = mapaRes.entrySet().iterator();
		List<ChartData> chartDatas = new ArrayList<ChartData>();

		Chart chart = new Chart();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			ChartData chartData = new ChartData();
			String monthString;

			monthString = new DateFormatSymbols().getMonths()[Integer.parseInt(pair.getKey().toString()) - 1];

			chartData.setName(monthString);
			chartData.setDrilldown(monthString);
			chartData.setY(Double.valueOf(pair.getValue().toString()));
			chartDatas.add(chartData);

			chart.getSeries().addAll(buscarDetalhes(Integer.parseInt(pair.getKey().toString())).getSeries());

			it.remove(); // avoids a ConcurrentModificationException
		}

		Chart chartDias = new Chart();
		for (ChartSerie chartSerie : chart.getSeries()) {
			for (ChartData chartData : chartSerie.getData()) {
				int semana = Integer.valueOf(chartData.getName().replaceAll("Semana ", ""));
				chartDias.getSeries().addAll(buscarDetalhesSemana(semana).getSeries());
			}
		}

		chart.getSeries().addAll(chartDias.getSeries());

		Gson gson = new Gson();

		String dados = gson.toJson(chartDatas);
		// System.out.println(dados);

		drilldown = gson.toJson(chart.getSeries());
		// System.out.println(drilldown);

		this.dados = dados;

		graficoVendas = true;
		graficoClientes = false;

	}

	public void graficoClienteFaturamento() {

		// Obtém o command para executar a respectiva operação

		ICommand command = commands.get(CustomManagedBean.VENDAS_ANO);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		Resultado resultadoVendas = command.execute(chart);

		command = commands.get(CustomManagedBean.CLIENTES_ANO);
		Resultado resultadoClientes = command.execute(chart);

		List<ChartData> chartDatas = new ArrayList<ChartData>();

		Map<String, Object> mapa = new HashMap<>();
		Map<Integer, Double> mapaRes = new HashMap<>();
		Map<Integer, Integer> mapaCli = new HashMap<>();

		List<Object> listMapPie = new ArrayList<>();

		String[] meses;
		Object[] faturamento;
		Object[] clientes;

		StringBuilder sb = new StringBuilder();
		List<Double> listaFaturamento = new ArrayList<>();
		List<Integer> listaClientes = new ArrayList<>();

		mapaRes = ((GraficoVendasAno) resultadoVendas.getEntidades().get(0)).getVendas();
		mapaCli = ((GraficoClientesAno) resultadoClientes.getEntidades().get(0)).getClientes();
		Iterator it = mapaRes.entrySet().iterator();

		Chart chart = new Chart();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			ChartData chartData = new ChartData();

			String monthString;
			monthString = new DateFormatSymbols().getMonths()[Integer.parseInt(pair.getKey().toString()) - 1];
			if (StringUtils.isNullOrEmpty(sb.toString()))
				sb.append(monthString);
			else
				sb.append("," + monthString);

			listaFaturamento.add(Double.valueOf(pair.getValue().toString()));

			chart.getSeries().addAll(buscarDetalhes(Integer.parseInt(pair.getKey().toString())).getSeries());

			chartData.setName(monthString);
			chartData.setDrilldown(monthString);
			chartData.setY(Double.valueOf(pair.getValue().toString()));
			chartDatas.add(chartData);

			mapa = new HashMap<>();
			mapa.put("name", monthString);
			mapa.put("y", pair.getValue());
			mapa.put("drilldown", monthString);
			listMapPie.add(mapa);

			it.remove(); // avoids a ConcurrentModificationException
		}

		meses = sb.toString().split(",");

		mapaCli = ((GraficoClientesAno) resultadoClientes.getEntidades().get(0)).getClientes();
		it = mapaCli.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			listaClientes.add(Integer.valueOf(pair.getValue().toString()));

			it.remove(); // avoids a ConcurrentModificationException
		}
		Gson gson = new Gson();

		faturamento = listaFaturamento.toArray();

		clientes = listaClientes.toArray();

		dados = gson.toJson(chartDatas);
		this.faturamento = gson.toJson(faturamento);
		this.meses = gson.toJson(meses);
		this.clientes = gson.toJson(clientes);
		System.out.println(dados.toString());

		graficoVendas = false;
		graficoClientes = true;
	}

	public Chart buscarDetalhes(int mes) {

		// Obtém o command para executar a respectiva operação

		ICommand command = commands.get(CustomManagedBean.BUSCAR_DETALHES);

		Chart chart = new Chart();

		chart.setId(mes);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		Resultado resultadoDetalhes = command.execute(chart);

		chart = (Chart) resultadoDetalhes.getEntidades().get(0);

		return chart;
	}

	public Chart buscarDetalhesSemana(int semana) {

		// Obtém o command para executar a respectiva operação

		ICommand command = commands.get(CustomManagedBean.BUSCAR_DETALHES_SEMANA);

		Chart chart = new Chart();

		chart.setId(semana);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		Resultado resultadoDetalhes = command.execute(chart);

		chart = (Chart) resultadoDetalhes.getEntidades().get(0);

		return chart;
	}

	public Map<Integer, Double> getVendasAno() {
		return vendasAno;
	}

	public void setVendasAno(Map<Integer, Double> vendasAno) {
		this.vendasAno = vendasAno;
	}

	public Map<Integer, Map<Integer, Double>> getVendasSemana() {
		return vendasSemana;
	}

	public void setVendasSemana(Map<Integer, Map<Integer, Double>> vendasSemana) {
		this.vendasSemana = vendasSemana;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}

	public String getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(String faturamento) {
		this.faturamento = faturamento;
	}

	public String getClientes() {
		return clientes;
	}

	public void setClientes(String clientes) {
		this.clientes = clientes;
	}

	public String getMeses() {
		return meses;
	}

	public void setMeses(String meses) {
		this.meses = meses;
	}

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}

	public String getDrilldown() {
		return drilldown;
	}

	public void setDrilldown(String drilldown) {
		this.drilldown = drilldown;
	}

	public boolean isGraficoClientes() {
		return graficoClientes;
	}

	public void setGraficoClientes(boolean graficoClientes) {
		this.graficoClientes = graficoClientes;
	}

	public boolean isGraficoVendas() {
		return graficoVendas;
	}

	public void setGraficoVendas(boolean graficoVendas) {
		this.graficoVendas = graficoVendas;
	}

}