import Requests.DeleteRequest;
import Requests.PostRequest;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.CreateCourierJson;
import pojo.LoginCourierJson;
import pojo.LoginCourierResponseJson;

import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {

    // переменная для BaseURI
    static String baseURI = "http://qa-scooter.praktikum-services.ru";

    // переменные для эндпоинтов
    static String endpointCreateCourier = "/api/v1/courier";
    static String endpointLoginCourier = "/api/v1/courier/login";
    static String endpointDeleteCourier = "/api/v1/courier/";

    // создание запросов
    static PostRequest postRequest = new PostRequest(baseURI);
    static DeleteRequest deleteRequest = new DeleteRequest(baseURI);

    // переменные для JSON
    static String courierName = "istominTestCourier3";
    static String courierPassword = "123";
    static String courierFirstName = "Kompote";
    static String courierId;

    // инициализация POJO
    static CreateCourierJson correctCreateCourierJson = new CreateCourierJson(courierName, courierPassword, courierFirstName);
    static LoginCourierJson correctLoginCourierJson = new LoginCourierJson(courierName, courierPassword);

    @BeforeClass
    public static void setUp() {

        // запрос на создание курьера
        postRequest.sendPostRequest(correctCreateCourierJson, endpointCreateCourier);
        // запрос на логин курьера, чтобы получить id
        Response loginCuorierResponse = postRequest.sendPostRequest(correctLoginCourierJson, endpointLoginCourier);
        if (loginCuorierResponse.getStatusCode() == 200) {
            LoginCourierResponseJson courierLoginResponseJson = loginCuorierResponse
                    .body()
                    .as(LoginCourierResponseJson.class);
            courierId = courierLoginResponseJson.getId();
        }
    }

    @AfterClass
    public static void tearDown() {
        // запрос на удаление курьера по id
        String deleteCourierEndpoint = endpointDeleteCourier + courierId;
        deleteRequest.sendDeleteRequest(deleteCourierEndpoint)
                .then()
                .statusCode(200);
    }

    @Test
    public void loginCourierReturnsCorrectStatusCode() {
        postRequest.sendPostRequest(correctLoginCourierJson, endpointLoginCourier)
                .then()
                .statusCode(200);
    }

    @Test
    public void loginCourierReturnsCorrectBody() {
        postRequest.sendPostRequest(correctLoginCourierJson, endpointLoginCourier)
                .then()
                .assertThat().body("id", notNullValue());
    }

    @Test
    public void loginCourierWithMissingLoginParameterReturnsCorrectErrorMessageAndStatusCode() {
        String missingLogin = "";
        LoginCourierJson missingLoginCourierJson = new LoginCourierJson(missingLogin, courierPassword);
        postRequest.sendPostRequest(missingLoginCourierJson, endpointLoginCourier)
                .then()
                .assertThat()
                .body("message", Matchers.equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void loginCourierWithMissingPasswordParameterReturnsCorrectErrorMessageAndStatusCode() {
        String missingPassword = "";
        LoginCourierJson missingPasswordCourierJson = new LoginCourierJson(courierName, missingPassword);
        postRequest.sendPostRequest(missingPasswordCourierJson, endpointLoginCourier)
                .then()
                .assertThat()
                .body("message", Matchers.equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void loginCourierWithIncorrectLoginReturnsCorrectErrorMessageAndStatusCode() {
        String incorrectLogin = "IstominIncorrectLogin";
        LoginCourierJson missingLoginCourierJson = new LoginCourierJson(incorrectLogin, courierPassword);
        postRequest.sendPostRequest(missingLoginCourierJson, endpointLoginCourier)
                .then()
                .assertThat()
                .body("message", Matchers.equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void loginCourierWithIncorrectPasswordReturnsCorrectErrorMessageAndStatusCode() {
        String incorrectPassword = "incorrectPassword";
        LoginCourierJson missingLoginCourierJson = new LoginCourierJson(courierName, incorrectPassword);
        postRequest.sendPostRequest(missingLoginCourierJson, endpointLoginCourier)
                .then()
                .assertThat()
                .body("message", Matchers.equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

 }
