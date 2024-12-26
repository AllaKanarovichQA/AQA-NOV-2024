package org.prog.testng;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.prog.selenium.pages.AlloUaPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.beans.Statement;
import java.sql.*;
import java.util.List;

//TODO: Add table to database which contains PhoneId, PhoneName, GoodsId
//TODO: In Allo.ua search for iphone, and store first three devices name
// and код товару (GoodsId) to database
// TODO: * - check if this code and name are already present in database. If its present - do nothing
public class SQLHomeWork {
    // 1. open allo.ua
    // 2. search for iphone (or whatever)
    // 3. Get first 3 items
    // 4. Get name and id for those items
    // 5. insert into database for each
    // * - before insert for each - select from DB where name & id


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
    public void savePhoneToDatabase(String phoneName, String goodsId) {

        String insertQuery = "INSERT INTO Phones (PhoneName, GoodsId) VALUES (?, ?)";
        String checkQuery = "SELECT COUNT(*) FROM Phones WHERE PhoneName = ? AND GoodsId = ?";


        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "password");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        ) {

            checkStatement.setString(1, phoneName);
            checkStatement.setString(2, goodsId);

            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                System.out.println("Телефон вже існує у базі даних: ");
            } else {
                preparedStatement.setString(1, phoneName);
                preparedStatement.setString(2, goodsId);

                preparedStatement.executeUpdate();
                System.out.println("Дані успішно додані до бази даних: ");
            }

        } catch (SQLException e) {
            System.err.println("Помилка під час запису даних у базу: " + e.getMessage());
        }
    }

    @Test
    public void searchingByTest() {
        alloUaPage.loadPageAndAcceptCookiesIfPresent();
        alloUaPage.setSearchInputText("Samsung");
        alloUaPage.executeSearch();

        List<WebElement> searchHeaders = alloUaPage.getSearchHeaders();
        int limit = Math.min(3, searchHeaders.size());

        for (int i = 0; i < limit; i++) {
            WebElement phoneElement = searchHeaders.get(i);

            Actions actions = new Actions(driver);
            actions.moveToElement(phoneElement).perform();

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", phoneElement);

            String phoneName = phoneElement.getText();
            System.out.println("Телефон №" + (i + 1) + ": " + phoneName);

            String phoneCode = alloUaPage.getCodePhone(phoneElement);
            if (phoneCode != null) {
                System.out.println("Код товару: " + phoneCode);

                savePhoneToDatabase(phoneName, phoneCode);
            } else {
                System.out.println("Код товару для телефону №" + (i + 1) + " не знайдено.");
            }
        }
    }}





