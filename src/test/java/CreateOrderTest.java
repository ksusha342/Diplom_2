import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderCheck;
import org.example.order.OrderClient;
import org.example.user.*;
import org.junit.*;

import java.util.Arrays;
import java.util.List;


public class CreateOrderTest {
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
    @DisplayName("Create order with authorization successfully")
    @Description("Check that it is possible to create order with authorization")
    public void createOrderWithAuthorizationSuccessfully() {
        ValidatableResponse response = orderClient.createOrderWithAutorization(order, accessToken);
        orderCheck.createdOrderSuccessfully(response);
    }

    @Test
    @DisplayName("Create order without authorization unsuccessfully")
    @Description("Check that it is impossible to create order without authorization")
    public void createOrderWithoutAuthorizationSuccessfully() {
        ValidatableResponse response = orderClient.createOrderWithoutAutorization(order);
        orderCheck.createdOrderUnsuccessfullyWithoutAutorization(response);
    }

    @Test
    @DisplayName("Create order without ingredients unsuccessfully")
    @Description("Check that it is impossible to create order without ingredients")
    public void createNullIngredientsOrderUnsuccessfully() {
        order.setIngredients(null);
        ValidatableResponse response = orderClient.createOrderWithAutorization(order, accessToken);
        orderCheck.createdOrderUnsuccessfullyWithBadRequest(response);
    }

    @Test
    @DisplayName("Create order with empty ingredients unsuccessfully")
    @Description("Check that it is impossible to create order with empty ingredients")
    public void createEmptyIngredientsOrderUnsuccessfully() {
        List<String> emptyIngredients = List.of();
        order.setIngredients(emptyIngredients);
        ValidatableResponse response = orderClient.createOrderWithAutorization(order, accessToken);
        orderCheck.createdOrderUnsuccessfullyWithBadRequest(response);
    }

    @Test
    @DisplayName("Create order with nonexistent ingredients unsuccessfully")
    @Description("Check that it is impossible to create order with nonexistent ingredients")
    public void createIncorrectIngredientsOrderUnsuccessfully() {
        List<String> nonexistentIngredients = Arrays.asList("11111", "22222");
        order.setIngredients(nonexistentIngredients);
        ValidatableResponse response = orderClient.createOrderWithAutorization(order, accessToken);
        orderCheck.createdOrderUnsuccessfullyWithInternalError(response);
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
