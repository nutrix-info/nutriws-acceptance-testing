package net.cokkee.nutrix.accptest.steps;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import net.cokkee.nutrix.model.dto.NutrixIngredientDTO;
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
@Component("ingredientSteps")
public class NutrixIngredientSteps extends NutrixAbstractSteps {

    @BeforeStories
    public void init() {
    }

    @AfterStories
    public void shutdown() {
    }

    private NutrixIngredientDTO ingredientObject = new NutrixIngredientDTO();

    private Response response;

    @Given("a ingredient label:<label>")
    @Alias("a ingredient label: $label")
    public void given_a_ingredient_code(@Named("label") String label) {
        ingredientObject.setLabel(label);
    }

    @Given("a ingredient object with label:'<label>', description:'<description>'")
    @Alias("a ingredient object with label: '$label', description:'$description'")
    public void given_a_ingredient_object(@Named("label") String label, @Named("description") String description) {
        ingredientObject.setLabel(label);
        ingredientObject.setDescription(description);
    }

    @When("I list all ingredient objects")
    public void when_i_list_all_of_ingredient_objects() {
        response = RestAssured.given().
                contentType("application/json").
                expect().
                when().
                get(serviceUrl("ingredient/find")).
                then().
                extract().path("total"); //extract de lam gi vay a
    }

    @When("I insert ingredient object to database")
    public void when_i_insert_ingredient_object_to_database() {
        response = RestAssured.
                given().
                contentType("application/json").
                body(NutrixDataUtil.convertObjectToJson(ingredientObject)).
                when().
                put(serviceUrl("ingredient/crud/" + ingredientObject.getId()));
    }

    @When("I update ingredient object to database")
    public void when_i_update_ingredient_object_to_database() {
        response = RestAssured.
                given().
                contentType("application/json").
                body(NutrixDataUtil.convertObjectToJson(ingredientObject)).
                when().
                put(serviceUrl("ingredient/crud/" + ingredientObject.getId()));
    }

    @When("I delete ingredient object to database")
    public void when_i_delete_ingredient_object_to_database() {
        response = RestAssured.
                given().
                contentType("application/json").
                when().
                delete(serviceUrl("ingredient/crud/" + ingredientObject.getId()));
    }

    @Then("ingredient object should be insert succesful")
    public void thenIngredientObjectShouldBeInsertSuccessful() {
        Assert.assertTrue(response.getStatusCode() == 200);

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Assert.assertTrue(NutrixDataUtil.verifyUUID(jsonPath.getString("id")));
        Assert.assertEquals(ingredientObject.getLabel(), jsonPath.getString("label"));
        Assert.assertEquals(ingredientObject.getDescription(), jsonPath.getString("description"));
    }

    @Then("ingredient object should not be inserted")
    public void thenIngredientObjectShouldNotBeInserted() {
        Assert.assertTrue(response.getStatusCode() == NutrixConstants.ValidationFailedException_Code);

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        JsonPath jsonPath = new JsonPath(responseBody);

        Assert.assertTrue(NutrixConstants.ValidationFailedException_Code == jsonPath.getInt("label"));
    }
}
