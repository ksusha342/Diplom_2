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
                .body("message", is("User already exists"));
    }

    public void createdUserUnsuccessfullyWithoutRequiredFields(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_FORBIDDEN)
                .body("message", is("Email, password and name are required fields"));
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
                .body("message", is("email or password are incorrect"));
    }

    public void deletedUserSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpsURLConnection.HTTP_ACCEPTED)
                .body("message", is("User successfully removed"));
    }
}
