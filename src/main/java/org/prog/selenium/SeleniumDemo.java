package org.prog.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.prog.selenium.pages.AlloUaPage;

import java.util.List;

public class SeleniumDemo {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        AlloUaPage alloUaPage = new AlloUaPage(driver);

        try {
            executeAlloSearch(alloUaPage, "Iphone");
            alloUaPage.setPhonePosition(4);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public static void executeAlloSearch(AlloUaPage alloUaPage, String phoneName) {
        alloUaPage.loadPageAndAcceptCookiesIfPresent();
        alloUaPage.setSearchInputText(phoneName);
        alloUaPage.executeSearch();
        List<WebElement> searchHeaders = alloUaPage.getSearchHeaders();


        if (searchHeaders.isEmpty()) {
            System.out.println("Oooops, something went wrong >_<");

        } else {
            String firstIphoneName = searchHeaders.get(2).getText();
            System.out.println("Назва першого айфона: " + firstIphoneName);

        }
    }
}
