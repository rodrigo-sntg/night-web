package night.web.controle.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import night.core.aplicacao.Resultado;
import night.dominio.CategoriaPrincipal;
import night.dominio.ItemConsumo;
import night.dominio.ItemEstoque;
import night.dominio.ItemEstoqueConsumo;
import night.dominio.Medida;
import night.dominio.SubCategoria;
import night.web.controle.web.command.ICommand;

@ManagedBean
@ViewScoped
public class ItemConsumoManagedBean extends CustomManagedBean<ItemConsumo> {

	private static final long serialVersionUID = 1L;
	private ItemConsumo item;
	private long idItem;
	private List<ItemConsumo> listaItens;
	private List<ItemEstoque> listaEstoque;
	private List<CategoriaPrincipal> categorias;
	private List<SubCategoria> subCategorias;
	private ItemEstoqueConsumo itemEstoqueConsumo;
	private Medida medidaItemEstoqueConsumo;
	private long qtd;
	private double valor;

	public ItemConsumoManagedBean() {
		super();
		itemEstoqueConsumo = new ItemEstoqueConsumo();
		item = new ItemConsumo();
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
		ItemEstoque estoque = new ItemEstoque();
		result = command.execute(estoque);

		listaEstoque = new ArrayList<>();
		result.getEntidades().forEach(e -> {
			listaEstoque.add((ItemEstoque) e);
			System.out.println(((ItemEstoque) e).getNomeItem() + " - ESTOQUE");
		});

		listaItens = new ArrayList<>();
		ItemConsumo itemConsumo = new ItemConsumo();
		result = command.execute(itemConsumo);
		result.getEntidades().forEach(e -> {
			listaItens.add((ItemConsumo) e);
			System.out.println(((ItemConsumo) e).getNomeItem() + " - CONSUMO");
		});

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		Resultado res;
		String id = req.getParameter("id");

		if (id != null && !id.isEmpty()) {
			ICommand com = commands.get(CONSULTAR);
			item = new ItemConsumo();
			item.setId(Long.valueOf(id));
			this.consultar();
		}

	}

	public void salvar() {

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obt�m o command para executar a respectiva opera��o

		ICommand command = commands.get(operacao);
		ICommand commandConsultar = commands.get(this.CONSULTAR);
		item.setId(0);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		Resultado resultadoSubCategoria = commandConsultar.execute(item.getSubCategoria());
		item.setSubCategoria((SubCategoria) resultadoSubCategoria.getEntidades().get(0));

		Resultado resultado = command.execute(item);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btsalvar.getClientId(context), message);
		} else {
			item = (ItemConsumo) resultado.getEntidades().get(0);
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
		Resultado resultado = command.execute(item);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btpesquisar.getClientId(context), message);
			itemEstoqueConsumo = new ItemEstoqueConsumo();
			item = new ItemConsumo();
		} else {
			item = (ItemConsumo) resultado.getEntidades().get(0);
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
		Resultado resultado = command.execute(item);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btexcluir.getClientId(context), message);
		} else {
			FacesMessage message = new FacesMessage("Excluído com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btexcluir.getClientId(context), message);
			item = new ItemConsumo();
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
		Resultado resultado = command.execute(item);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
		} else {
			FacesMessage message = new FacesMessage("Alterado com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
			item = (ItemConsumo) resultado.getEntidades().get(0);
		}

	}

	public void carregaSubCategoria() {
		subCategorias = new ArrayList<>();
		SubCategoria sub = new SubCategoria();
		sub.getCategoria().setId(item.getSubCategoria().getCategoria().getId());

		ICommand command = commands.get(CustomManagedBean.BUSCAR_SUB_CATEGORIA);
		Resultado result = command.execute(sub);

		getSubCategorias().addAll((Collection<? extends SubCategoria>) result.getEntidades());
	}

	public void inserirItemLista() {
		ICommand command = commands.get(CustomManagedBean.CONSULTAR);
		ItemEstoque ie = new ItemEstoque();
		ie.setId(itemEstoqueConsumo.getItemEstoque().getId());
		Resultado res = command.execute(ie);
		ie = (ItemEstoque) res.getEntidades().get(0);
		itemEstoqueConsumo.setItemEstoque(ie);
		item.getListaItemEstoqueConsumo().add(itemEstoqueConsumo);
		itemEstoqueConsumo = new ItemEstoqueConsumo();

	}

	public void removerItemDaLista(long id) {
		this.getItem().getListaItemEstoqueConsumo().forEach(i -> {
			if (i.getId() == id) {
				this.getItem().getListaItemEstoqueConsumo().remove(i);
			}
		});
	}

	public void limpar() {
		item = new ItemConsumo();
	}

	public ItemConsumo getItem() {
		return item;
	}

	public void setItem(ItemConsumo item) {
		this.item = item;
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

	public ItemEstoqueConsumo getItemEstoqueConsumo() {
		return itemEstoqueConsumo;
	}

	public void setItemEstoqueConsumo(ItemEstoqueConsumo itemEstoqueConsumo) {
		this.itemEstoqueConsumo = itemEstoqueConsumo;
	}

	public Medida getMedidaItemEstoqueConsumo() {
		return medidaItemEstoqueConsumo;
	}

	public void setMedidaItemEstoqueConsumo(Medida medidaItemEstoqueConsumo) {
		this.medidaItemEstoqueConsumo = medidaItemEstoqueConsumo;
	}

	public List<ItemConsumo> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItemConsumo> listaItens) {
		this.listaItens = listaItens;
	}

	public long getIdItem() {
		return idItem;
	}

	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

}