Feature: Retrieve all todo items

  As a user, I want to retrieve all todo items so that I can see what tasks I have.

  Background:
    Given the API server is running
    And the following todo items exist:
      | id | title            | doneStatus | description         |
      | 1  | Buy groceries    | false      | Buy milk and eggs   |
      | 2  | Walk the dog     | false      | Evening walk        |
      | 3  | Finish project   | false      | Complete by Friday  |

  Scenario: Normal flow - Retrieve all todo items
    When I send a GET request to "/todos"
    Then the response status code should be 200
    And the response body should contain all the todo items

  Scenario: Alternate flow - Retrieve todo items with a filter
    When I send a GET request to "/todos?title=Walk the dog"
    Then the response status code should be 200
    And the response body should contain only the todo item with title "Walk the dog"

  Scenario: Error flow - Retrieve todo items with an invalid filter
    When I send a GET request to "/todos?doneStatus=maybe"
    Then the response status code should be 400
    And the response body should contain an error message indicating invalid filter value
