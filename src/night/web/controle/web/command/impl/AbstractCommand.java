
package night.web.controle.web.command.impl;

import night.core.IFachada;
import night.core.impl.controle.Fachada;
import night.web.controle.web.command.ICommand;



public abstract class AbstractCommand implements ICommand {

	protected IFachada fachada = new Fachada();

}
