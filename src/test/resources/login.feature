Feature: Tienda Login
  Scenario:
    Given I go to "http://172.16.7.129:8081"
    And I set user name with "admin"
    And I set password with "admin"
    And I click on login button
    Then I should see "SIS VENTAS" message

  Scenario:
    Given I go to "http://172.16.7.129:8081"
    And I set user name with "admin"
    And I set password with "incorrect"
    And I click on login button
    Then I should see "SIS" header
