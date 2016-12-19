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

public class TestaTelaCadastroProduto {

	private static ChromeDriverService service;

	private static WebDriver driver;

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
		driver.get("http://localhost:8080/web/pages/xhtml/cadastroProduto.xhtml");

	}

	@AfterClass
	public static void tearDownTest() {
		driver.quit();
	}

	@Test
	public void testaTituloDaPagina() {
		assertEquals("Cadastro Estoque", driver.getTitle());
	}

	// Método que testa o login no site DevMedia.
	@Test
	public void testaPesquisaPorCodigo() {

		driver.get("http://localhost:8080/web/pages/xhtml/cadastroEstoque.xhtml");

		WebElement codigo = driver.findElement(By.id("j_idt10:inputVazioCodigo"));

		inserirValorWebElement(codigo, "3");

		driver.findElement(By.id("j_idt10:btpesquisar")).click();

		WebElement nome = driver.findElement(By.id("j_idt10:nomeEstoque"));

		assertEquals("Brahma garrafa", nome.getAttribute("value"));
	}

	@Test
	public void testaInsercaoSemDados() {

		driver.get("http://localhost:8080/web/pages/xhtml/cadastroEstoque.xhtml");

		driver.findElement(By.id("j_idt10:btsalvar")).click();
		WebElement label = driver.findElement(By.id("j_idt10:labelErroSalvar"));

		String erroEsperado = "Os dados Nome, Descrição, Categoria, subCategoria, qtdPorUnidade, Un de medida e qtd Critica são de preenchimento obrigatório!";

		assertEquals(erroEsperado, label.getText());

	}

	@Test
	public void testaInsercaoSemNome() {

		driver.get("http://localhost:8080/web/pages/xhtml/cadastroEstoque.xhtml");

		WebElement descricao = driver.findElement(By.id("j_idt10:inputDescricao"));
		WebElement categoria = driver.findElement(By.id("j_idt10:selectCategoria"));
		Select selectCategoria = new Select(categoria);

		WebElement unidadeMedida = driver.findElement(By.id("j_idt10:selectUnidadeMedida"));
		Select selectUnidadeMedida = new Select(unidadeMedida);

		inserirValorWebElement(descricao, "teste sem nome");
		selectCategoria.selectByValue("1");
		espera(1000);
		WebElement subCategoria = driver.findElement(By.id("j_idt10:selectSubCategoria"));
		Select selectSubCategoria = new Select(subCategoria);
		selectSubCategoria.selectByValue("1");
		espera(1000);
		selectUnidadeMedida.selectByIndex(2);

		driver.findElement(By.id("j_idt10:btsalvar")).click();
		WebElement label = driver.findElement(By.id("j_idt10:labelErroSalvar"));

		String erroEsperado = "Os dados Nome, Descrição, Categoria, subCategoria, qtdPorUnidade, Un de medida e qtd Critica são de preenchimento obrigatório!";

		assertEquals(erroEsperado, label.getText());

	}

	private void inserirValorWebElement(WebElement elem, String valor) {
		espera(1000);
		elem.click();
		elem.clear();
		elem.sendKeys(valor);
	}

	private void espera(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
