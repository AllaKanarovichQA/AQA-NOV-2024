package org.prog.cucumber.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.prog.selenium.pages.AlloUaPage;
import org.prog.selenium.pages.GooglePage;
import org.prog.testng.AlloUaTestNG;
import org.prog.testng.SQLHomeWork;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.*;

public class AlloSteps {

    public static AlloUaPage alloUaPage;

    @Given("I load allo.ua page")
    public void loadPage() {
        alloUaPage.loadPage();
    }
    @Given("I accept Allo cookies if present")
    public void acceptPresentCookies() {
        if (alloUaPage.isCookiePresent()) {
            alloUaPage.acceptCookies();
        }
    }
    @When("I search for samsung")
    public void searchForSamsung() {
        alloUaPage.setSearchInputText("samsung");
        alloUaPage.executeSearch();
    }
    @Then("I see 3 first phones")
    public void iSeeThreeFirstPhones() {
        List<WebElement> searchResults = alloUaPage.getSearchHeaders();

        if (searchResults.size() < 3) {
            throw new AssertionError("Expected at least 3 phones, but found: " + searchResults.size());
        }

        System.out.println("First 3 phones:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Phone " + (i + 1) + ": " + searchResults.get(i).getText());
        }
    }

    @Then("I add these 3 first phones to the database")
    public static void iAddThese3FirstPhonesToTheDatabase() {
        List<WebElement> searchResults = alloUaPage.getSearchHeaders();
        int limit = Math.min(3, searchResults.size());

        SQLHomeWork sqlHomeWork = new SQLHomeWork();

        for (int i = 0; i < limit; i++) {
            WebElement phoneElement = searchResults.get(i);

            String phoneName = phoneElement.getText();
            String phoneCode = alloUaPage.getCodePhoneWithScroll(phoneElement);

            if (phoneCode != null) {
                System.out.println("Телефон №" + (i + 1) + ": " + phoneName);
                System.out.println("Код товару: " + phoneCode);
                sqlHomeWork.savePhoneToDatabase(phoneName, phoneCode);
            } else {
                System.err.println("Код товару для телефону №" + (i + 1) + " не знайдено.");
            }
        }
    }

}








