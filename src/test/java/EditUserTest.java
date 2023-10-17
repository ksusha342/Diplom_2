import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.*;
import org.junit.*;


public class EditUserTest {

    private static final UserClient client = new UserClient();
    private static final UserCheck check = new UserCheck();
    protected static User user;
    private static String accessToken;
    private final String additionalSymbol = "s";

    @Before
    public void createUser() {
        user = UserGenerator.random();
        ValidatableResponse response = client.create(user);
        accessToken = check.createdUserSuccessfully(response);
    }

    @Test
    @DisplayName("Edit user email with authorization successfully")
    @Description("Check that it is possible to edit user email with authorization")
    public void editUserEmailSuccessfully() {
        user.setEmail(additionalSymbol + user.getEmail());
        ValidatableResponse response = client.editWithAutorization(user, accessToken);
        check.editUserDataSuccessfully(response);
    }

    @Test
    @DisplayName("Edit user password with authorization successfully")
    @Description("Check that it is possible to edit user password with authorization")
    public void editUserPasswordSuccessfully() {
        user.setPassword(additionalSymbol + user.getPassword());
        ValidatableResponse response = client.editWithAutorization(user, accessToken);
        check.editUserDataSuccessfully(response);
    }

    @Test
    @DisplayName("Edit user name with authorization successfully")
    @Description("Check that it is possible to edit user password with authorization")
    public void editUserNameSuccessfully() {
        user.setName(additionalSymbol + user.getName());
        ValidatableResponse response = client.editWithAutorization(user, accessToken);
        check.editUserDataSuccessfully(response);
    }

    @Test
    @DisplayName("Edit user data with authorization successfully")
    @Description("Check that it is possible to edit user data with authorization")
    public void editUserDataSuccessfully() {
        user.setEmail(additionalSymbol + user.getEmail());
        user.setPassword(additionalSymbol + user.getPassword());
        user.setName(additionalSymbol + user.getName());
        ValidatableResponse response = client.editWithAutorization(user, accessToken);
        check.editUserDataSuccessfully(response);
    }

    @Test
    @DisplayName("Set existing user email with authorization unsuccessfully")
    @Description("Check that it is impossible to set existing user email with authorization")
    public void setExistingUserEmailUnsuccessfully() {
        var existingUser = UserGenerator.random();
        ValidatableResponse existingUserResponse = client.create(existingUser);
        var existingUserAccessToken = check.createdUserSuccessfully(existingUserResponse);

        user.setEmail(existingUser.getEmail());
        ValidatableResponse response = client.editWithAutorization(user, accessToken);
        check.editUserDataForbidden(response);

        check.deletedUserSuccessfully(client.delete(existingUserAccessToken));
    }

    @Test
    @DisplayName("Edit user email without authorization unsuccessfully")
    @Description("Check that it is impossible to edit user email without authorization")
    public void editUserEmailUnsuccessfully() {
        user.setEmail(additionalSymbol + user.getEmail());
        ValidatableResponse response = client.editWithoutAutorization(user);
        check.editUserDataUnsuccessfully(response);
    }

    @Test
    @DisplayName("Edit user password without authorization unsuccessfully")
    @Description("Check that it is impossible to edit user password without authorization")
    public void editUserPasswordUnsuccessfully() {
        user.setPassword(additionalSymbol + user.getPassword());
        ValidatableResponse response = client.editWithoutAutorization(user);
        check.editUserDataUnsuccessfully(response);
    }

    @Test
    @DisplayName("Edit user name without authorization unsuccessfully")
    @Description("Check that it is impossible to edit user name without authorization")
    public void editUserNameUnsuccessfully() {
        user.setName(additionalSymbol + user.getName());
        ValidatableResponse response = client.editWithoutAutorization(user);
        check.editUserDataUnsuccessfully(response);
    }

    @Test
    @DisplayName("Edit user data without authorization unsuccessfully")
    @Description("Check that it is impossible to edit user data without authorization")
    public void editUserDataUnsuccessfully() {
        user.setEmail(additionalSymbol + user.getEmail());
        user.setPassword(additionalSymbol + user.getPassword());
        user.setName(additionalSymbol + user.getName());
        ValidatableResponse response = client.editWithoutAutorization(user);
        check.editUserDataUnsuccessfully(response);
    }

    @After
    public void deleteUser() {
        if (accessToken == null) {
            return;
        }
        check.deletedUserSuccessfully(client.delete(accessToken));
    }
}
