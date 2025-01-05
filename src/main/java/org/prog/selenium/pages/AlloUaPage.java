package org.prog.selenium.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AlloUaPage {

    private final WebDriver driver;

    public AlloUaPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loadPageAndAcceptCookiesIfPresent() {
        loadPage();
        if (isCookiePresent()) {
            acceptCookies();
        }
    }

    public void loadPage() {
        driver.get("https://allo.ua/");
        driver.manage().window().maximize();
    }

    public void acceptCookies() {
        driver.findElements(By.tagName("button")).get(4).click();
    }

    public boolean isCookiePresent() {
        List<WebElement> cookiesLink = driver.findElements(By.xpath("//a[contains(@href, 'technologies/cookies')]"));
        if (!cookiesLink.isEmpty()) {
            return cookiesLink.get(0).isDisplayed();
        }
        return false;
    }

    public void setSearchInputText(String value) {
        driver.findElement(By.name("search")).sendKeys(value);
    }

    public void executeSearch() {
        driver.findElement(By.name("search")).sendKeys(Keys.ENTER);
    }

    public List<WebElement> getSearchHeaders() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class='product-card']//a[@class='product-card__title']"), 0));
    }

    public String getCodePhoneWithScroll(WebElement phoneElement) {
        Actions actions = new Actions(driver);
        actions.moveToElement(phoneElement).perform();

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", phoneElement);

        return getCodePhone(phoneElement);
    }
    public String getCodePhone(WebElement phoneElement){
        try {
            WebElement codePlace = phoneElement.findElement(By.xpath("./ancestor::div[contains(@class, 'product-card')]"));
            WebElement phoneCode = codePlace.findElement(By.xpath(".//span[@class='product-sku__value']"));
            if (phoneCode.isDisplayed()) {
                return phoneCode.getText();
            }
        }catch(NoSuchElementException | StaleElementReferenceException e){
            return null;
        }
        return null;
    }
}