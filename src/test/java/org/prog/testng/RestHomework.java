package org.prog.testng;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.prog.selenium.dto.ResultsDto;
import org.testng.Assert;
import org.testng.annotations.Test;


public class RestHomework {

    @Test
    public void restHomework() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("inc", "gender,name,nat,location");
        requestSpecification.queryParam("noinfo");
        requestSpecification.baseUri("https://randomuser.me/");
        requestSpecification.basePath("/api/");

        Response response = requestSpecification.get();

        ValidatableResponse validatableResponse = response.then();
        validatableResponse.contentType(ContentType.JSON);
        validatableResponse.statusCode(200);
        validatableResponse.statusLine("HTTP/1.1 200 OK");

        validatableResponse.body("results[0].location.city", Matchers.notNullValue());

//        Assert.assertNotNull (response.jsonPath().get("results[0].location.city").toString(),"Назва міста не може бути порожньою!");

//        ResultsDto dto = response.as(ResultsDto.class);
        //       Assert.assertNotNull(dto.getResults().get(0).getLocation().getCity(), "Назва міста не може бути порожньою!");


        String timezone = validatableResponse.extract().jsonPath().getString("results[0].location.timezone.description");
        System.out.println("Опис часового поясу: " + timezone);

    }
}