@Parallel
Feature: Title of your feature
  I want to use this template for my feature file

  @Test
  Scenario Outline: Parallet test <TC>
    Given Step is executed in parallel

    Examples: 
      | TC   |
      | TC01 |
      | TC02 |
      | TC03 |
      | TC04 |
      | TC05 |
      | TC06 |
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
