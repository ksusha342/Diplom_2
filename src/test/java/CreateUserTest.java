import io.restassured.response.ValidatableResponse;
import org.example.user.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CreateUserTest {

    private final UserClient client = new UserClient();
    private final UserCheck check = new UserCheck();
    protected User user;
    private String accessToken;

    @Before
    public void createUser() {
        user = UserGenerator.random();
    }

    @Test
    @DisplayName("Check user creation")
    @Description("Basic user creation test")
    public void createUserSuccessfully() {
        ValidatableResponse response = client.create(user);

        accessToken = check.createdUserSuccessfully(response);
    }

    @Test
    @DisplayName("Check the creation of two identical users")
    @Description("Check that it is impossible to create two identical users")
    public void createTwoIdenticalUsers() {
        ValidatableResponse response = client.create(user);
        ValidatableResponse secondResponse = client.create(user);

        accessToken = check.createdUserSuccessfully(response);
        check.createdUserUnsuccessfully(secondResponse);
    }

    @Test
    @DisplayName("Check user creation without email field")
    @Description("Check that it is impossible to create user without email field")
    public void createUserWithoutEmail() {
        user.setEmail(null);

        ValidatableResponse response = client.create(user);
        check.createdUserUnsuccessfullyWithoutRequiredFields(response);
    }

    @Test
    @DisplayName("Check user creation without password field")
    @Description("Check that it is impossible to create user without password field")
    public void createUserWithoutPassword() {
        user.setPassword(null);

        ValidatableResponse response = client.create(user);
        check.createdUserUnsuccessfullyWithoutRequiredFields(response);
    }

    @Test
    @DisplayName("Check user creation without name field")
    @Description("Check that it is impossible to create user without name field")
    public void createUserWithoutName() {
        user.setName(null);

        ValidatableResponse response = client.create(user);
        check.createdUserUnsuccessfullyWithoutRequiredFields(response);
    }

    @After
    public void deleteUser() {
        if (accessToken == null) {
            return;
        }
        check.deletedUserSuccessfully(client.delete(accessToken));
    }
}
