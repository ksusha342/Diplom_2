import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.*;
import org.junit.*;


public class LoginUserTest {

    private static final UserClient client = new UserClient();
    private static final UserCheck check = new UserCheck();
    protected static User user;
    protected Credentials credentials;
    private static String accessToken;
    private final String additionalSymbol = "s";

    @BeforeClass
    public static void createUser() {
        user = UserGenerator.random();
        ValidatableResponse response = client.create(user);
        accessToken = check.createdUserSuccessfully(response);
    }

    @Before
    public void createCredentials() {
        credentials = Credentials.from(user);
    }

    @Test
    @DisplayName("Check user login")
    @Description("Basic user login test")
    public void loginUserSuccessfully() {
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Login user with incorrect email field")
    @Description("Check that it is impossible to login user with incorrect email field")
    public void loginUserWithIncorrectLogin() {
        credentials.setEmail(additionalSymbol + credentials.getEmail());
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInUnsuccessfullyWithWrongCredentials(loginResponse);
    }

    @Test
    @DisplayName("Login user with incorrect password field")
    @Description("Check that it is impossible to login user with incorrect password field")
    public void loginUserWithIncorrectPassword() {
        credentials.setPassword(additionalSymbol + credentials.getPassword());
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInUnsuccessfullyWithWrongCredentials(loginResponse);
    }

    @Test
    @DisplayName("Login user with with non-existent credentials")
    @Description("Check that it is impossible to login user with non-existent credentials")
    public void loginUserWithNonexistentUser() {
        credentials.setEmail(additionalSymbol + credentials.getEmail());
        credentials.setPassword(additionalSymbol + credentials.getPassword());
        ValidatableResponse loginResponse = client.login(credentials);
        check.loggedInUnsuccessfullyWithWrongCredentials(loginResponse);
    }

    @AfterClass
    public static void deleteUser() {
        if (accessToken == null) {
            return;
        }
        check.deletedUserSuccessfully(client.delete(accessToken));
    }
}
