package org.example.order;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import javax.net.ssl.HttpsURLConnection;

import java.util.List;

import static org.hamcrest.Matchers.is;


public class OrderCheck {
    public void createdOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    public void createdOrderUnsuccessfullyWithBadRequest(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_BAD_REQUEST)
                .body("success", is(false));
    }

    public void createdOrderUnsuccessfullyWithInternalError(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_INTERNAL_ERROR);
    }

    public List<String> getIngredientSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .body("success", is(true));

        JsonPath jsonPath = response.extract().jsonPath();

        String id1 = jsonPath.getString("data._id[0]");
        String id2 = jsonPath.getString("data._id[1]");

        return List.of(id1, id2);
    }
    public void getOrderListSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    public void getOrderListUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false));
    }
}
