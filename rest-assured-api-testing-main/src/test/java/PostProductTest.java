import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.*;

public class PostProductTest extends TestBase{


    private Product validProduct;
    private Product invalidProduct;

    @BeforeClass
    public void generateTestData() {
        validProduct = new Product("Agua", 1, "branca", 5);
        // registerUserRequest(SPEC, validUser);
        invalidProduct = new Product("Agua", 2, "123def", 3);

    }

    @AfterClass
    public void removeTestData() {
        deleteProductRequest(SPEC, validProduct);
    }

    @Test
    public void shouldReturnSuccessAndStatus201() {
        Response postProductResponse = postProductRequest(SPEC, validProduct);
        postProductResponse.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_POST)).
                body("_id", notNullValue());
    }

    @Test
    public void shouldReturnFailAndStatus400() {
        Response postProductResponse = postProductRequest(SPEC, invalidProduct);
        postProductResponse.
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo(Constants.MESSAGE_FAILED_PRODUCT)).
                body("_id", nullValue());
    }

    @Test
    public void shouldReturnFailAndStatus401() {
        Response postProductResponse = postProductRequest(SPEC, invalidProduct);
        postProductResponse.
                then().
                assertThat().
                statusCode(401).
                body("message", equalTo(Constants.MESSAGE_FAILED_TOKEN)).
                body("_id", nullValue());
    }
}
