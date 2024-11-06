Feature: Update a todo item's title

  As a user, I want to update a todo item's title so that I can correct mistakes.

  Background:
    Given the API server is running
    And a todo item with id "1" and title "Buy grceries" exists

  Scenario: Normal flow - Update todo item's title
    When I send a PUT request to "/todos/1" with "title" set to "Buy groceries"
    Then the response status code should be 200
    And the response body should contain the todo item with the updated title

  Scenario: Alternate flow - Update todo item's description only
    When I send a PUT request to "/todos/1" with "description" set to "Buy milk, eggs, and bread"
    Then the response status code should be 200
    And the response body should contain the todo item with the updated description

  Scenario: Error flow - Update a non-existent todo item
    When I send a PUT request to "/todos/999" with "title" set to "Some title"
    Then the response status code should be 404
    And the response body should contain an error message indicating the todo item was not found
