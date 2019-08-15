package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class loginStepDef {
    WebDriver driver = Browser.launch();

    public loginStepDef() throws MalformedURLException {
    }

    @Given("^I go to \"([^\"]*)\"$")
    public void i_go_to(String url) throws Throwable {
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Given("^I set user name with \"([^\"]*)\"$")
    public void i_set_user_name_with(String username) throws Throwable {
        driver.findElement(By.id("logina")).sendKeys(username);
    }

    @Given("^I set password with \"([^\"]*)\"$")
    public void i_set_password_with(String password) throws Throwable {
        driver.findElement(By.id("clavea")).sendKeys(password);
    }

    @Then("^I click on login button$")
    public void i_click_on_login_button() throws Throwable {

        driver.findElement(By.xpath("//*[@id=\"frmAcceso\"]/div[3]/div[2]/button")).click();
    }

    @Then("^I should see \"([^\"]*)\" message$")
    public void i_should_see_message(String expectedMesage) throws Throwable {
        String message = driver.findElement(By.xpath("/html/body/div/header/a/span[2]")).getText();
        Assert.assertTrue(message.contains(expectedMesage));
    }

    @Then("^I should see \"([^\"]*)\" header$")
    public void i_should_see_header(String expectedHeader) throws Throwable {
        String header = driver.findElement(By.xpath("/html/body/div/div[2]/p")).getText();
        Assert.assertTrue(header.contains(expectedHeader));
    }

}
