import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;


public class PostUserTests extends TestBase{

    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public void generateTestData() {
        validUser = new User("Tato1", "novo@email.com", "123a1bc", "true");
       // validUser.registerUserRequest(SPEC, validUser);
        invalidUser = new User("Teste", "beltrano@qa.com.br", "123def", "true");
    }

    @Test
    public void shouldReturnSuccessAndStatus201() {
        given().
                spec(SPEC).
                           header("Content-Type", "application/json").
                and().
                      body(validUser.getUserCredentials()).
                when().
                       post("usuarios").
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo("Cadastro realizado com sucesso"));

    }

    @Test
    public void shouldFailTheUpdateUserAndStatus400() {

        given().
                spec(SPEC).
                           header("Content-Type", "application/json").
                and().
                      body(invalidUser.getUserCredentials()).
                when().
                       post("usuarios").
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo("Este email já está sendo usado")).
                body("_id", nullValue());
    }


}
