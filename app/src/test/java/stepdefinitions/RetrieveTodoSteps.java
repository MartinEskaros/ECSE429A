package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.cucumber.datatable.DataTable;
import org.junit.jupiter.api.Assertions;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class RetrieveTodoSteps extends BaseSteps {

    @Given("the following todo items exist:")
    public void createTodoItems(DataTable dataTable) {
        List<Map<String, String>> todos = dataTable.asMaps();

        for (Map<String, String> todo : todos) {
            Map<String, Object> todoBody = new HashMap<>();
            todoBody.put("title", todo.get("title"));
            todoBody.put("description", todo.get("description"));
            todoBody.put("doneStatus", Boolean.parseBoolean(todo.get("doneStatus")));

            response = RestAssured.given()
                    .contentType("application/json")
                    .body(todoBody)
                    .post("/todos");

            Assertions.assertEquals(201, response.getStatusCode(),
                    "Failed to create todo item: " + todo.get("title"));
        }
    }

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = RestAssured.get(endpoint);
    }

    @Then("the response body should contain all the todo items")
    public void verifyAllTodosInResponse() {
        List<Map<String, Object>> todos = response.jsonPath().getList("$");

        Assertions.assertEquals(3, todos.size(),
                "Expected 3 todo items but got " + todos.size());

        verifyTodoExists(todos, "Buy groceries", "Buy milk and eggs");
        verifyTodoExists(todos, "Walk the dog", "Evening walk");
        verifyTodoExists(todos, "Finish project", "Complete by Friday");
    }

    @Then("the response body should contain only the todo item with title {string}")
    public void verifySpecificTodoInResponse(String title) {
        List<Map<String, Object>> todos = response.jsonPath().getList("$");

        Assertions.assertEquals(1, todos.size(),
                "Expected exactly one todo item but got " + todos.size());

        Map<String, Object> todo = todos.get(0);
        Assertions.assertEquals(title, todo.get("title"),
                "Todo title does not match expected value");
    }

    private void verifyTodoExists(List<Map<String, Object>> todos, String title, String description) {
        boolean found = todos.stream()
                .anyMatch(todo ->
                        todo.get("title").equals(title) &&
                                todo.get("description").equals(description)
                );
        Assertions.assertTrue(found,
                "Could not find todo with title: " + title + " and description: " + description);
    }
}