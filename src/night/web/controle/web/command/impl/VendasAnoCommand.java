
package night.web.controle.web.command.impl;

import java.util.ArrayList;
import java.util.List;

import night.core.aplicacao.Resultado;
import night.dominio.Dominio;
import night.dominio.GraficoVendasAno;
import night.dominio.IEntidade;

public class VendasAnoCommand extends AbstractCommand {

	@Override
	public Resultado execute(Dominio entidade) {

		GraficoVendasAno grafico = new GraficoVendasAno();
		grafico.setVendas(fachada.vendasAno());
		Resultado result = new Resultado();
		List<IEntidade> lista = new ArrayList<>();
		lista.add(grafico);
		result.setEntidades(lista);
		return result;
	}

}
