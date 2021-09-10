import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoint.*;

public class PostUserTest extends TestBase{

    private User validUser;
    private User invalidUser;

    @BeforeClass
    public void generateTestData() {
        validUser = new User("PostUser", "PostU@email.com", "123abc", "true");
       // registerUserRequest(SPEC, validUser);
        invalidUser = new User("Teste", "beltrano@qa.com.br", "123def", "true");

    }

    @AfterClass
    public void removeTestData() {
        deleteUserRequest(SPEC, validUser);
    }

    @Test
    public void shouldReturnSuccessAndStatus201() {
        Response postUserResponse = postUserRequest(SPEC, validUser);
        postUserResponse.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_POST)).
                body("_id", notNullValue());
    }

    @Test
    public void shouldReturnFailAndStatus400() {
        Response postUserResponse = postUserRequest(SPEC, invalidUser);
        postUserResponse.
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo(Constants.MESSAGE_FAILED_POST)).
                body("_id", nullValue());
    }
}
