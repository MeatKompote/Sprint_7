package Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AbstractPojo;
import pojo.CreateCourierJson;

import static io.restassured.RestAssured.given;

public class PostRequest {

    public PostRequest(String baseURI) {
            RestAssured.baseURI = baseURI;
    }

    public Response sendPostRequest (AbstractPojo Json, String endpoint) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(Json)
                .when()
                .post(endpoint);
        return response;
    }
}
