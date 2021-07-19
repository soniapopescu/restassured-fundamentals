package com.psrestassured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class BasicTest {

    @Test
    public void someTest() {
        RestAssured.get("http://api.github.com")
                .then()
                .statusCode(200);
    }

    @Test
    public void peek() {
        Response peek = RestAssured.get("http://api.github.com")
                .peek();
        ResponseBody peek1 = peek.body().peek();
        String as = peek1.as(String.class);
        String x="";
    }

    @Test
    public void prettyPeekFunc() {
        RestAssured.get("http://api.github.com")
                .prettyPeek();
    }

    //PRINTS ONLY THE BODY OF THE RESPONSE
    @Test
    public void print() {
        RestAssured.get("http://api.github.com")
                .print();
    }

    @Test
    public void prettyPrintFunc() {
        RestAssured.get("http://api.github.com")
                .prettyPrint();
    }

    @Test
    public void convenienceMethods() {
        Response response = RestAssured.get("http://api.github.com");
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.getContentType(), "application/json, charset=utf-8");
    }

    @Test
    public void genericHeader() {
        Response response = RestAssured.get("http://api.github.com");
        assertEquals(response.getHeader("server"), "GitHub.com");
        assertEquals(response.getHeader("x-ratelimit-limit"), "60");
        //OR
        assertEquals(Integer.parseInt(response.getHeader("x-ratelimit-limit")), 60);
    }

    @Test
    public void getHeaders() {
        Response response = RestAssured.get("http://api.github.com");
        Headers headers = response.getHeaders();
        String val = headers.getValue("header1");
        int size = headers.size();
        List<Header> list = headers.asList();
        boolean isPresent = headers.hasHeaderWithName("header2");
        assertTrue(isPresent);
    }

    @Test
    public void basicValidatableExample() {
        RestAssured.get("http://api.github.com")
                .then()
                .assertThat()                       //asta e doar sintactic sugar
                    .statusCode(200)
                .and()                              //sintactic sugar
                    .contentType(ContentType.JSON)
                .and()                              //sintactic sugar
                .assertThat()                       //sintactic sugar
                    .header("x-ratelimit-limit", "60"); //nu merge Integer.ParseInt() daca fac chaining methods
    //NO ASSERTIONS. EVERYTHING HAPPENS WITHIN RESTASSURED!
    }

    @Test
    public void jsonPathReturnsMap() {
        Response response = RestAssured.get("http://api.github.com");

        ResponseBody<?> body = response.body();
        JsonPath jPath = body.jsonPath();

        JsonPath jPath2 = response.body().jsonPath();

        Map<String, String> fullJson = jPath2.get();
        Map<String, String> subMap = jPath2.get("resources");
        Map<String, String> subMap2 = jPath2.get("resources.core");

        int value1 = jPath.get("resources.core.limit");
        int value2 = jPath.get("resources.graphql.remaining");

        assertEquals(value1, 60);
        assertEquals(value2, 0);

    }
}
