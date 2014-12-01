Narrative:
In order to create new nutrient in system
As a administrator
I would like to use POST method to insert a nutrient object.

Scenario: Nutrient Object Creation
Given a nutrient object with code:'<code>', name:'<name>', description:'<description>', originFlag:'<originFlag>', unitType:'<unitType>', numDec:'<numDec>', refId:'<refId>', refDb:'<refDb>'
When I insert nutrient object to database
Then nutrient object should be insert successful
Examples:
|code       |name           |description|originFlag|unitType|numDec|refId|refDb|
|NUTRIENT_A |Nutrient A     |200        |1|g|2|345|USNNDB|
|NUTRIENT_B |Nutrient B     |303        |3|mg|3|346|USNNDB|

Scenario: Insert Nutrient object with invalid Code
Given a nutrient object with code:'<code>', name:'<name>', description:'<description>', originFlag:'<originFlag>', unitType:'<unitType>', numDec:'<numDec>', refId:'<refId>', refDb:'<refDb>'
When I insert nutrient object to database
Then nutrient object should not be inserted
Examples:
|code                              |name           |description |originFlag|unitType|numDec|refId|refDb|
|NUTRIENT                          |               |200         |1|g|2|345|USNNDB|
|ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567 |Nutrient Y     |303         |3|mg|3|346|USNNDB|
