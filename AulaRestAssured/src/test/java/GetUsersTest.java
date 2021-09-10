import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import static models.User.getUserRequest;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class GetUsersTest extends TestBase{

    private static User validUser1;
    private static User validUser2;
    private static User validUser3;
    private static User invalidUser1;

    @BeforeClass
    public void generateTestData(){
        validUser1 = new User("Ana Silva", "ana@email.com", "123abc", "true");
        validUser1.registerUserRequest(SPEC, validUser1);
        validUser2 = new User("Chico", "chico@email.com", "123abc", "true");
        validUser2.registerUserRequest(SPEC, validUser2);
        validUser3 = new User("Maria", "maria@email.com", "lalala123", "false");
        validUser3.registerUserRequest(SPEC, validUser3);
        invalidUser1 = new User("Carlos", "carlos@email.com", "minhasenha", "0");
    }

    @AfterClass
    public void removeTestData(){
        validUser1.deleteUserRequest(SPEC, validUser1);
        validUser2.deleteUserRequest(SPEC, validUser2);
        validUser3.deleteUserRequest(SPEC, validUser3);

    }


    @DataProvider(name="usersData")
    public Object[][] createTestData() {
        return  new  Object[][]{
                {"?nome="+validUser1.name, 2},
                {"?password="+validUser2.password, 2},
                {"?email="+validUser3.email, 1},
                {"?nome="+invalidUser1.name+"&email="+invalidUser1.email, 0}

    };

}


    @Test(dataProvider ="usersData")
    public void shouldReturnAllUsersAndStatus200(String query, int totalUsers) {
        Response getUserResponse = getUserRequest(SPEC, query);
      //  given().
        //        spec(SPEC).
        //when().
          //      get("usuarios"+query).
            getUserResponse.
                then().
                        assertThat().
                        statusCode(200).
                        body("quantidade", equalTo(totalUsers));


    }

}
