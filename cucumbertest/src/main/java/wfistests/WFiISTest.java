package wfistests;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.net.MalformedURLException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Krzysztof Podlaski on 21.05.2018.
 */
public class WFiISTest {
    WebDriver driver = null;
    @Given("^I am on WFiIS web page$")
    public void goToWFiIS() throws MalformedURLException {
        System.setProperty(
                "webdriver.gecko.driver",
                "D:\\Programowanie\\gecodriver\\geckodriver.exe");
        FirefoxOptions fxOptions = new FirefoxOptions();
        FirefoxProfile fxProfile = new FirefoxProfile();
        fxProfile.setPreference("browser.download.folderList",2);
        fxProfile.setPreference("browser.download.manager.showWhenStarting",false);
        fxProfile.setPreference("browser.download.dir","D:\\Users\\Krzysztof Podlaski\\Downloads");
        fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf");
        fxOptions.setProfile(fxProfile);
        driver = new FirefoxDriver(fxOptions);
        driver.navigate().to("https://wfiis.uni.lodz.pl");
    }

    @When("^I click on DlaStudentow$")
    public void selectDlaStudentow() {
        WebElement element = driver.findElement(By.id("menu-item-182"));
        element.findElement(By.tagName("a")).click();
    }

    @When ("^I choose Plany Zajec$")
    public void choosePlanyZajec() {
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://wfiis.uni.lodz.pl/wfis-main/dla-studentow");
        WebElement element = driver.findElement(By.className("page-item-37"));
        element.findElement(By.tagName("a")).click();
    }

    @When("^I select Informatyka stacjonarne$")
    public void selectStacjonarne() {
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://wfiis.uni.lodz.pl/wfis-main/dla-studentow/plany");
        WebElement element = driver.findElement(By.className("page-item-8412"));
        element.findElement(By.tagName("a")).click();
    }

    @Then("^I can see list of files to download$")
    public void checkListOfFiles() {
        WebElement results
                = driver.findElement(By.id("container"));
        assertTrue(results.getText()
                .contains("Informatyka, specjalność: informatyka stosowana (studia inżynierskie)"));
        assertTrue(results.getText().
                contains("Studia II stopnia"));
        assertFalse(results.getText().contains("fizyka"));
    }

    @Then("^I can download a plan file$")
    public void fileDownloadTest(){
        WebElement element = driver.findElement(By.className("entry-content"));
        List<WebElement> els = element.findElements(By.tagName("a"));
        els.get(0).click();
        driver.close();
    }
}
