Narrative:
In order to create new nutrient in system
As a administrator
I would like to use POST method to insert a nutrient object.

Scenario: Nutrient Object Creation
Given a nutrient object with code:'<code>', name:'<name>', description:'<description>'
When I insert nutrient object to database
Then nutrient object should be insert successful
Examples:
|code       |name           |description|
|NUTRIENT_A |Nutrient A     |200        |
|NUTRIENT_B |Nutrient B     |303        |

Scenario: Insert Nutrient object with invalid Code
Given a nutrient object with code:'<code>', name:'<name>', description:'<description>'
When I insert nutrient object to database
Then nutrient object should not be inserted
Examples:
|code                              |name           |description |
|NUTRIENT#X                        |               |200         |
|ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567 |Nutrient Y     |303         |
