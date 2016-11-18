
package night.web.controle.web.command;

import night.core.aplicacao.Resultado;
import night.dominio.Dominio;

public interface ICommand {

	public Resultado execute(Dominio entidade);

}
