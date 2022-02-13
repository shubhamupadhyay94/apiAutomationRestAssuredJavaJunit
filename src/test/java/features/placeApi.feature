Feature: validate add place api

  #E2E testing of api , add place, get place and delete place

  @addPlace
  Scenario: Verify if a place is being successfully added using AddPlaceAPI
    Given I create a add place payload
    When user call "addPlaceApi" with "post" http request
    Then the addPlaceApi call got success with status code 200
    And "status" in response body is "ok"
    And "scope" in response body is "App"

  @addMulitplePlace
  Scenario: Verify if multiple place is being successfully added using AddPlaceAPI and validate placeName using getPlaceAPI
    Given I create a add place payload with <name> and <language> and <address>
    When user call "addPlaceApi" with "post" http request
    Then the addPlaceApi call got success with status code 200
    And "status" in response body is "ok"
    And "scope" in response body is "App"
    And verify "getPlaceAPI" is created successfully with <name>
    Examples:
    |name|language|address|
    |"DMart"|"English-IN"|"Satya Building , Bhayandar east, 4098767"|
    |"Reliance Mart"|"English-IN"|"Anupam Building , Bhayandar east, 4098767"|

  @deletePlace
  Scenario: verify delete place is working successfully
    Given create delete payload
    When user call "deletePlaceAPI" with "post" http request
    Then the addPlaceApi call got success with status code 200
    And "status" in response body is "ok"
