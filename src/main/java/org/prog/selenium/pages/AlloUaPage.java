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
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class='product-card']//a[@class='product-card__title']"), 0));
    }

    public void setPhonePosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Спробуй ще раз (порядковий номер не може бути меньше 0)");
        }
        List<WebElement> allPhones = getSearchHeaders();
        if (position >= allPhones.size()) {
            throw new IllegalArgumentException("Не виявлено стільки позицій телефонів");
        }
        if (position <= allPhones.size()&&allPhones.size()>=0) {

            WebElement phoneElement = allPhones.get(position - 1);
            Actions actions = new Actions(driver);
            actions.moveToElement(phoneElement).perform();

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", phoneElement);

            System.out.println("Назва телефону на позиції " + position + ": " + phoneElement.getText());

            String codeValue = getCodePhone(phoneElement);
            if (codeValue != null) {
                System.out.println("Код товару: " + codeValue);
            } else {
                System.out.println("Код товару не знайдено для позиції " + position);
            }
        }
    }
    private String getCodePhone(WebElement phoneElement){
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

