package stepdefinitions;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class BaseSteps {
    protected static Response response;
    protected static RequestSpecification request;
    protected static final String BASE_URL = "http://localhost:4567";
    protected static Map<String, String> testContext = new HashMap<>();

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
    }

    protected String resolvePath(String endpoint) {
        if (endpoint.contains(":id")) {
            String id = testContext.get("currentTodoId");
            endpoint = endpoint.replace(":id", id);
        }
        return endpoint;
    }
}