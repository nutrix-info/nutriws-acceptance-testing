/*
Class Steps này, như là một diễn viên, biết cách múa may, diễn tả hành động,
nhưng không biết lắp ghép lại, nên không thể thành vở kịch được.
*/
package net.cokkee.nutrix.accptest.steps;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import net.cokkee.nutrix.model.dto.NutrixNutrientDTO;
import net.cokkee.nutrix.util.NutrixConstants;
import net.cokkee.nutrix.util.NutrixDataUtil;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.springframework.stereotype.Component;

/**
 *
 * @author drupalex
 */
@Component("nutrientSteps")
public class NutrixNutrientSteps extends NutrixAbstractSteps {

    @BeforeStories
    public void init() {
    }

    @AfterStories
    public void shutdown() {
    }

    private NutrixNutrientDTO nutrientObject = new NutrixNutrientDTO();

    private Response response;

    @Given("a nutrient code:<code>")
    @Alias("a nutrient code:$code")
    public void given_a_nutrient_code(@Named("code") String code) {
        nutrientObject.setCode(code);
    }

    @Given("a nutrient object with code:'<code>', name:'<name>', description:'<description>'")
    @Alias("a nutrient object with code:'$code', name:'$name', description:'$description'")
    public void given_a_nutrient_object(@Named("code") String code,
            @Named("name") String name, @Named("description") String description) {
        nutrientObject.setCode(code);
        nutrientObject.setName(name);
        nutrientObject.setDescription(description);
    }

    @When("I list all of nutrient objects")
    public void when_i_list_all_of_nutrient_objects() {
        response = RestAssured.
                given().
                contentType("application/json").
                expect().
                when().
                get(serviceUrl("nutrient/find")).
                then().
                extract().path("total");
    }

    @When("I insert nutrient object to database")
    public void when_i_insert_nutrient_object_to_database() {
        response = RestAssured.
                given().
                contentType("application/json").
                body(NutrixDataUtil.convertObjectToJson(nutrientObject)).
                when().
                post(serviceUrl("nutrient/crud"));
    }

    @When("I update nutrient object to database")
    public void when_i_update_nutrient_object_to_database() {
        response = RestAssured.
                given().
                contentType("application/json").
                body(NutrixDataUtil.convertObjectToJson(nutrientObject)).
                when().
                put(serviceUrl("nutrient/crud/" + nutrientObject.getId()));
    }

    @When("I delete nutrient object from database")
    public void when_i_delete_nutrient_object_from_database() {
        response = RestAssured.
                given().
                contentType("application/json").
                when().
                delete(serviceUrl("nutrient/crud/" + nutrientObject.getId()));
    }

    @Then("nutrient object should be insert successful")
    public void thenNutrientObjectShouldBeInsertSuccessful() {
        Assert.assertTrue(response.getStatusCode() == 200);

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        //Assert.assertTrue(NutrixDataUtil.verifyUUID(jsonPath.getString("id")));
        Assert.assertEquals(nutrientObject.getName(), jsonPath.getString("name"));
        Assert.assertEquals(nutrientObject.getCode(), jsonPath.getString("code"));
        //Assert.assertEquals(nutrientObject.getDescription(), jsonPath.getString("description"));
    }

    @Then("nutrient object should not be inserted")
    public void thenNutrientObjectShouldNotBeInserted() {
        Assert.assertTrue(response.getStatusCode() == NutrixConstants.ValidationFailedException_Code);

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Assert.assertTrue(NutrixConstants.ValidationFailedException_Code == jsonPath.getInt("code"));
        //Assert.assertEquals(nutrientObject.getDescription(), jsonPath.getString("description"));
    }
}
