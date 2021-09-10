package models;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import io.restassured.specification.RequestSpecification;

public class User {

    public String name;
    public String email;
    public String password;
    public String isAdmin;
    public String authToken;
    public String userID;

    public User (String name, String email, String password, String isAdmin){
           this.name = name;
           this.email = email;
           this.password = password;
           this.isAdmin = isAdmin;

    }

    public void setUserAuthToken(String authToken){
        this.authToken = authToken;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }


    public String getUserCredentials(){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("email", this.email);
        userJsonRepresentation.put("password", this.password);
        return  userJsonRepresentation.toJSONString();
    }

    public static Response authenticateUser(RequestSpecification spec, User user){

        String credentials = user.getUserCredentials();
        Response loginResponse =
                given().
                        header("Content-Type", "application/json").
                and().
                        body(credentials).
                when().post("login");

        JsonPath jsonPathEvaluator = loginResponse.jsonPath();
       // setUserAuthToken(jsonPathEvaluator.get("authorization"));
        String authToken = jsonPathEvaluator.get("authorization");
        user.setUserAuthToken(authToken);
        return loginResponse;
    }




    public Response registerUserRequest(RequestSpecification spec, User user) {

        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome",user.name);
        userJsonRepresentation.put("email",user.email);
        userJsonRepresentation.put("password",user.password);
        userJsonRepresentation.put("administrador",user.isAdmin);


        Response registerUserResponse =
                given().
                        header("accept", "application/json").
                        header("Content-Type", "application/json").
                and().
                        body(userJsonRepresentation.toJSONString()).
                when().
                        post("http://localhost:3000/usuarios");

        JsonPath jsonPathEvaluator = registerUserResponse.jsonPath();
        setUserAuthToken(jsonPathEvaluator.<String>get("_id"));

        return registerUserResponse;
    }

    public static Response deleteUserRequest(RequestSpecification spec, User user) {
        Response deleteUserResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        when().
                        delete("usuarios/"+user.userID);
        return deleteUserResponse;
    }

    public static Response getUserRequest(RequestSpecification spec, String query){
        Response getUserResponse =
                given().
                        spec(spec).
                        when().
                        get("usuarios"+query);
        return getUserResponse;
    }

}

