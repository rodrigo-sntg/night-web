
package night.web.controle.web.command.impl;

import night.core.aplicacao.Resultado;
import night.dominio.Dominio;

public class BuscarSubCategoriaCommand extends AbstractCommand {

	@Override
	public Resultado execute(Dominio entidade) {

		return fachada.buscarSubCategoria(entidade);
	}

}
