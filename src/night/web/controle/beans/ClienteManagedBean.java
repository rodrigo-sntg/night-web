package night.web.controle.beans;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import night.core.aplicacao.Resultado;
import night.dominio.Cliente;
import night.dominio.Comanda;
import night.web.controle.web.command.ICommand;

@ManagedBean
@ViewScoped
public class ClienteManagedBean extends CustomManagedBean<Cliente> {

	private static final long serialVersionUID = 1L;
	private Cliente cliente;
	private String cpf;
	private String rg;
	private String telefone;
	private Comanda comanda;
	protected UIComponent btabrir;

	public ClienteManagedBean() {
		super();
		comanda = new Comanda();
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		Resultado res;
		String id = (String) req.getAttribute("idCliente");
		if (id != null && !id.isEmpty()) {
			ICommand com = commands.get(CONSULTAR);
			cliente = new Cliente();
			cliente.setId(Long.valueOf(id));
			this.consultar();
		}
		comanda.setCliente(cliente);
		cliente = new Cliente();
		cliente.setAtivo(true);
	}

	public void salvar() {
		// O viewhelper retorna a entidade especifica para a tela que chamou
		// esta servlet

		arrumaCpfRgTelefone();

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obtém o command para executar a respectiva operação

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */
		Resultado resultado = command.execute(cliente);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btsalvar.getClientId(context), message);
		} else {
			cliente = (Cliente) resultado.getEntidades().get(0);
			FacesMessage message = new FacesMessage("Salvo com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btsalvar.getClientId(context), message);
		}

	}

	public String abrirComanda() {

		if (cliente.getId() != 0) {
			comanda.setCliente(cliente);
			comanda.setDtCadastro(new Date());
			comanda.setDataEntrada(new Date());
			comanda.setStatus(true);
			// Obtém o command para executar a respectiva operação

			ICommand command = commands.get(CustomManagedBean.SALVAR);

			/*
			 * Executa o command que chamará a fachada para executar a operação
			 * requisitada o retorno é uma instância da classe resultado que
			 * pode conter mensagens derro ou entidades de retorno
			 */
			Resultado resultado = command.execute(comanda);
			if (resultado.getMsg() != null && resultado.getMsg() != "") {
				FacesMessage message = new FacesMessage(resultado.getMsg());
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(super.btsalvar.getClientId(context), message);
			} else {
				FacesMessage message = new FacesMessage("Comanda aberta com sucesso.");
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(super.btsalvar.getClientId(context), message);
				// return "comanda?faces-redirect=true&cod="
				// + ((Comanda)
				// resultado.getEntidades().get(0)).getNumeroComanda();
			}

		}
		return "";
	}

	public void consultar() {
		// O viewhelper retorna a entidade especifica para a tela que chamou
		// esta servlet

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		arrumaCpfRgTelefone();

		// Obtém o command para executar a respectiva operação

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */
		Resultado resultado = command.execute(cliente);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btpesquisar.getClientId(context), message);
		} else {
			if (resultado.getEntidades() != null)
				cliente = (Cliente) resultado.getEntidades().get(0);
		}

		setRg(String.valueOf(cliente.getRg()));
		setCpf(String.valueOf(cliente.getCpf()));
		setTelefone(String.valueOf(cliente.getTelefone()));

	}

	public void excluir() {
		// O viewhelper retorna a entidade especifica para a tela que chamou
		// esta servlet

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		arrumaCpfRgTelefone();

		// Obtém o command para executar a respectiva operação

		ICommand command = commands.get(operacao);
		cliente.setAtivo(false);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		if (cliente == null || cliente.getCpf() == 0) {
			FacesMessage message = new FacesMessage("É necessário pesquisar um cliente para excluir.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btexcluir.getClientId(context), message);
		}
		Resultado resultado = command.execute(cliente);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btexcluir.getClientId(context), message);
		} else {
			cliente = new Cliente();
		}

	}

	private void arrumaCpfRgTelefone() {
		if (rg != "")
			cliente.setRg(Long.valueOf(rg.replace(".", "").replace("-", "").replaceAll("\\s+", "")));

		if (cpf != "")
			cliente.setCpf(Long.valueOf(cpf.replace(".", "").replace("-", "").replaceAll("\\s+", "")));

		if (telefone != "")
			cliente.setTelefone(
					Long.valueOf(telefone.replace("(", "").replace(")", "").replace("-", "").replaceAll("\\s+", "")));
	}

	public void alterar() {
		// O viewhelper retorna a entidade especifica para a tela que chamou
		// esta servlet

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		arrumaCpfRgTelefone();

		// Obtém o command para executar a respectiva operação

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */
		Resultado resultado = command.execute(cliente);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
		} else {
			cliente = (Cliente) resultado.getEntidades().get(0);
			FacesMessage message = new FacesMessage("Alterado com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
		}

	}

	public void limpar() {
		cliente = new Cliente();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(Comanda comanda) {
		this.comanda = comanda;
	}

	public UIComponent getBtabrir() {
		return btabrir;
	}

	public void setBtabrir(UIComponent btabrir) {
		this.btabrir = btabrir;
	}
}