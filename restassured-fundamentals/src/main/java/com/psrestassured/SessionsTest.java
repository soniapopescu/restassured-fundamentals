package com.psrestassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SessionsTest {

    @Test
    public void crudTest() {
        Response responseGet = RestAssured.get("http://localhost:8080/api/v1/sessions");

        //1. GET all sessions
        RestAssured.get("http://localhost:8080/api/v1/sessions")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
                //.body("findAll {d -> d.session_length = 60}.name", Matchers.hasItems("Jens", "Linda"));

        //1.1 Count elements in response
        ResponseBody<?> responseBody = responseGet.body();
        JsonPath jPath = responseBody.jsonPath();
        List<Object> idList = jPath.get("session_id");
        //Assert.assertEquals(idList.size(), 86);


        //2. POST
        RestAssured.baseURI = "http://localhost:8080/api/v1/sessions";

        String requestParams = "{\n" +
                "    \"session_id\": 0,\n" +
                "    \"session_name\": \"Cucumber\",\n" +
                "    \"session_description\": \"abc\",\n" +
                "    \"session_length\": 60,\n" +
                "    \"speakers\": [\n" +
                "        {\n" +
                "            \"speaker_id\": 4,\n" +
                "            \"first_name\": \"Lori\",\n" +
                "            \"last_name\": \"Vanhoose\",\n" +
                "            \"title\": \"Java Technical Lead\",\n" +
                "            \"company\": \"National Bank\",\n" +
                "            \"speaker_bio\": \"Test\",\n" +
                "            \"speaker_photo\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //Fara .header() intoarce 415 status code!!
        Response responsePost = RestAssured.given()
                .header("Content-type", "application/json")
                .body(requestParams)
                .post("http://localhost:8080/api/v1/sessions");
        Assert.assertEquals(responsePost.getStatusCode(), 200);

        //Same result:
//      RestAssured.given()
//              .header("Content-type", "application/json")
//              //.body("{\"session_id\":92,\"session_name\":\"Cucumber\",\"session_description\":\"Some text\",\"session_length\":60, \"speakers\":[{\"speaker_id\":4,\"first_name\":\"Lori\",\"last_name\":\"Vanhoose\",\"title\":\"JavaTechnicalLead\",\"company\":\"NationalBank\",\"speaker_bio\":\"Test\",\"speaker_photo\":null}]}")
//              .body(requestParams)
//              .when()
//              .post("http://localhost:8080/api/v1/sessions")
//              .then()
//              .assertThat()
//              .statusCode(200);

        //3. GET the session with inserted id and check status
        ResponseBody<?> body = responsePost.body();
        JsonPath jsonPath = body.jsonPath();
        int insertedId = jsonPath.get("session_id");
        RestAssured.get("http://localhost:8080/api/v1/sessions/"
                                + insertedId)
                .then()
                .assertThat()
                .statusCode(200);
        //sau
        //Assert.assertEquals(responseGet.statusCode(), 200);

        //Object mapping for inserted session
        SessionObj session = RestAssured.get("http://localhost:8080/api/v1/sessions/" + insertedId)
                                        .as(SessionObj.class);
        Assert.assertEquals(session.getSession_name(), "Cucumber");
        Assert.assertEquals(session.getSpeakers().get(0).getFirst_name(), "Lori");


        //4. PUT for updating inserted session
        String putParams = "{\n" +
                "    \"session_id\":" + insertedId + ",\n" +
                "    \"session_name\": \"UPDATED\",\n" +
                "    \"session_description\": \"I updated this resource!!\",\n" +
                "    \"session_length\": 60,\n" +
                "    \"speakers\": [\n" +
                "        {\n" +
                "            \"speaker_id\": 21,\n" +
                "            \"first_name\": \"Linda\",\n" +
                "            \"last_name\": \"Carver\",\n" +
                "            \"title\": \"Senior Developer\",\n" +
                "            \"company\": \"Chicago Technology Research\",\n" +
                "            \"speaker_bio\": \"Test\",\n" +
                "            \"speaker_photo\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Response responsePut = RestAssured.given()
                .header("Content-type", "application/json")
                .body(putParams)
                .when()
                .put("http://localhost:8080/api/v1/sessions/"+insertedId);
        //responsePut.prettyPrint();
        Assert.assertEquals(responsePut.getStatusCode(), 200);
        Assert.assertTrue(responsePut.asString().contains("UPDATED"));

        //DELETE inserted session
        RestAssured.delete("http://localhost:8080/api/v1/sessions/"+insertedId)
                .then()
                .assertThat()
                .statusCode(200);

        RestAssured.get("http://localhost:8080/api/v1/sessions/"+insertedId)
                .then()
                .assertThat()
                .statusCode(500);

        //Reporter.log(String.valueOf(session.getSession_name()), true);
        //Reporter.log(session2.toString(), true);


    }

}
