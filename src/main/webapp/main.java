package main.webapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import night.web.controle.web.command.ICommand;

class MyCall {
	public int calc(int x, int y) {
		return x * y;
	}
}

public class main {
	private static Map<String, ICommand> commands;

	public static void main(String[] args) {

		// String[] str = { "A", "B", "C" };
		// Arrays.parallelPrefix(str, String::concat);
		// System.out.println(str[2]);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yy/mm/dd");
		LocalDateTime ldt = LocalDateTime.of(2015, 10, 10, 11, 22);
		System.out.println(f.format(ldt));

		// MyCall my = new MyCall() {
		// public void print() {
		// System.out.println("myCall");
		// }
		// };

		// my.print();

		// IntStream ins = IntStream.of(1, 4, 5, 2);
		// IntSummaryStatistics sumy = ins.summaryStatistics();
		// sumy.accept(3);
		// System.out.println(sumy.getSum());

		// IntStream ins = IntStream.range(1, 6);
		// System.out.println(ins.average());

		// List<Integer> list = new ArrayList<>();
		// list.add(1);
		// list.add(2);
		// list.add(3);
		// list.replaceAll(in -> Integer.compare(2, in));
		// System.out.println(list);

		// Map<String, Integer> map = new HashMap<>();
		// map.put("1", 1);
		// map.put("2", 2);
		// map.put("3", 3);
		// map.putIfAbsent("1", 0);
		// map.computeIfPresent("3", (k, v) -> v > 3 ? v : null);
		// System.out.println(map.values());

		// Map<Integer, String> map = new HashMap<>();
		// map.put(1, "one");
		// map.put(2, "two");
		// map.put(3, "three");
		// Consumer<Integer> cons = System.out::print;
		// map.forEach(cons);

		// Stream<Integer> ins = Stream.of(1, 2, 3);
		// System.out.println(ins.peek(System.out::print).findAny().orElse(0));

		// Map<Boolean, List<Integer>> map =
		// ins.collect(Collectors.partitioningBy(in -> in % 2 != 0));
		// System.out.println(map.get(true));
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
