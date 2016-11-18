package night.web.controle.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import night.core.aplicacao.Resultado;
import night.dominio.CategoriaPrincipal;
import night.dominio.Entrada;
import night.dominio.ItemEstoque;
import night.dominio.SubCategoria;
import night.web.controle.web.command.ICommand;

@ManagedBean
@ViewScoped
public class EstoqueManagedBean extends CustomManagedBean<ItemEstoque> {

	private static final long serialVersionUID = 1L;
	private ItemEstoque estoque;
	private List<ItemEstoque> listaEstoque;
	private List<CategoriaPrincipal> categorias;
	private List<SubCategoria> subCategorias;

	private long qtd;
	private double valor;

	public EstoqueManagedBean() {
		super();
		estoque = new ItemEstoque();
		setCategorias(new ArrayList<CategoriaPrincipal>());
		setSubCategorias(new ArrayList<SubCategoria>());
	}

	@PostConstruct
	public void init() {
		setCategorias(new ArrayList<CategoriaPrincipal>());
		setSubCategorias(new ArrayList<SubCategoria>());
		ICommand command = commands.get(CustomManagedBean.BUSCARTODOS);
		Resultado result = command.execute(new CategoriaPrincipal());
		getCategorias().addAll((Collection<? extends CategoriaPrincipal>) result.getEntidades());
		result = command.execute(new SubCategoria());
		getSubCategorias().addAll((Collection<? extends SubCategoria>) result.getEntidades());
		result = command.execute(estoque);
		listaEstoque = new ArrayList<>();
		result.getEntidades().forEach(e -> {
			listaEstoque.add((ItemEstoque) e);
		});

		listaEstoque.forEach(e -> {
			System.out.println(e.getNomeItem());
		});

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		Resultado res;
		String id = req.getParameter("id");

		if (id != null && !id.isEmpty()) {
			ICommand com = commands.get(CONSULTAR);
			estoque = new ItemEstoque();
			estoque.setId(Long.valueOf(id));
			this.consultar();
		}
	}

	public void salvar() {

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obt�m o command para executar a respectiva opera��o

		ICommand command = commands.get(operacao);
		ICommand commandConsultar = commands.get(this.CONSULTAR);
		estoque.setId(0);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		Resultado resultadoSubCategoria = commandConsultar.execute(estoque.getSubCategoria());
		estoque.setSubCategoria((SubCategoria) resultadoSubCategoria.getEntidades().get(0));

		Resultado resultado = command.execute(estoque);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btsalvar.getClientId(context), message);
		} else {
			estoque = (ItemEstoque) resultado.getEntidades().get(0);
			FacesMessage message = new FacesMessage("Salvo com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btsalvar.getClientId(context), message);
		}

	}

	public void consultar() {

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obt�m o command para executar a respectiva opera��o

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */
		Resultado resultado = command.execute(estoque);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btpesquisar.getClientId(context), message);
			estoque = new ItemEstoque();
		} else {

			estoque = (ItemEstoque) resultado.getEntidades().get(0);
		}

	}

	public void excluir() {

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obt�m o command para executar a respectiva opera��o

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */
		Resultado resultado = command.execute(estoque);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btexcluir.getClientId(context), message);
		} else {
			FacesMessage message = new FacesMessage("Excluído com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
			estoque = new ItemEstoque();
		}

	}

	public void alterar() {

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obt�m o command para executar a respectiva opera��o

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */
		Resultado resultado = command.execute(estoque);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
		} else {
			FacesMessage message = new FacesMessage("Alteração feita com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
			estoque = (ItemEstoque) resultado.getEntidades().get(0);
		}

	}

	public void carregaSubCategoria() {
		subCategorias = new ArrayList<>();
		SubCategoria sub = new SubCategoria();
		sub.getCategoria().setId(estoque.getSubCategoria().getCategoria().getId());

		ICommand command = commands.get(CustomManagedBean.BUSCAR_SUB_CATEGORIA);
		Resultado result = command.execute(sub);

		getSubCategorias().addAll((Collection<? extends SubCategoria>) result.getEntidades());
	}

	public void adicionarItensEstoque() {
		this.estoque.setQtdEstoque(this.estoque.getQtdEstoque() + this.qtd);
		this.estoque.setCustoTotalEstoque(this.estoque.getCustoTotalEstoque() + this.valor);
		this.estoque.getCustoUnitario();
		Entrada ent = new Entrada();
		ent.setPrecoCompra(BigDecimal.valueOf(valor / qtd));
		ent.setData(new Date());
		double volumeExtra = estoque.getMedida().getMedidaPorUnidade() * this.qtd;
		estoque.setVolumeEstoque(estoque.getVolumeEstoque() + volumeExtra);
		ICommand comm = commands.get(SALVAR);
		Resultado result = comm.execute(ent);
		estoque.getHistoricoPrecosCompra().add((Entrada) result.getEntidades().get(0));
		this.alterar();
	}

	public void limpar() {
		estoque = new ItemEstoque();
	}

	public ItemEstoque getEstoque() {
		return estoque;
	}

	public void setEstoque(ItemEstoque estoque) {
		this.estoque = estoque;
	}

	public List<ItemEstoque> getListaEstoque() {
		return listaEstoque;
	}

	public void setListaEstoque(List<ItemEstoque> listaEstoque) {
		this.listaEstoque = listaEstoque;
	}

	public List<CategoriaPrincipal> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaPrincipal> categorias) {
		this.categorias = categorias;
	}

	public List<SubCategoria> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<SubCategoria> subCategorias) {
		this.subCategorias = subCategorias;
	}

	public long getQtd() {
		return qtd;
	}

	public void setQtd(long qtd) {
		this.qtd = qtd;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}