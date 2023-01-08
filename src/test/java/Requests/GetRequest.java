package Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetRequest {

    public GetRequest(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public Response sendGetRequest (String endpoint) {
        Response response = given()
                .when()
                .get(endpoint);
        return response;
    }
}
