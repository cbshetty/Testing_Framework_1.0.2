@SPAN
Feature: Test Data Setup for SPAN Calculator

  @TestDataSetup
  Scenario: Test Data Setup for Span Calculator tests
    Given Valid symbol "MCX_Option_Symbol" is set as property for exchange "MCX", product "OPTION"
    And Valid symbol "MCX_Future_Symbol" is set as property for exchange "MCX", product "FUTURE"
    And Valid symbol "CDS_Option_Symbol" is set as property for exchange "CDS", product "OPTION"
    And Valid symbol "CDS_Future_Symbol" is set as property for exchange "CDS", product "FUTURE"
    And Valid symbol "NFO_Option_Symbol" is set as property for exchange "NFO", product "OPTION"
    And Valid symbol "NFO_Future_Symbol" is set as property for exchange "NFO", product "FUTURE"
    And Status of Scenario "TestDataSetup" is set to "COMPLETED"