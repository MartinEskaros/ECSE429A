Feature: Create a new todo item

  As a user, I want to create a new todo item so that I can keep track of my tasks.

  Background:
    Given the API server is running
    And there are no todo items in the system

  Scenario: Normal flow - Create a new todo item with valid data
    When I send a POST request to "/todos" with a valid todo item
    Then the response status code should be 201
    And the response body should contain the new todo item with the given data

  Scenario: Alternate flow - Create a new todo item without optional fields
    When I send a POST request to "/todos" with only the mandatory field "title"
    Then the response status code should be 201
    And the response body should contain the new todo item with default values for optional fields

  Scenario: Error flow - Create a new todo item with missing mandatory field
    When I send a POST request to "/todos" without the "title" field
    Then the response status code should be 400
    And the response body should contain an error message indicating that "title" is mandatory
