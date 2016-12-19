package testes;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.Select;

public class TestaTelaCadastroCliente {

	private static ChromeDriverService service;

	private static WebDriver driver;

	// Declarando um objeto do tipo WebDriver, utilizado pelo Selenium
	// WebDriver.

	// Método que inicia tudo que for necessário para o teste
	// Cria uma instância do navegador e abre a página inicial da DevMedia.
	@BeforeClass
	public static void setUpTest() {

		service = new ChromeDriverService.Builder().usingDriverExecutable(new File("/usr/local/bin/chromedriver"))
				.usingAnyFreePort().build();
		try {
			service.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File file = new File("/usr/local/bin/chromedriver");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		driver = new ChromeDriver();
		driver.get("http://localhost:8080/web/pages/xhtml/cadastro.xhtml");

	}

	// Método que finaliza o teste, fechando a instância do WebDriver.
	@AfterClass
	public static void tearDownTest() {
		driver.quit();
	}

	// Testa título "Cadastro Cliente".
	@Test
	public void testaTituloDaPagina() {
		assertEquals("Cadastro Cliente", driver.getTitle());
	}

	// Método que testa o login no site DevMedia.
	@Test
	public void testaPesquisaPorCpf() {

		// Instancia um novo objeto do tipo "WebElement", e passa como parâmetro
		// um elemento da tela cujo valor do atributo "cpf" seja igual a
		// "cpf".
		WebElement cpf = driver.findElement(By.className("cpf"));

		// Insere dados no elemento "cpf".
		inserirValorWebElement(cpf, "37443472811");

		// Clica no botão "OK" e submete os dados para concluir a pesquisa.
		driver.findElement(By.id("btpesquisar")).click();

		WebElement nome = driver.findElement(By.id("inputnome"));

		assertEquals("Rodrigo Santiago Silva", nome.getAttribute("value"));
	}

	// Método que testa inserção com cpf inválido
	@Test
	public void testaInsercaoCpfInvalido() {

		// Instancia um novo objeto do tipo "WebElement", e passa como parâmetro
		// um elemento da tela cujo valor do atributo "cpf" seja igual a
		// "cpf".

		// driver.findElement(By.id("btnovo")).click();
		driver.get("http://localhost:8080/web/pages/xhtml/cadastro.xhtml");

		WebElement cpf = driver.findElement(By.className("cpf"));
		WebElement rg = driver.findElement(By.className("rg"));
		WebElement nome = driver.findElement(By.id("inputnome"));
		WebElement dataNascimento = driver.findElement(By.id("dataNascimento"));
		WebElement rua = driver.findElement(By.id("inputrua"));
		WebElement complemento = driver.findElement(By.id("inputcomplemento"));
		WebElement cidade = driver.findElement(By.id("inputcidade"));
		WebElement estado = driver.findElement(By.id("selectestado"));
		Select selectEstado = new Select(estado);

		// Sarah Jennifer de Paula
		/*
		 * cpf errado - 85633674802 cpf certo - 85633674804
		 */

		// Insere dados

		inserirValorWebElement(nome, "Sarah Jennifer de Paula");
		inserirValorWebElement(dataNascimento, "25/03/1989");
		inserirValorWebElement(rg, "467764621");
		inserirValorWebElement(cpf, "85633674802");
		inserirValorWebElement(rua, "Rua Washington Luís,s/n,340");
		inserirValorWebElement(complemento, "Centro");
		inserirValorWebElement(cidade, "Ariri");
		selectEstado.selectByValue("sp");

		// Clica no botão salvar
		driver.findElement(By.id("btsalvar")).click();
		WebElement label = driver.findElement(By.id("labelErroSalvar"));

		assertEquals("CPF inválido!", label.getText());

	}

	// Método que testa inserção sem nome
	@Test
	public void testaInsercaoSemNome() {

		driver.get("http://localhost:8080/web/pages/xhtml/cadastro.xhtml");

		/**
		 * instanciando os elementos
		 */
		WebElement cpf = driver.findElement(By.className("cpf"));
		WebElement rg = driver.findElement(By.className("rg"));
		WebElement nome = driver.findElement(By.id("inputnome"));
		WebElement dataNascimento = driver.findElement(By.id("dataNascimento"));
		WebElement rua = driver.findElement(By.id("inputrua"));
		WebElement complemento = driver.findElement(By.id("inputcomplemento"));
		WebElement cidade = driver.findElement(By.id("inputcidade"));
		Select selectEstado = new Select(driver.findElement(By.id("selectestado")));

		/**
		 * inserindo os dados nos elementos instanciados
		 */

		inserirValorWebElement(dataNascimento, "25/03/1989");
		inserirValorWebElement(rg, "467764621");
		inserirValorWebElement(cpf, "76711438396");
		inserirValorWebElement(rua, "Rua Washington Luís,s/n,340");
		inserirValorWebElement(complemento, "Centro");
		inserirValorWebElement(cidade, "Ariri");
		selectEstado.selectByValue("sp");

		// Clica no botão salvar
		driver.findElement(By.id("btsalvar")).click();
		WebElement label = driver.findElement(By.id("labelErroSalvar"));

		assertEquals("Nome e endereço são de preenchimento obrigatórios!", label.getText());

	}

	// Método que testa inserção com cpd duplicado
	@Test
	public void testaInsercaoComCpfDuplicado() {

		// driver.findElement(By.id("btnovo")).click();
		driver.get("http://localhost:8080/web/pages/xhtml/cadastro.xhtml");

		/**
		 * instanciando os elementos
		 */
		WebElement cpf = driver.findElement(By.className("cpf"));
		WebElement rg = driver.findElement(By.className("rg"));
		WebElement nome = driver.findElement(By.id("inputnome"));
		WebElement dataNascimento = driver.findElement(By.id("dataNascimento"));
		WebElement rua = driver.findElement(By.id("inputrua"));
		WebElement complemento = driver.findElement(By.id("inputcomplemento"));
		WebElement cidade = driver.findElement(By.id("inputcidade"));
		Select selectEstado = new Select(driver.findElement(By.id("selectestado")));

		/**
		 * inserindo os dados nos elementos instanciados
		 */
		inserirValorWebElement(nome, "teste teste");
		inserirValorWebElement(dataNascimento, "25/03/1989");
		inserirValorWebElement(rg, "467764621");
		inserirValorWebElement(cpf, "37443472811");
		inserirValorWebElement(rua, "Rua Washington Luís,s/n,340");
		inserirValorWebElement(complemento, "Centro");
		inserirValorWebElement(cidade, "Ariri");
		selectEstado.selectByValue("sp");

		// Clica no botão "OK" e submete os dados para concluir a pesquisa.
		driver.findElement(By.id("btsalvar")).click();
		WebElement label = driver.findElement(By.id("labelErroSalvar"));

		assertEquals("CPF Duplicado!", label.getText());

	}

	private void inserirValorWebElement(WebElement elem, String valor) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		elem.click();
		elem.clear();
		elem.sendKeys(valor);
	}
}
