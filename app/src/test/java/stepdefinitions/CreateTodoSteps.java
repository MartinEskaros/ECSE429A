package stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import java.util.HashMap;
import java.util.Map;

public class CreateTodoSteps extends BaseSteps {

    @When("I send a POST request to {string} with a valid todo item")
    public void sendPostRequestWithValidTodo(String endpoint) {
        Map<String, Object> todoBody = new HashMap<>();
        todoBody.put("title", "Test Todo");
        todoBody.put("description", "Test Description");
        todoBody.put("doneStatus", false);

        response = RestAssured.given()
                .contentType("application/json")
                .body(todoBody)
                .post(endpoint);
    }

    @When("I send a POST request to {string} with only the mandatory field {string}")
    public void sendPostRequestWithMandatoryField(String endpoint, String field) {
        Map<String, Object> todoBody = new HashMap<>();
        todoBody.put(field, "Test Todo Title");

        response = RestAssured.given()
                .contentType("application/json")
                .body(todoBody)
                .post(endpoint);
    }

    @When("I send a POST request to {string} without the {string} field")
    public void sendPostRequestWithoutField(String endpoint, String field) {
        Map<String, Object> todoBody = new HashMap<>();
        if (!field.equals("title")) {
            todoBody.put("description", "Test Description");
            todoBody.put("doneStatus", false);
        }

        response = RestAssured.given()
                .contentType("application/json")
                .body(todoBody)
                .post(endpoint);
    }

    @Then("the response body should contain the new todo item with the given data")
    public void verifyResponseBodyContainsTodoData() {
        Assertions.assertNotNull(response.jsonPath().getString("id"));
        Assertions.assertEquals("Test Todo", response.jsonPath().getString("title"));
        Assertions.assertEquals("Test Description", response.jsonPath().getString("description"));
        Assertions.assertEquals(false, response.jsonPath().getBoolean("doneStatus"));
    }

    @Then("the response body should contain the new todo item with default values for optional fields")
    public void verifyResponseBodyContainsDefaultValues() {
        Assertions.assertNotNull(response.jsonPath().getString("id"));
        Assertions.assertEquals("Test Todo Title", response.jsonPath().getString("title"));

        String description = response.jsonPath().getString("description");
        Boolean doneStatus = response.jsonPath().getBoolean("doneStatus");

        Assertions.assertTrue(description == null || description.isEmpty(),
                "Description should be null or empty by default");
        Assertions.assertTrue(doneStatus == null || doneStatus == false,
                "DoneStatus should be null or false by default");
    }
}