package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;

public class AssignCategorySteps extends BaseSteps {

    @Given("a category with id {string} and title {string} exists")
    public void createCategory(String id, String title) {
        // First, try to delete if it exists
        RestAssured.delete("/categories/" + id);

        Map<String, Object> categoryBody = new HashMap<>();
        categoryBody.put("title", title);

        response = RestAssured.given()
                .contentType("application/json")
                .body(categoryBody)
                .post("/categories");

        testContext.put("currentCategoryId", id);
    }

    @When("I create a new category with id {string} and title {string}")
    public void createNewCategory(String id, String title) {
        Map<String, Object> categoryBody = new HashMap<>();
        categoryBody.put("title", title);

        response = RestAssured.given()
                .contentType("application/json")
                .body(categoryBody)
                .post("/categories");

        testContext.put("currentCategoryId", id);
    }

    @Then("the response body should confirm the relationship was created")
    public void verifyRelationshipCreated() {
        String todoId = testContext.get("currentTodoId");
        String categoryId = testContext.get("currentCategoryId");

        response = RestAssured.get("/todos/" + todoId + "/categories");
        org.junit.jupiter.api.Assertions.assertEquals(200, response.getStatusCode(),
                "Failed to verify category assignment");
    }
}