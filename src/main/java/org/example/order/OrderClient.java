package org.example.order;

import io.restassured.response.ValidatableResponse;
import org.example.Client;


public class OrderClient extends Client {
    public static final String ORDER_CREATE_PATH = "/orders";
    public static final String INGREDIENTS_LIST_PATH = "/ingredients";
    public static final String ORDER_LIST_PATH = "/orders";

    public ValidatableResponse createOrderWithoutAuthorization(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_CREATE_PATH)
                .then().log().all();
    }
    public ValidatableResponse createOrderWithAuthorization(Order order, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_CREATE_PATH)
                .then().log().all();
    }

    public ValidatableResponse getIngredientsForCreatingOrder() {
        return spec()
                .when()
                .get(INGREDIENTS_LIST_PATH)
                .then().log().all();
    }

    public ValidatableResponse getOrderListWithAuthorization(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_LIST_PATH)
                .then().log().all();
    }

    public ValidatableResponse getOrderListWithoutAuthorization() {
        return spec()
                .when()
                .get(ORDER_LIST_PATH)
                .then().log().all();
    }
}
