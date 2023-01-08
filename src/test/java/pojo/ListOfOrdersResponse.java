package pojo;

import java.util.List;

public class ListOfOrdersResponse {

    private List<OrderResponseJson> orders;

    public ListOfOrdersResponse(List<OrderResponseJson> orders) {
        this.orders = orders;
    }

    public ListOfOrdersResponse() {
    }

    public List<OrderResponseJson> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderResponseJson> orders) {
        this.orders = orders;
    }
}
