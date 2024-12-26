package org.prog.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.prog.selenium.pages.AlloUaPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AlloUaTestNG {

    private WebDriver driver;
    private AlloUaPage alloUaPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        alloUaPage = new AlloUaPage(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void searchingByTest() {
        alloUaPage.loadPageAndAcceptCookiesIfPresent();
        alloUaPage.setSearchInputText("samsung");
        alloUaPage.executeSearch();

        List<WebElement> searchHeaders = alloUaPage.getSearchHeaders();
        int limit = Math.min(3, searchHeaders.size());

        for (int i = 0; i < limit; i++) {
            WebElement phoneElement = searchHeaders.get(i);

            String phoneName = phoneElement.getText();
            System.out.println("Телефон №" + (i + 1) + ": " + phoneName);

            String phoneCode = alloUaPage.getCodePhoneWithScroll(phoneElement);
            if (phoneCode != null) {
                System.out.println("Код товару: " + phoneCode);
            } else {
                System.out.println("Код товару для телефону №" + (i + 1) + " не знайдено.");
            }
        }

    }
    }



