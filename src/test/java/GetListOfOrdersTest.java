import Requests.GetRequest;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetListOfOrdersTest {

    // переменная для BaseURI
    String baseURI = "http://qa-scooter.praktikum-services.ru";

    // переменные для эндпоинтов
    String endpointCreateCourier = "/api/v1/orders";

    // создание запросов
    GetRequest getRequest = new GetRequest(baseURI);


    @Test
    public void getListOfOrdersReturnsCorrectListOfOrders() {
        getRequest.sendGetRequest(endpointCreateCourier)
                .then()
                .assertThat().body("orders", notNullValue());
    }
}
