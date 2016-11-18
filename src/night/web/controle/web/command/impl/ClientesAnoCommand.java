
package night.web.controle.web.command.impl;

import java.util.ArrayList;
import java.util.List;

import night.core.aplicacao.Resultado;
import night.dominio.Dominio;
import night.dominio.GraficoClientesAno;
import night.dominio.IEntidade;

public class ClientesAnoCommand extends AbstractCommand {

	@Override
	public Resultado execute(Dominio entidade) {

		GraficoClientesAno grafico = new GraficoClientesAno();
		grafico.setClientes(fachada.clientesAno());
		Resultado result = new Resultado();
		List<IEntidade> lista = new ArrayList<>();
		lista.add(grafico);
		result.setEntidades(lista);
		return result;
	}

}
