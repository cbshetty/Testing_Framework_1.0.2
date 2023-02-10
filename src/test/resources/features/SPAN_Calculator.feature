@SPAN
Feature: Test the API Contracts for SPAN Calculator
  Test the API Contracts for SPAN Calculator

  @GetExchange
  Scenario Outline: <TC>  Span Calculator: Validate API response
    When Application calls the Span Calculator Get Exchange API
    Then API returns the expected response
    And API response status is 200
    
  Examples: 
      | TC  | 
      | GetExchange_TC01 |

