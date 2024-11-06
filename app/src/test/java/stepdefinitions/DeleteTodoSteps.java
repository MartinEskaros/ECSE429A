package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;

public class DeleteTodoSteps extends BaseSteps {

    @Given("the todo item with id {string} has been deleted")
    public void deleteTodoItem(String id) {
        response = RestAssured.delete("/todos/" + id);

        // Verify deletion
        response = RestAssured.get("/todos/" + id);
        Assertions.assertEquals(404, response.getStatusCode(),
                "Todo item with id " + id + " should not exist after deletion");
    }

    @When("I send a DELETE request to {string}")
    public void sendDeleteRequest(String endpoint) {
        String resolvedEndpoint = resolvePath(endpoint);
        response = RestAssured.delete(resolvedEndpoint);
    }

    @Then("the response body should confirm deletion")
    public void verifyDeletionConfirmation() {
        String message = response.jsonPath().getString("message");
        Assertions.assertNotNull(message, "Response message should not be null");
        Assertions.assertTrue(message.toLowerCase().contains("deleted") ||
                        message.toLowerCase().contains("removal"),
                "Response should confirm deletion: " + message);
    }

    @Then("the todo item with id {string} should no longer exist in the system")
    public void verifyTodoNoLongerExists(String id) {
        response = RestAssured.get("/todos/" + id);
        Assertions.assertEquals(404, response.getStatusCode(),
                "Todo item with id " + id + " should not exist");
    }

    @Then("the response body should contain an error message indicating invalid id")
    public void verifyInvalidIdError() {
        String errorMessage = response.jsonPath().getString("message");
        Assertions.assertNotNull(errorMessage, "Error message should not be null");
        Assertions.assertTrue(errorMessage.toLowerCase().contains("invalid") &&
                        errorMessage.toLowerCase().contains("id"),
                "Error message should indicate invalid id: " + errorMessage);
    }
}