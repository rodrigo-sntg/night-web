package night.web.controle.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import night.core.aplicacao.Resultado;
import night.core.impl.controle.Fachada;
import night.core.util.MedidaUtils;
import night.dominio.CategoriaPrincipal;
import night.dominio.Comanda;
import night.dominio.Dominio;
import night.dominio.ItemComanda;
import night.dominio.ItemConsumo;
import night.dominio.ItemEstoque;
import night.dominio.ItemEstoqueConsumo;
import night.dominio.Pagamento;
import night.web.controle.web.command.ICommand;

@ManagedBean
@ViewScoped
public class ComandaManagedBean extends CustomManagedBean<Comanda> {

	private static final long serialVersionUID = 1L;
	private Comanda comanda;
	private long idItem;
	private String nome;
	private ItemConsumo item;
	private ItemComanda itemComanda;
	private List<ItemConsumo> listaItens;
	private List<Pagamento> listaPagamentos;
	private int qtd;
	private Pagamento pagamento;

	public ComandaManagedBean() {
		super();
		item = new ItemConsumo();
		itemComanda = new ItemComanda();
		comanda = new Comanda();
		listaItens = new ArrayList<>();
		pagamento = new Pagamento();

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		Resultado res;
		String id = (String) req.getAttribute("numeroComanda");
		if (id != null && !id.isEmpty()) {
			ICommand com = commands.get(CONSULTAR);
			comanda = new Comanda();
			comanda.setNumeroComanda(Integer.valueOf(id));
			this.consultar();
		}

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@PostConstruct
	public void init() {
		ICommand command = commands.get(CustomManagedBean.BUSCARTODOS);
		Resultado result = command.execute(new CategoriaPrincipal());
		result = command.execute(new ItemConsumo());
		getListaItens().addAll((Collection<? extends ItemConsumo>) result.getEntidades());

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

		String codComanda = req.getParameter("numeroComanda");

		if (codComanda != null && !codComanda.isEmpty()) {
			ICommand com = commands.get(CONSULTAR);
			comanda = new Comanda();
			comanda.setNumeroComanda(Integer.valueOf(codComanda));
			this.consultar();
		}

	}

	@SuppressWarnings("static-access")
	public void salvar() {

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obt�m o command para executar a respectiva opera��o

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		Resultado resultado = command.execute(comanda);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btsalvar.getClientId(context), message);
		} else {
			comanda = (Comanda) resultado.getEntidades().get(0);
			FacesMessage message = new FacesMessage("Salvo com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btsalvar.getClientId(context), message);
		}

	}

	@SuppressWarnings("static-access")
	public void salvar(Dominio entidade) {

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obt�m o command para executar a respectiva opera��o

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */

		Resultado resultado = command.execute(pagamento);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btsalvar.getClientId(context), message);
		} else {
			pagamento = (Pagamento) resultado.getEntidades().get(0);
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
		Resultado resultado = command.execute(comanda);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btpesquisar.getClientId(context), message);
			comanda = new Comanda();
		} else if (!((Comanda) resultado.getEntidades().get(0)).isStatus()) { // comanda
																				// fechada
			FacesMessage message = new FacesMessage("Comanda fechada.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btpesquisar.getClientId(context), message);
			comanda = new Comanda();
		} else {
			comanda = (Comanda) resultado.getEntidades().get(0);
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
		Resultado resultado = command.execute(comanda);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btexcluir.getClientId(context), message);
		} else {
			FacesMessage message = new FacesMessage("Excluído com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btexcluir.getClientId(context), message);
			comanda = new Comanda();
		}

	}

	public String alterar() {

		String operacao = new Object() {
		}.getClass().getEnclosingMethod().getName().toUpperCase();

		// Obt�m o command para executar a respectiva opera��o

		ICommand command = commands.get(operacao);

		/*
		 * Executa o command que chamará a fachada para executar a operação
		 * requisitada o retorno é uma instância da classe resultado que pode
		 * conter mensagens derro ou entidades de retorno
		 */
		Resultado resultado = command.execute(comanda);
		if (resultado.getMsg() != null && resultado.getMsg() != "") {
			FacesMessage message = new FacesMessage(resultado.getMsg());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
		} else {
			FacesMessage message = new FacesMessage("Alterado com sucesso.");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(super.btalterar.getClientId(context), message);
			comanda = (Comanda) resultado.getEntidades().get(0);
		}

		return "comanda?faces-redirect=true&numeroComanda=" + comanda.getNumeroComanda();
	}

	public void fecharComanda() {
		Resultado result;
		ICommand comm = null;
		if (pagamento.getFormaPagamento().equals(Pagamento.PAGAMENTO_DINHEIRO)) {
			if (pagamento.getValorRecebido() < comanda.getValorTotal()) {
				FacesMessage message = new FacesMessage("Pagamento Inválido.");
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(super.btalterar.getClientId(context), message);
			}
		} else {
			comanda.setStatus(false);
			pagamento.setComanda(comanda);
			pagamento.setValorRecebido(comanda.getValorTotal());
			pagamento.setValor(comanda.getValorTotal());
			comm = commands.get(Fachada.SALVAR);
			comm.execute(pagamento);
			comanda.getPagamento().add(pagamento);
			// this.salvar(pagamento);

			this.alterar();
		}

		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
				FacesContext.getCurrentInstance(), null, "comanda.xhtml?numeroComanda=" + comanda.getNumeroComanda());

	}

	public String inserirItemLista() {
		ICommand command = commands.get(CustomManagedBean.CONSULTAR);
		Resultado res;
		ItemConsumo ic = new ItemConsumo();
		ICommand comm;
		// ItemComanda itemComanda = new ItemComanda();

		boolean existe = false;

		ic.setId(idItem);
		res = command.execute(ic);
		ic = (ItemConsumo) res.getEntidades().get(0);

		for (ItemEstoqueConsumo itemConsumo : ic.getListaItemEstoqueConsumo()) {

			double precoTotal = itemConsumo.getItemEstoque().getCustoTotalEstoque();
			double volume = itemConsumo.getItemEstoque().getVolumeEstoque();

			// Utils para converter de um tipo de unidade de medida para outro

			MedidaUtils medidaUtils = new MedidaUtils();

			double medidaConvertida = medidaUtils.converte(itemConsumo.getMedida(),
					itemConsumo.getItemEstoque().getMedida());
			// reduzir o volume de estoque
			volume = (volume - (medidaConvertida * qtd));
			// reduzir proporcionalmente o valor total no estoque
			precoTotal -= ((medidaConvertida / itemConsumo.getItemEstoque().getMedida().getMedidaPorUnidade())
					* precoTotal) * qtd;

			ItemEstoque estoque = itemConsumo.getItemEstoque();
			estoque.setCustoTotalEstoque(precoTotal);
			estoque.setVolumeEstoque(volume);
			estoque.setQtdEstoque((int) Math.ceil(volume));
			comm = commands.get(Fachada.ALTERAR);
			comm.execute(estoque);
			/**
			 * TODO Repetir os testes dessa mesma forma para o static void main
			 * tentar reproduzir exatamente essa situação
			 */

			// itemConsumo.getItemEstoque().setCustoTotalEstoque(precoTotal);
			itemConsumo.setItemEstoque(estoque);
			// itemConsumo.getItemEstoque().setVolumeEstoque(volume);
			// itemConsumo.getItemEstoque().setQtdEstoque((int)
			// Math.ceil(volume));
		}
		itemComanda.setItem(ic);
		if (qtd == 0)
			this.getItemComanda().setQuantidade(1);
		else
			this.getItemComanda().setQuantidade(qtd);
		for (ItemComanda iComanda : comanda.getListaItemComanda()) {
			if (iComanda.getItem().getId() == ic.getId()) {
				iComanda.setQuantidade(iComanda.getQuantidade() + itemComanda.getQuantidade());
				existe = true;
				comm = commands.get(Fachada.ALTERAR);
				comm.execute(iComanda);
			}
		}
		if (!existe) {
			comm = commands.get(Fachada.SALVAR);
			comm.execute(itemComanda);
			comanda.getListaItemComanda().add(itemComanda);
		}

		comanda.getValorTotal();

		item = new ItemConsumo();
		this.setItemComanda(new ItemComanda());

		this.alterar();

		return "comanda?faces-redirect=true&numeroComanda=" + comanda.getNumeroComanda();

	}

	public void namedChanged(AjaxBehaviorEvent event) {
		nome = item.getNomeItem();
	}

	public void altera(long id) {

		item = new ItemConsumo();

		ICommand command = commands.get(CustomManagedBean.CONSULTAR);
		Resultado res;
		ItemConsumo ic = new ItemConsumo();

		ic.setId(id);
		res = command.execute(ic);
		ic = (ItemConsumo) res.getEntidades().get(0);

		item = ic;

		this.setIdItem(id);
	}

	public void removerItemDaLista(long id) {
		this.comanda.getListaItemComanda().forEach(i -> {
			if (i.getId() == id) {
				this.comanda.getListaItemComanda().remove(i);
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

	public List<ItemConsumo> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItemConsumo> listaItens) {
		this.listaItens = listaItens;
	}

	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(Comanda comanda) {
		this.comanda = comanda;
	}

	public ItemComanda getItemComanda() {
		return itemComanda;
	}

	public void setItemComanda(ItemComanda itemComanda) {
		this.itemComanda = itemComanda;
	}

	public List<Pagamento> getListaPagamentos() {
		return listaPagamentos;
	}

	public void setListaPagamentos(List<Pagamento> listaPagamentos) {
		this.listaPagamentos = listaPagamentos;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public long getIdItem() {
		return idItem;
	}

	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}