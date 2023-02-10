@SPAN
Feature: Test the API Contracts for SPAN Calculator
  Test the API Contracts for SPAN Calculator

  #@TestDataSetup
  #Scenario: Test Data Setup for Span Calculator tests
  #	Given Start Scenario "TestDataSetup"
    #Given Valid symbol "MCX_Option_Symbol" is set as property for exchange "MCX", product "OPTION"
    #And Valid symbol "MCX_Future_Symbol" is set as property for exchange "MCX", product "FUTURE"
    #And Valid symbol "CDS_Option_Symbol" is set as property for exchange "CDS", product "OPTION"
    #And Valid symbol "CDS_Future_Symbol" is set as property for exchange "CDS", product "FUTURE"
    #And Valid symbol "NFO_Option_Symbol" is set as property for exchange "NFO", product "OPTION"
    #And Valid symbol "NFO_Future_Symbol" is set as property for exchange "NFO", product "FUTURE"
    #And End Scenario "TestDataSetup"
    
  Background:
  Given Wait until status of scenario "TestDataSetup" is "COMPLETED"

  @GetExchange
  Scenario Outline: <TC> : Span Calculator Get Exchange API - Validate API response
    When Application calls the Span Calculator Get Exchange API
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "$.exchange" has 3 item(s)
    And Array parameter "$.exchange" has items with paramter "exchangeName" equal to "MCX,NFO,CDS"

    Examples: 
      | TC               |
      | GetExchange_TC01 |

  @GetExchangeProduct
  Scenario Outline: <TC> : Span Calculator Get Exchange Product API - Validate API response with Valid Exchange
    When Application calls the Span Calculator Get Exchange Product API for exchange "<exchange>"
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Array parameter "$.product[?(@.productName=='FUTURE')]" does not have parameter(s) "productType"
    And Array parameter "$.product[?(@.productName=='OPTION')]" has parameter(s) "productType"
    And Array parameter "$.product[?(@.productName=='OPTION')].productType" returns 2 items
    And Array parameter "$.product[?(@.productName=='OPTION')].productType" has value(s) "CALL,PUT"

    #And Array parameter "$.product[?(@.productName=='OPTION')].productType" does not have value(s) "CALL,PUT"
    #And Parameter "$.product[1].productType" has 2 item(s)
    #And Parameter "$.product[1].productType" has value(s) "CALL,PUT"
    #And Parameter "$.product[1].productType" does not have value(s) "PUSH"
    Examples: 
      | TC                      | exchange |
      | GetExchangeProduct_TC01 | MCX      |
      | GetExchangeProduct_TC02 | CDS      |
      | GetExchangeProduct_TC03 | NFO      |

  @GetExchangeProduct
  Scenario Outline: <TC> : Span Calculator Get Exchange Product API - Validate API response with Invalid Exchange
    When Application calls the Span Calculator Get Exchange Product API for exchange "<exchange>"
    Then API returns an error in response
    And API response status is 400
    And Value of string parameter "code" is "404"
    And Value of string parameter "description" is "exchangeName not valid"

    Examples: 
      | TC                      | exchange |
      | GetExchangeProduct_TC04 | ABC      |

  @GetProductContract
  Scenario Outline: <TC> : Span Calculator Get Product Contract API - Validate API response with Valid Exchange and Product
    When Application calls the Span Calculator Get Product Contrat API for exchange "<exchange>" and product "<product>"
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Contract symbol contains symbol and expiry date seperated by -

    Examples: 
      | TC                      | exchange | product |
      | GetProductContract_TC01 | MCX      | FUTURE  |
      | GetProductContract_TC02 | MCX      | OPTION  |

  @GetProductContract
  Scenario Outline: <TC> : Span Calculator Get Product Contract API - Validate API response with Valid Exchange and Product
    When Application calls the Span Calculator Get Product Contrat API for exchange "<exchange>" and product "<product>"
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Contract symbol contains symbol and expiry date seperated by -
    And Array parameter "$.contract" has items with paramter "lotSize" equal to "1"

    Examples: 
      | TC                      | exchange | product |
      | GetProductContract_TC03 | CDS      | FUTURE  |
      | GetProductContract_TC04 | CDS      | OPTION  |

  @GetProductContract
  Scenario Outline: <TC> : Span Calculator Get Product Contract API - Validate API response with Valid Exchange and Product
    When Application calls the Span Calculator Get Product Contrat API for exchange "<exchange>" and product "<product>"
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Contract symbol contains symbol and expiry date seperated by -

    Examples: 
      | TC                      | exchange | product |
      | GetProductContract_TC05 | NFO      | FUTURE  |
      | GetProductContract_TC06 | NFO      | OPTION  |

  @GetProductContract
  Scenario Outline: <TC> : Span Calculator Get Product Contract API - Validate API response with <test>
    When Application calls the Span Calculator Get Product Contrat API for exchange "<exchange>" and product "<product>"
    Then API returns an error in response
    And API response status is 400
    And Value of string parameter "code" is "404"
    And Value of string parameter "description" is "<description>"

    Examples: 
      | TC                      | test                  | exchange | product | description            |
      | GetProductContract_TC07 | invalid exchange name | ABC      | FUTURE  | exchangeName not valid |
      | GetProductContract_TC08 | invalid product name  | CDS      | ABC     | productName not valid  |

  @GetStrikePrice
  Scenario Outline: <TC> : Span Calculator Get Strike Price API - Validate API response with Valid Exchange and Product
    When Application calls the Span Calculator Get Strike Price API for exchange "<exchange>" and product "<product>" and symbol "<symbol>" and option type "<optionType>"
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms

    Examples: 
      | TC                  | exchange | product | symbol                | optionType |
      | GetStrikePrice_TC01 | MCX      | OPTION  | {{MCX_Option_Symbol}} | CALL       |
      | GetStrikePrice_TC02 | MCX      | OPTION  | {{MCX_Option_Symbol}} | PUT        |
      | GetStrikePrice_TC03 | NFO      | OPTION  | {{NFO_Option_Symbol}} | CALL       |
      | GetStrikePrice_TC04 | NFO      | OPTION  | {{NFO_Option_Symbol}} | PUT        |
      | GetStrikePrice_TC05 | CDS      | OPTION  | {{CDS_Option_Symbol}} | CALL       |
      | GetStrikePrice_TC06 | CDS      | OPTION  | {{CDS_Option_Symbol}} | PUT        |

  @GetStrikePrice
  Scenario Outline: <TC> : Span Calculator Get Strike Price API - Validate API response with <test>
    When Application calls the Span Calculator Get Strike Price API for exchange "<exchange>" and product "<product>" and symbol "<symbol>" and option type "<optionType>"
    Then API returns an error in response
    And API response status is 400
    And Value of string parameter "code" is "404"
    And Value of string parameter "description" is "<description>"

    Examples: 
      | TC                  | test                  | exchange | product | symbol                | optionType | description            |
      | GetStrikePrice_TC07 | invalid exchange name | ABC      | OPTION  | {{MCX_Option_Symbol}} | CALL       | exchangeName not valid |
      | GetStrikePrice_TC08 | invalid product name  | MCX      | ABC     | {{MCX_Option_Symbol}} | CALL       | productName not valid  |
      | GetStrikePrice_TC09 | invalid option type   | MCX      | OPTION  | {{MCX_Option_Symbol}} | CALL1      | option type not valid  |

  @GetStrikePrice
  Scenario Outline: <TC> : Span Calculator Get Strike Price API - Validate API response with <test>
    When Application calls the Span Calculator Get Strike Price API for exchange "<exchange>" and product "<product>" and symbol "<symbol>" and option type "<optionType>"
    And API response status is 200
    And API repsonse time is below 5000 ms
    And API returns an empty response

    Examples: 
      | TC                  | test                                | exchange | product | symbol         | optionType |
      | GetStrikePrice_TC10 | invalid symbol name(correct format) | MCX      | OPTION  | METAL1-17FEB23 | CALL       |

  @GetStrikePrice
  Scenario Outline: <TC> : Span Calculator Get Strike Price API - Validate API response with <test>
    When Application calls the Span Calculator Get Strike Price API for exchange "<exchange>" and product "<product>" and symbol "<symbol>" and option type "<optionType>"
    And API response status is 400
    And API repsonse time is below 5000 ms
    And Value of string parameter "code" is "400"
    And Value of string parameter "description" is "Invalid Contract"

    Examples: 
      | TC                  | test                                  | exchange | product | symbol | optionType |
      | GetStrikePrice_TC11 | invalid symbol name(incorrect format) | MCX      | OPTION  | ABC    | C          |

  @GetStrikePrice
  Scenario Outline: <TC> : Span Calculator Get Strike Price API - Validate API response for <test>
    When Application calls the Span Calculator Get Strike Price API for exchange "<exchange>" and product "<product>" and symbol "<symbol>" and option type "<optionType>"
    Then API response status is 200
    And API returns an empty response

    Examples: 
      | TC                  | test   | exchange | product | symbol                | optionType |
      | GetStrikePrice_TC12 | FUTURE | MCX      | FUTURE  | {{MCX_Future_Symbol}} | CALL       |

  @MarginCalculator
  Scenario Outline: <TC> : Span Calculator Margin Calculator API - Validate API response for <test>
    Given Positions "<postion>" are fetched from row <row> in file "<file>"
    When Application calls the Margin Calculator API
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "positionMargin" has <NoOfPositions> item(s)
    And Array parameter "positionMargin.*" does not have parameter(s) "optionType"
    And Array parameter "positionMargin.*" has items with paramter "strikePrice" equal to "0.00"
    And totalPositionMargin is the sum of all totalMargin in postionMargin

    #And For items in array parameter "positionMargin" if "product" is "FUTCOM" then "strikePrice" is "0.0"
    Examples: 
      | TC                    | test                   | postion   | NoOfPositions | row | file                                           |
      | MarginCalculator_TC01 | Single Future Position | position1 |             1 |   2 | src/test/resources/Margin_Calculator_Data.xlsx |

  @MarginCalculator
  Scenario Outline: <TC> : Span Calculator Margin Calculator API - Validate API response for <test>
    Given Positions "<postion>" are fetched from row <row> in file "<file>"
    When Application calls the Margin Calculator API
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "positionMargin" has <NoOfPositions> item(s)
    And Array parameter "positionMargin.*" has parameter(s) "optionType"
    And totalPositionMargin is the sum of all totalMargin in postionMargin

    Examples: 
      | TC                    | test                   | postion   | NoOfPositions | row | file                                           |
      | MarginCalculator_TC02 | Single Option Position | position1 |             1 |   1 | src/test/resources/Margin_Calculator_Data.xlsx |

  @MarginCalculator
  Scenario Outline: <TC> : Span Calculator Margin Calculator API - Validate API response for <test>
    Given Positions "<postion>" are fetched from row <row> in file "<file>"
    When Application calls the Margin Calculator API
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "positionMargin" has <NoOfPositions> item(s)
    And Array parameter "positionMargin.*" does not have parameter(s) "optionType"
    And totalPositionMargin is the sum of all totalMargin in postionMargin

    #And For items in array parameter "positionMargin" if "product" is "FUTCOM" then "strikePrice" is "0.0"
    Examples: 
      | TC                    | test                      | postion             | NoOfPositions | row | file                                           |
      | MarginCalculator_TC03 | Multiple Future Positions | position1,position2 |             2 |   2 | src/test/resources/Margin_Calculator_Data.xlsx |

  @MarginCalculator
  Scenario Outline: <TC> : Span Calculator Margin Calculator API - Validate API response for <test>
    Given Positions "<postion>" are fetched from row <row> in file "<file>"
    When Application calls the Margin Calculator API
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "positionMargin" has <NoOfPositions> item(s)
    And Array parameter "positionMargin.*" has parameter(s) "optionType"
    And totalPositionMargin is the sum of all totalMargin in postionMargin

    Examples: 
      | TC                    | test                     | postion             | NoOfPositions | row | file                                           |
      | MarginCalculator_TC04 | Multiple Option Position | position1,position2 |             2 |   3 | src/test/resources/Margin_Calculator_Data.xlsx |

  @MarginCalculator
  Scenario Outline: <TC> : Span Calculator Margin Calculator API - Validate API response for <test>
    Given Positions "<postion>" are fetched from row <row> in file "<file>"
    When Application calls the Margin Calculator API
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "positionMargin" has <NoOfPositions> item(s)
    And totalPositionMargin is the sum of all totalMargin in postionMargin
    And Array parameter "positionMargin[?(@.product contains 'FUT')]" does not have parameter(s) "optionType"
    And Array parameter "positionMargin[?(@.product contains 'FUT')]" has items with paramter "strikePrice" equal to "0.00"
    And Array parameter "positionMargin[?(@.product contains 'OPT')]" has parameter(s) "optionType"

    #And For items in array parameter "positionMargin" if "product" is "FUTCOM" then "strikePrice" is "0.0"
    Examples: 
      | TC                    | test                             | postion             | NoOfPositions | row | file                                           |
      | MarginCalculator_TC05 | Both Future and Option positions | position1,position2 |             2 |   1 | src/test/resources/Margin_Calculator_Data.xlsx |

  @MarginCalculator
  Scenario Outline: <TC> : Span Calculator Margin Calculator API - Validate API response for <test>
    Given Positions "<postion>" are fetched from row <row> in file "<file>"
    When Application calls the Margin Calculator API
    Then API returns an error in response
    And API response status is 400
    And Value of string parameter "code" is "400"
    And Value of string parameter "description" is "<description>"

    Examples: 
      | TC                    | test                    | postion   | row | description                                                              | file                                           |
      | MarginCalculator_TC06 | Invalid Exchange        | position1 |   4 | invalid exchange                                                         | src/test/resources/Margin_Calculator_Data.xlsx |
      | MarginCalculator_TC07 | Invalid Product         | position1 |   5 | invalid product name                                                     | src/test/resources/Margin_Calculator_Data.xlsx |
      | MarginCalculator_TC08 | Invalid Option Type     | position1 |   7 | invalid option type                                                      | src/test/resources/Margin_Calculator_Data.xlsx |
      | MarginCalculator_TC09 | Invalid Symbol          | position1 |   6 | Position Params Incorrect. check strike, symbol, expiry and option type. | src/test/resources/Margin_Calculator_Data.xlsx |
      | MarginCalculator_TC10 | Invalid Trade Type      | position1 |   8 | invalid trade type                                                       | src/test/resources/Margin_Calculator_Data.xlsx |
      | MarginCalculator_TC11 | Invalid Strike Price(0) | position1 |   9 | Position Params Incorrect. check strike, symbol, expiry and option type. | src/test/resources/Margin_Calculator_Data.xlsx |
      | MarginCalculator_TC12 | Invalid Strike Price    | position1 |  10 | Position Params Incorrect. check strike, symbol, expiry and option type. | src/test/resources/Margin_Calculator_Data.xlsx |

  @MarginCalculator
  Scenario Outline: <TC> : Span Calculator Margin Calculator API - Validate API response for <test>
    Given Positions "<postion>" are fetched from row <row> in file "<file>"
    When Application calls the Margin Calculator API
    Then API response matches schema "<ScehmaFileName>"
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "positionMargin" has <NoOfPositions> item(s)
    And Array parameter "positionMargin" has items with value of Double paramter "SPANMargin" equal to 0.0
    And Array parameter "positionMargin" has items with value of Double paramter "exposureMargin" equal to 0.0
    And Array parameter "positionMargin" has items with value of Double paramter "totalMargin" equal to 0.0
    And Value of double json parameter "margin.SPANMargin" is 0.0
    And Value of double json parameter "margin.exposureMargin" is 0.0
    And Value of double json parameter "margin.totalMargin" is 0.0
    And Value of double json parameter "margin.netPremium" is 0.0

    Examples: 
      | TC                    | test                | postion   | NoOfPositions | row | description            | file                                           | ScehmaFileName                     |
      | MarginCalculator_TC13 | Quantity equal to 0 | position1 |             1 |  11 | invalid quantity price | src/test/resources/Margin_Calculator_Data.xlsx | SPAN_MarginCalculator_Qty_0_Schema |

  @MarginCalculator
  Scenario Outline: <TC> : Span Calculator Margin Calculator API - Validate Margin for <test>
    Given Positions "<postion>" are fetched from row <row> in file "<file>"
    When Application calls the Margin Calculator API
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "positionMargin" has <NoOfPositions> item(s)
    And totalPositionMargin is the sum of all totalMargin in postionMargin
    And Array parameter "positionMargin[?(@.product contains 'OPT')]" has parameter(s) "optionType"
    And Array parameter "positionMargin" has items with value of Double paramter "SPANMargin" equal to 0.0
    And Array parameter "positionMargin" has items with value of Double paramter "exposureMargin" equal to 0.0
    And Array parameter "positionMargin" has items with value of Double paramter "totalMargin" equal to 0.0
    And Value of double json parameter "margin.SPANMargin" is 0.0
    And Value of double json parameter "margin.exposureMargin" is 0.0
    And Value of double json parameter "margin.totalMargin" is 0.0
    And Value of double json parameter "margin.netPremium" is not 0.0

    Examples: 
      | TC                    | test                                    | postion   | NoOfPositions | row | file                                           |
      | MarginCalculator_TC14 | Option Buy Call Position(All Exchanges) | position1 |             1 |  12 | src/test/resources/Margin_Calculator_Data.xlsx |
      | MarginCalculator_TC15 | Option Buy Put Position(All Exchanges)  | position1 |             1 |  13 | src/test/resources/Margin_Calculator_Data.xlsx |

  @AppState
  Scenario Outline: <TC> : Span Calculator Get App State Calculator API - Validate API response
    When Application calls the Span Calculator Get App State API
    Then API returns the expected reponse
    And API response status is 200
    And API repsonse time is below 5000 ms
    And Parameter "$.*" has 3 item(s)
    And Value of string json parameter "$.*.Files.*.FileName" contains current date in one of the formats "ddMMyyyy,yyyyMMdd"

    Examples: 
      | TC               |
      | GetAppState_TC01 |
