package org.prog.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumDemo {

    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            driver = new ChromeDriver();
            driver.get("https://allo.ua/");

            WebElement searchInput = driver.findElement(By.name("search"));
            searchInput.sendKeys("Iphone");
            searchInput.sendKeys(Keys.ENTER);

            List<WebElement> searchHeaders = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(("//div[@class='product-card']//a[@class='product-card__title']")), 0));

            if (searchHeaders.isEmpty()) {
                System.out.println("Oooops, something went wrong >_<");

            } else {
                String firstIphoneName = searchHeaders.get(0).getText();
                System.out.println("Назва першого айфона: " + firstIphoneName);
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

    }
}
