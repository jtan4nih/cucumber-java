Feature: Belly

  Scenario Outline: a few cukes
    Given I have <cakecount> cukes in my belly
    When I wait 1 hour
    Then my belly <status>
	Examples:
		|cakecount	|status					|
		|1					|is fine				|
		|2					|is still ok		|
		|3					|is a bit full	|
		|55					|growl					|