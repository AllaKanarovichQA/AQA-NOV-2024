// add method which checks goods id on position X
// if X < 0 -> throw exception
// if X > goods count -> throw exception
// if 0 <= X <= goods_amount -> scroll to item, then print goods id for that item
package org.prog.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        return new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(("//div[@class='product-card']//a[@class='product-card__title']")), 0));
    }


    public void setPhonePosition(int position){
        if (position<0) {
            throw new IllegalArgumentException("Спробуй ще раз (порядковий номер не може бути меньше 0");
        }
        List<WebElement> allPhones = getSearchHeaders();
        if(position>=allPhones.size()){
            throw new IllegalArgumentException("Не виявлено стільки позицій телефонів");
        }

    }


}


