Feature: Belly

  Scenario Outline: a few cukes
    Given I have <cakecount> cukes in my belly
    When I wait 1 hour
    Then my belly should growl
	Examples:
		|cakecount	|
		|1					|
		|2					|
		|3					|
		|55					|