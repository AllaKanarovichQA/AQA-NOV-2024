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

        String firstPhoneName = searchHeaders.get(0).getText();
        System.out.println("Назва першого телефону: " + firstPhoneName);
    }

    @Test
    public void setPhonePositionTest() {
        int position = 3;

        List<WebElement> searchHeaders = alloUaPage.getSearchHeaders();

        Assert.assertTrue(position > 0, "Спробуй ще раз (порядковий номер не може бути меньше 1)");
        Assert.assertTrue(position <= searchHeaders.size(), "Не виявлено стільки позицій телефонів. Максимальна доступна позиція: " + searchHeaders.size());
        alloUaPage.setPhonePosition(position);

    }
}
