Feature: Test Allo.ua search

  Scenario: Load page and accept cookies
    Given I load allo.ua page
    Given I accept Allo cookies if present
    When I search for samsung
    Then I see 3 first phones
    Then I add these 3 first phones to the database


