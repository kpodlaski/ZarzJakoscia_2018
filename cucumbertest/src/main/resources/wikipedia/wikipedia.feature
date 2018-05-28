Feature: search Wikipedia

  Background:
    #Given Open http://en.wikipedia.org
    #And Do login

  Scenario: direct search article
    Given Enter search term "Cucumber"
    When Do search
    Then Single result is shown for "Cucumber"