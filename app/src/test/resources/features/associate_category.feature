Feature: Assign a category to a todo item

  As a user, I want to assign a category to a todo item so that I can organize my tasks.

  Background:
    Given the API server is running
    And a todo item with id "1" and title "Buy groceries" exists
    And a category with id "10" and title "Shopping" exists

  Scenario: Normal flow - Assign an existing category to a todo item
    When I send a POST request to "/todos/1/categories" with "id" set to "10"
    Then the response status code should be 201
    And the response body should confirm the relationship was created

  Scenario: Alternate flow - Assign a new category to a todo item
    When I create a new category with id "11" and title "Errands"
    And I send a POST request to "/todos/1/categories" with "id" set to "11"
    Then the response status code should be 201
    And the response body should confirm the relationship was created

  Scenario: Error flow - Assign a category to a non-existent todo item
    When I send a POST request to "/todos/999/categories" with "id" set to "10"
    Then the response status code should be 404
    And the response body should contain an error message indicating the todo item was not found
