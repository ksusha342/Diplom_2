package org.example.user;

import io.restassured.response.ValidatableResponse;

import javax.net.ssl.HttpsURLConnection;

import static org.hamcrest.Matchers.is;

public class UserCheck {

    public String createdUserSuccessfully(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .body("success", is(true))
                .extract()
                .path("accessToken");
    }
    public void createdUserUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false));
    }

    public void createdUserUnsuccessfullyWithoutRequiredFields(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false));
    }

    public void loggedInSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    public void loggedInUnsuccessfullyWithWrongCredentials(ValidatableResponse loginResponse) {
        loginResponse
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false));
    }

    public void deletedUserSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_ACCEPTED)
                .body("success", is(true));
    }

    public void editUserDataSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    public void editUserDataForbidden(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false));
    }

    public void editUserDataUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false));
    }

}
