Narrative:
In order to create a new Ingredient in system
As an administrator, I want to create a new Ingredient
I would like to use POST to insert a nutrient object

Scenario: insert an ingredient into database.
Given an ingredient object with label:'<label>', description:'<description>'
When I insert ingredient object to database
Then ingredient object should be insert successful
Examples:

|label           |descriptions            |
|INGREDIENT_A    |protein                 |
|INGREDIENT_B    |unsaturated fats        |

Scenario: insert an ingredient with invalid label
Given an ingredient object with code: label:'<label>', description:'<description>'
When  insert ingredient object to database
Then ingredient object should not be inserted
Examples:

|label                                 |descriptions      |
|INGREDIENT#X                          |protein           |
|ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567     |unsaturated fats  |