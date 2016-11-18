package night.web.controle.web.command.impl;

import java.util.ArrayList;
import java.util.List;

import night.core.aplicacao.Resultado;
import night.dominio.Chart;
import night.dominio.ChartData;
import night.dominio.ChartSerie;
import night.dominio.Dominio;
import night.dominio.IEntidade;

public class BuscarDetalhesSemanaCommand extends AbstractCommand {

	@Override
	public Resultado execute(Dominio entidade) {
		Chart chart = new Chart();
		ChartSerie chartSerie = new ChartSerie();
		List<ChartData> listChartData = fachada.getDetalhesSemana(Integer.parseInt(String.valueOf(entidade.getId())));
		Resultado result = new Resultado();
		List<IEntidade> lista = new ArrayList<>();

		chartSerie.setId("Semana " + entidade.getId());
		chartSerie.setName("Semana " + entidade.getId());
		chartSerie.setData(listChartData);

		List<ChartSerie> series = new ArrayList<>();
		series.add(chartSerie);

		chart.setSeries(series);

		lista.add(chart);
		result.setEntidades(lista);
		return result;
	}

}
