
package night.web.controle.web.command.impl;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import night.core.aplicacao.Resultado;
import night.dominio.Chart;
import night.dominio.ChartData;
import night.dominio.ChartSerie;
import night.dominio.Dominio;
import night.dominio.IEntidade;

public class BuscarDetalhesCommand extends AbstractCommand {

	@Override
	public Resultado execute(Dominio entidade) {
		Chart chart = new Chart();
		ChartSerie chartSerie = new ChartSerie();
		List<ChartData> listChartData = fachada.getDetalhesMes(Integer.parseInt(String.valueOf(entidade.getId())));
		String monthString = new DateFormatSymbols().getMonths()[Integer.parseInt(String.valueOf(entidade.getId()))
				- 1];
		Resultado result = new Resultado();
		List<IEntidade> lista = new ArrayList<>();

		chartSerie.setId(monthString);
		chartSerie.setName(monthString);
		chartSerie.setData(listChartData);

		List<ChartSerie> series = new ArrayList<>();
		series.add(chartSerie);

		chart.setSeries(series);

		lista.add(chart);
		result.setEntidades(lista);
		return result;
	}

}
