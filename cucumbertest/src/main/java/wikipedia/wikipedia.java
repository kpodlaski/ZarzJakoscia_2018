package wikipedia;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Krzysztof Podlaski on 14.05.2018.
 */
public class wikipedia {

    private WebDriver driver;

    @Before
    public void before() {
        System.setProperty("webdriver.gecko.driver", "D:\\Programowanie\\gecodriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("http://en.wikipedia.org");
    }

    @After
    public void after() {
        //driver.quit();
    }

    @Given("^Enter search term \"(.*?)\"$")
    public void searchFor(String searchTerm) {
        WebElement searchField =
          driver.findElement(By.id("searchInput"));
        searchField.sendKeys(searchTerm);
    }
    @When("^Do search$")
    public void clickSearchButton() {
        WebElement searchButton
          = driver.findElement(By.id("searchButton"));
        searchButton.click();
    }
    @Then("^Single result is shown for '(.*?)'$")
    public void assertSingleResult(String searchResult) {
        WebElement results
         = driver.findElement(
         By.cssSelector("div#mw-content-text.mw-content-ltr p"));
        assertFalse(results.getText()
          .contains(searchResult +
                  " may refer to:"));
        assertTrue(results.getText().
                startsWith(searchResult));
    }


}
