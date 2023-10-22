import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.*;
import org.example.user.*;
import org.junit.*;

import java.util.List;

public class GetOrderListTest {
    private static final UserClient client = new UserClient();
    private static final UserCheck check = new UserCheck();
    private static final OrderClient orderClient = new OrderClient();
    private static final OrderCheck orderCheck = new OrderCheck();
    protected Order order;
    private static List<String> ingredients;
    protected static User user;
    private static String accessToken;

    @BeforeClass
    public static void setup() {
        createUser();
        getIngredients();
    }

    @Before
    public void createOrder() {
        order = new Order(ingredients);
    }

    @Test
    @DisplayName("Get list of user orders with authorization successfully")
    @Description("Check that it is possible to get list of user orders with authorization")
    public void getOrderListTestWithAuthorizationSuccessfully(){
        ValidatableResponse response = orderClient.createOrderWithAuthorization(order, accessToken);
        orderCheck.createdOrderSuccessfully(response);

        var getOrderListResponse = orderClient.getOrderListWithAuthorization(accessToken);
        orderCheck.getOrderListSuccessfully(getOrderListResponse);
    }

    @Test
    @DisplayName("Get list of user orders without authorization unsuccessfully")
    @Description("Check that it is impossible to get list of user orders without authorization")
    public void getOrderListTestWithoutAuthorizationUnsuccessfully(){
        ValidatableResponse response = orderClient.createOrderWithoutAuthorization(order);
        orderCheck.createdOrderSuccessfully(response);

        var getOrderListResponse = orderClient.getOrderListWithoutAuthorization();
        orderCheck.getOrderListUnsuccessfully(getOrderListResponse);
    }

    @AfterClass
    public static void deleteUser() {
        if (accessToken == null) {
            return;
        }
        check.deletedUserSuccessfully(client.delete(accessToken));
    }

    private static void createUser() {
        user = UserGenerator.random();
        ValidatableResponse response = client.create(user);
        accessToken = check.createdUserSuccessfully(response);
    }

    private static void getIngredients() {
        ValidatableResponse response = orderClient.getIngredientsForCreatingOrder();
        ingredients = orderCheck.getIngredientSuccessfully(response);
    }
}

