Feature: WfisTests
#This is how background can be used to eliminate duplicate steps

  Background:
  User navigates to WfiIS page
    Given I am on WFiIS web page

#Scenario Plans to download
  Scenario:
    When I click on DlaStudentow
    And I choose Plany Zajec
    And I select Informatyka stacjonarne
    Then I can see list of files to download
    And I can download a plan file

