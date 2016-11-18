
package night.web.controle.web.command.impl;

import night.core.aplicacao.Resultado;
import night.dominio.Dominio;

public class SalvarCommand extends AbstractCommand {

	@Override
	public Resultado execute(Dominio entidade) {

		return fachada.salvar(entidade);
	}

}
