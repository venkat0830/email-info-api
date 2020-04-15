@tag
Feature: Email Alert for Record information

  @tag1
  Scenario: Send email for MissedProviderEmailList
    When Client calls /api/provider/email API with MissedProviderEmailList
    Then BaseResponse should be Success

  @tag2
  Scenario: Validate for bad reqeuest without providing corporateTaxID
    When Client calls /api/provider/email API  without providing corporateTaxID in MissedProviderEmailList
    Then BaseResponse should be Bad Request

  @tag3
  Scenario: Validate for bad reqeuest without providing providerTin
    When Client calls /api/provider/email API  without providing providerTin in MissedProviderEmailList
    Then BaseResponse should be Bad Request

  @tag4
  Scenario: Validate for bad reqeuest without providing uuID
    When Client calls /api/provider/email API  without providing uuID in MissedProviderEmailList
    Then BaseResponse should be Bad Request
