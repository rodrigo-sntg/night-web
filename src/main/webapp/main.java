package main.webapp;

import java.util.Map;

import night.web.controle.web.command.ICommand;

public class main {
	private static Map<String, ICommand> commands;

	public static void main(String[] args) {

		// Produts p = new Produts();
		// p.setNome("teste");
		// p.setPreco(123.2);
		//
		// EntityManagerFactory factory =
		// Persistence.createEntityManagerFactory("rods");
		// EntityManager em = factory.createEntityManager();
		// em.getTransaction().begin();
		// em.persist(p);
		// em.getTransaction().commit();
		// commands = new HashMap<String, ICommand>();
		//
		// commands.put("SALVAR", new SalvarTesteCommand());
		// // TODO Auto-generated method stub
		// // O viewhelper retorna a entidade especifica para a tela que chamou
		// // esta servlet
		// Cliente entidade = new Cliente();
		// Comanda comanda = new Comanda();
		// entidade.getComanda().add(comanda);
		//
		// entidade.setAtivo(true);
		// entidade.setNome("rodrigo");
		// entidade.setDtCadastro(new Date());
		// entidade.setDtNascimento(new Date());
		// entidade.setCpf(Long.valueOf("12312312312"));
		// entidade.setRg(Long.valueOf("12312312312"));
		// entidade.setTelefone(Long.valueOf("1112341234"));
		// // Obtém a operação executada
		// String operacao = "SALVAR";
		//
		// // Obtém o command para executar a respectiva operação
		// ICommand command = commands.get(operacao);
		//
		// // Cliente cli =
		// //
		// HibernateDAOFactory.DEFAULT.buildClienteDAO().getById(Long.valueOf("1"));
		//
		// Cliente rodrigo = new Cliente();
		// Endereco end = new Endereco();
		// end.setCidade("mogi das cruzes");
		// end.setComplemento("apt 31");
		// end.setDtCadastro(new Date());
		// end.setEstado("sp");
		// end.setRua("rua professor flaviano de melo");
		// rodrigo.setEndereco(end);
		// rodrigo.setNome("rodrigo");
		// rodrigo.setCpf(Long.valueOf("12312312312"));
		// rodrigo.setRg(Long.valueOf("1231231231"));
		// rodrigo.setTelefone(Long.valueOf("11975776906"));
		// rodrigo.setAtivo(true);
		// rodrigo.setDtNascimento(new Date());
		// rodrigo.setDtCadastro(new Date());
		//
		// ClienteDAOOld cliDao = new ClienteDAOOld();
		//
		// // cliDao.salvar(rodrigo);
		//
		// // List<Dominio> lista = cliDao.consultar(new Cliente());
		// Cliente novo = new Cliente();
		// novo.setId(8);
		//
		// List<Dominio> lista = cliDao.consultar(novo);
		// novo = (Cliente) lista.get(0);
		// novo.setNome("SANTIAGO");
		// novo.getEndereco().setCidade("tokio");
		// cliDao.alterar(novo);
		//
		// // HibernateDAOFactory.DEFAULT.buildClienteDAO().salvar(rodrigo);
		// // HibernateDAOFactory.DEFAULT.buildClienteDAO().save(rodrigo);
		//
		// /*
		// * Executa o command que chamar� a fachada para executar a opera��o
		// * requisitada o retorno � uma inst�ncia da classe resultado que pode
		// * conter mensagens derro ou entidades de retorno
		// */
		//
		// Resultado resultado = command.execute(entidade);
		// resultado.getEntidades();
	}

}
