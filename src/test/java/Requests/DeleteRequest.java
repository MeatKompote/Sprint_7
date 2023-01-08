package Requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AbstractPojo;

import static io.restassured.RestAssured.given;

public class DeleteRequest {

    public DeleteRequest(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public Response sendDeleteRequest (String endpoint) {
        Response response = given()
                .when()
                .delete(endpoint);
        return response;
    }
}
