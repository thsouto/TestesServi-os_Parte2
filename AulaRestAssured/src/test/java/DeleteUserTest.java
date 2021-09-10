import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static models.User.deleteUserRequest;
import static org.hamcrest.Matchers.equalTo;


public class DeleteUserTest extends TestBase {

    private User validUser;

    @BeforeClass
    public void generateTestData() {
        validUser = new User("Tatu", "tatu@email.com", "123abc", "true");
        validUser.registerUserRequest(SPEC, validUser);
    }

    @Test
    public void shouldRemoveUserAndReturnSuccessMessageAndStatus200(){
        Response deleteUserResponse = deleteUserRequest(SPEC, validUser);
        deleteUserResponse.
        then().
                    assertThat().
                    statusCode(200).
                    body("message", equalTo("Nenhum registro excluído"));
        }
    }