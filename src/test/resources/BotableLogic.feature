@Botablelogic
Feature: Send one email to the provider about the record types information in trackit

  @PositiveScenario
  Scenario: Send Email for all type of records with consideration of Botable
    When client calls /retrieveDailyEmail to request the getEmailDetails
    And BoTable Toggle functionalities should be turned on
    Then BaseResponse should be success

  @NegativeScenario
  Scenario: Do not Send Email to the provider
    When client calls /retrieveDailyEmail to request the getEmailDetails
    And BoTable Toggle functionalities turned off
    Then Don't need to send the emails
