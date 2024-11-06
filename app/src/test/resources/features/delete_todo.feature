Feature: Delete a todo item

  As a user, I want to delete a todo item so that I can remove completed tasks.

  Background:
    Given the API server is running
    And a todo item with id "2" and title "Walk the dog" exists

  Scenario: Normal flow - Delete an existing todo item
    When I send a DELETE request to "/todos/2"
    Then the response status code should be 200
    And the response body should confirm deletion
    And the todo item with id "2" should no longer exist in the system

  Scenario: Alternate flow - Delete an already deleted todo item
    Given the todo item with id "2" has been deleted
    When I send a DELETE request to "/todos/2"
    Then the response status code should be 404
    And the response body should contain an error message indicating the todo item was not found

  Scenario: Error flow - Delete a todo item with an invalid id
    When I send a DELETE request to "/todos/abc"
    Then the response status code should be 400
    And the response body should contain an error message indicating invalid id
