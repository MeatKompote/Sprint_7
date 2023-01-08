import Requests.DeleteRequest;
import Requests.PostRequest;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojo.CreateCourierJson;
import pojo.LoginCourierJson;
import pojo.LoginCourierResponseJson;

public class CreateCourierTest {

    // переменная для BaseURI
    String baseURI = "http://qa-scooter.praktikum-services.ru";

    // переменные для эндпоинтов
    String endpointCreateCourier = "/api/v1/courier";
    String endpointLoginCourier = "/api/v1/courier/login";
    String endpointDeleteCourier = "/api/v1/courier/";

    // создание запросов
    PostRequest postRequest = new PostRequest(baseURI);
    DeleteRequest deleteRequest = new DeleteRequest(baseURI);

    // переменные для JSON с корректными данными
    String courierName = "istominTestCourier2";
    String courierPassword = "123";
    String courierFirstName = "Kompote";


    // инициализация POJO с корректными данными
    CreateCourierJson correctCreateCourierJson = new CreateCourierJson(courierName, courierPassword, courierFirstName);
    LoginCourierJson correctLoginCourierJson = new LoginCourierJson(courierName, courierPassword);

    @After
    public void tearDown() {
        // запрос на логин курьера, чтобы получить id
        Response loginCourierResponse = postRequest.sendPostRequest(correctLoginCourierJson, endpointLoginCourier);
        if (loginCourierResponse.getStatusCode() == 200) {
            LoginCourierResponseJson courierLoginResponseJson = loginCourierResponse
                    .body()
                    .as(LoginCourierResponseJson.class);

            // запрос на удаление курьера по id
            String deleteCourierEndpoint = endpointDeleteCourier + courierLoginResponseJson.getId();
            deleteRequest.sendDeleteRequest(deleteCourierEndpoint)
                    .then()
                    .statusCode(200);
        }
    }

    @Test
    public void CreateCourierRequestReturnsCorrectStatusCode() {
        // запрос на создание курьера
        postRequest.sendPostRequest(correctCreateCourierJson, endpointCreateCourier)
                .then()
                .statusCode(201);
    }

    @Test
    public void CreateCourierRequestReturnsCorrectBody() {

        // запрос на создание курьера
        postRequest.sendPostRequest(correctCreateCourierJson, endpointCreateCourier)
                .then()
                .assertThat().body("ok", Matchers.equalTo(true));

    }

    @Test
    public void CreateCourierWithMissingCourierNameReturnsCorrectErrorMessageAndStatusCode() {
        // переменные для JSON
        String courierEmptyName = "";

        // инициализация POJO
        CreateCourierJson emptyNameCreateCourierJson = new CreateCourierJson(courierEmptyName, courierPassword, courierFirstName);

        // запрос на создание курьера
        postRequest.sendPostRequest(emptyNameCreateCourierJson, endpointCreateCourier)
                .then()
                .assertThat().body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void CreateCourierWithMissingCourierPasswordReturnsCorrectErrorMessageAndStatusCode() {
        // переменные для JSON
        String courierEmptyPassword = "";

        // инициализация POJO
        CreateCourierJson emptyPasswordCreateCourierJson = new CreateCourierJson(courierName, courierEmptyPassword, courierFirstName);

        // запрос на создание курьера
        postRequest.sendPostRequest(emptyPasswordCreateCourierJson, endpointCreateCourier)
                .then()
                .assertThat().body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void CreateCourierWithMissingCourierFirstNameReturnsCorrectBodyAndStatusCode() {
        // переменные для JSON
        String courierEmptyFirstName = "";

        // инициализация POJO
        CreateCourierJson emptyFirstNameCreateCourierJson = new CreateCourierJson(courierName, courierPassword, courierEmptyFirstName);


        // запрос на создание курьера
        postRequest.sendPostRequest(emptyFirstNameCreateCourierJson, endpointCreateCourier)
                .then()
                .assertThat().body("ok", Matchers.equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    public void CreateCourierWithDuplicateCourierNameReturnsCorrectErrorMessageAndStatusCode() {

        // запрос на создание курьера
        postRequest.sendPostRequest(correctCreateCourierJson, endpointCreateCourier)
                .then()
                .assertThat().body("ok", Matchers.equalTo(true))
                .and()
                .statusCode(201);

        // повторный запрос на создание курьера с тем же именем
        postRequest.sendPostRequest(correctCreateCourierJson, endpointCreateCourier)
                .then()
                .assertThat().body("message", Matchers.equalTo("Этот логин уже используется."))
                .and()
                .statusCode(409);
    }
 }
