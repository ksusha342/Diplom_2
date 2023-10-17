package org.example.user;

import io.restassured.response.ValidatableResponse;
import org.example.Client;


public class UserClient extends Client {
    public static final String USER_CREATE_PATH = "/auth/register";
    public static final String USER_LOGIN_PATH = "/auth/login";
    public static final String USER_DELETE_PATH = "/auth/user";
    public static final String USER_EDIT_PATH = "/auth/user";

    public ValidatableResponse create(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_CREATE_PATH)
                .then().log().all();
    }

    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(USER_LOGIN_PATH)
                .then().log().all();
    }

    public ValidatableResponse delete(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_DELETE_PATH)
                .then().log().all();
    }

    public ValidatableResponse editWithAutorization(User user, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_EDIT_PATH)
                .then().log().all();
    }

    public ValidatableResponse editWithoutAutorization(User user) {
        return spec()
                .body(user)
                .when()
                .patch(USER_EDIT_PATH)
                .then().log().all();
    }
}
