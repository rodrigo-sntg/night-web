package cliente;

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

public class TestaBotaoPesquisa {

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
		cpf.sendKeys("37443472811");

		// Clica no botão "OK" e submete os dados para concluir a pesquisa.
		driver.findElement(By.id("btpesquisar")).click();

		WebElement nome = driver.findElement(By.id("inputnome"));

		assertEquals("Rodrigo Santiago Silva", nome.getAttribute("value"));

		if (!nome.getAttribute("value").equals("Rodrigo Santiago Silva")) {
			System.out.println(nome.getText());
		} else {
			System.out.println(nome.getText() + " SIMMMM");
		}
	}
}
