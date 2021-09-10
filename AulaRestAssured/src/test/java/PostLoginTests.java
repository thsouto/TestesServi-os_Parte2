import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostLoginTests extends TestBase{

    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Mari", "mari@email.com", "123abc", "true");
        validUser.registerUserRequest(SPEC, validUser);
        invalidUser = new User("Mari", "mari@email.com", "asdas", "true");
    }

    @Test
    public void shouldReturnSuccessMessageAuthTokenAndStatus200(){
          given().
                spec(SPEC).
                            header("Content-Type", "application/json").
                and().
                      body(validUser.getUserCredentials()).
                when().
                       post("login").
                then().
                assertThat().
                statusCode(200).
                body("message", equalTo("Login realizado com sucesso")).
                body("authorization", notNullValue());
    }

    @Test
    public void shouldReturnFailureMessageAndStatus401(){

    given().
            spec(SPEC).
            header("Content-Type", "application/json").
    and().
            body(invalidUser.getUserCredentials()).
    when().
            post("login").
    then().
            assertThat().
            statusCode(401).
            body("message", equalTo("Email e/ou senha inválidos")).
            body("authorization", nullValue());
        }
}
