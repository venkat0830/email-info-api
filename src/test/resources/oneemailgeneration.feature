@GenerateOneEmail
Feature: Send one email to the provider about the record types information in trackit

  Scenario: Send Email for all type of records
    When client calls /retrieveDailyEmail to request the getEmailDetails
    Then BaseResponse should be Success

  #Scenario: Send Email for recon and pend with consideration of operator email address
    #When client calls /retrieveDailyEmail to request the getEmailDetails
    #And when Operator email address is same as priamry emaill address or preferred email address
    #Then Send Email to the given email address
