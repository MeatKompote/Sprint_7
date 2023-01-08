import Requests.PostRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.ColoursJson;
import pojo.CreateOrderJson;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    // переменная для BaseURI
    String baseURI = "http://qa-scooter.praktikum-services.ru";

    // переменные для эндпоинтов
    String endpointCreateOrder = "/api/v1/orders";

    // создание запросов
    PostRequest postRequest = new PostRequest(baseURI);

    // инициализация полей для параметризации
    private final List<ColoursJson> listOfColours;
    private final int responseCode;

    // переменные для JSON
    String firstName = "Istomin";
    String lastName = "Kompote";
    String address = "Street";
    int metroStation = 5;
    String phone = "8 888 888 88 88";
    int rentTime = 6;
    String deliveryDate = "2020-06-06";
    String comment = "Comment";


    public CreateOrderTest(List<ColoursJson> listOfColours, int responseCode) {
        this.listOfColours = listOfColours;
        this.responseCode = responseCode;
    }

    @Parameterized.Parameters
    public static Object[][] getListOfColours() {
        ColoursJson blackColourJson = new ColoursJson("BLACK");
        ColoursJson greyColourJson = new ColoursJson("GREY");
        List<ColoursJson> emptyList = null;
        List<ColoursJson> blackColour = Arrays.asList(blackColourJson);
        List<ColoursJson> greyColour = Arrays.asList(greyColourJson);
        List<ColoursJson> blackAndGreyColours = Arrays.asList(blackColourJson, greyColourJson);
        return new Object[][] {
                {emptyList, 201},
                {blackColour, 201},
                {greyColour, 201},
                {blackAndGreyColours, 201}
        };
    }

    @Test
    public void createOrderReturnsTrackNumberAndCorrectStatusCode() {
        CreateOrderJson createCourierJson = new CreateOrderJson(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, listOfColours);
        postRequest.sendPostRequest(createCourierJson, endpointCreateOrder)
                .then()
                .assertThat().body("track", notNullValue())
                .and()
                .statusCode(responseCode);
    }
}
