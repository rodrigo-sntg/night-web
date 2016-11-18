package night.web.controle.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;

import night.dominio.Dominio;
import night.web.controle.web.command.ICommand;
import night.web.controle.web.command.impl.AlterarCommand;
import night.web.controle.web.command.impl.BuscarDetalhesCommand;
import night.web.controle.web.command.impl.BuscarDetalhesSemanaCommand;
import night.web.controle.web.command.impl.BuscarSubCategoriaCommand;
import night.web.controle.web.command.impl.BuscarTodosCommand;
import night.web.controle.web.command.impl.ClientesAnoCommand;
import night.web.controle.web.command.impl.ConsultarCommand;
import night.web.controle.web.command.impl.ExcluirCommand;
import night.web.controle.web.command.impl.SalvarCommand;
import night.web.controle.web.command.impl.VendasAnoCommand;
import night.web.controle.web.command.impl.VisualizarCommand;

public class CustomManagedBean<T extends Dominio> implements Serializable {
	public static final String BUSCAR_SUB_CATEGORIA = "BUSCAR_SUB_CATEGORIA";
	public static final String ALTERAR = "ALTERAR";
	public static final String BUSCARTODOS = "BUSCARTODOS";
	public static final String VISUALIZAR = "VISUALIZAR";
	public static final String EXCLUIR = "EXCLUIR";
	public static final String CONSULTAR = "CONSULTAR";
	public static final String SALVAR = "SALVAR";
	public static final String VENDAS_ANO = "VENDAS_ANO";
	public static final String CLIENTES_ANO = "CLIENTES_ANO";
	public static final String BUSCAR_DETALHES = "BUSCAR_DETALHES";
	public static final String BUSCAR_DETALHES_SEMANA = "BUSCAR_DETALHES_SEMANA";

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	protected static Map<String, ICommand> commands;
	protected UIComponent btsalvar;
	protected UIComponent btpesquisar;
	protected UIComponent btalterar;
	protected UIComponent btexcluir;
	protected T entidade;

	protected CustomManagedBean() {
		/*
		 * Utilizando o command para chamar a fachada e indexando cada command
		 * pela operação garantimos que esta servelt atenderá qualquer
		 * operaoperação
		 */
		commands = new HashMap<String, ICommand>();

		commands.put(SALVAR, new SalvarCommand());
		commands.put(EXCLUIR, new ExcluirCommand());
		commands.put(CONSULTAR, new ConsultarCommand());
		commands.put(VISUALIZAR, new VisualizarCommand());
		commands.put(BUSCARTODOS, new BuscarTodosCommand());
		commands.put(ALTERAR, new AlterarCommand());
		commands.put(VENDAS_ANO, new VendasAnoCommand());
		commands.put(CLIENTES_ANO, new ClientesAnoCommand());
		commands.put(BUSCAR_DETALHES, new BuscarDetalhesCommand());
		commands.put(BUSCAR_DETALHES_SEMANA, new BuscarDetalhesSemanaCommand());

		commands.put(BUSCAR_SUB_CATEGORIA, new BuscarSubCategoriaCommand());

	}

	public void setBtsalvar(UIComponent mybutton) {
		this.btsalvar = mybutton;
	}

	public UIComponent getBtsalvar() {
		return btsalvar;
	}

	public void setBtpesquisar(UIComponent mybutton) {
		this.btpesquisar = mybutton;
	}

	public UIComponent getBtpesquisar() {
		return btpesquisar;
	}

	public UIComponent getBtalterar() {
		return btalterar;
	}

	public void setBtalterar(UIComponent btalterar) {
		this.btalterar = btalterar;
	}

	public UIComponent getBtexcluir() {
		return btexcluir;
	}

	public void setBtexcluir(UIComponent btexcluir) {
		this.btexcluir = btexcluir;
	}
}
