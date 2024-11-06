package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import java.util.HashMap;
import java.util.Map;

public class CommonSteps {
    protected static Response response;  // Changed to protected static
    protected static RequestSpecification request;  // Changed to protected static
    protected static final String BASE_URL = "http://localhost:4567";
    protected static Map<String, String> testContext = new HashMap<>();

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Accept", "application/json");
    }

    @Given("the API server is running")
    public void verifyServerIsRunning() {
        try {
            Response healthCheck = RestAssured.get(BASE_URL + "/todos");
            Assertions.assertTrue(healthCheck.getStatusCode() == 200 || healthCheck.getStatusCode() == 204,
                    "API server is not running. Status code: " + healthCheck.getStatusCode());
        } catch (Exception e) {
            throw new RuntimeException("API server is not running: " + e.getMessage());
        }
    }

    @Given("a todo item with id {string} and title {string} exists")
    public void createTodoItem(String id, String title) {
        // First, try to delete if it exists
        RestAssured.delete("/todos/" + id);

        Map<String, Object> todoBody = new HashMap<>();
        todoBody.put("title", title);

        Response createResponse = RestAssured.given()
                .contentType("application/json")
                .body(todoBody)
                .post("/todos");

        Assertions.assertEquals(201, createResponse.getStatusCode(),
                "Failed to create todo item with title: " + title);

        testContext.put("currentTodoId", id);
    }

    @Then("the response status code should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode(),
                "Expected status code " + expectedStatusCode + " but got " + response.getStatusCode());
    }

    // Helper method for all children classes
    protected String resolvePath(String endpoint) {
        if (endpoint.contains(":id")) {
            String id = testContext.get("currentTodoId");
            endpoint = endpoint.replace(":id", id);
        }
        return endpoint;
    }
}