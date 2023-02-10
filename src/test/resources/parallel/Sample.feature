@Parallel
Feature: Title of your feature
  I want to use this template for my feature file

  @Test
  Scenario Outline: Parallet test <TC>
    Given Test Patch Request
    Then API returns an error in response
    And API response status is 500
    And API repsonse time is below 5000 ms
    And Value of string parameter "code" is "500"

    Examples: 
      | TC   |
      | TC01 |
      
  @Test
  Scenario Outline: Parallet test <TC>
    Given Test Delete Request
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Value of string parameter "cancellationType" is "USER_CANCELLED"

    Examples: 
      | TC   |
      | TC02 |
     
  #@tag1
  #Scenario: Title of your scenario 1
  #	Given sample step
    #When Application calls the "Sample" API
    #Then API response status is 200
    #
  #@tag2
  #Scenario: Title of your scenario 2
  #	Given sample step
    #When Application calls the "Sample" API
    #Then API response status is 200
    #
 #@tag3
  #Scenario: Title of your scenario 3
  #	Given sample step 2
    #When Application calls the "Sample" API
    #Then API response status is 200
