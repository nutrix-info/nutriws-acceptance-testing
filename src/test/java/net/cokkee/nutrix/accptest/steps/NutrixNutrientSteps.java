package net.cokkee.nutrix.accptest.steps;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import net.cokkee.nutrix.model.dto.NutrixNutrientDTO;
import net.cokkee.nutrix.util.NutrixConstants;
import net.cokkee.nutrix.util.NutrixDataUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    private static Log log = LogFactory.getLog(NutrixNutrientSteps.class);
    
    @BeforeStories
    public void init() {
        Response response = RestAssured.
                given().
                contentType("application/json").
                expect().
                when().
                get(serviceUrl("nutrient/find"));

        Assert.assertTrue(response.getStatusCode() == 200);

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        int total = jsonPath.getInt("total");
        for (int i = 0; i < total; i++) {
            String id = (jsonPath.getString("collection[" + i + "].id"));
            if (log.isDebugEnabled()) {
                log.debug("Nutrient#" + id + " will be deleted");
            }

            RestAssured.
                    given().
                    contentType("application/json").
                    when().
                    delete(serviceUrl("nutrient/crud/" + id));
        }
    }

    @AfterStories
    public void shutdown() {
    }

    private NutrixNutrientDTO nutrientObject = new NutrixNutrientDTO();

    private Response response;

    @Given("a nutrient refId:<refId> at refDb:<refDb>")
    @Alias("a nutrient refId:$refId at refDb:$refDb")
    public void given_a_nutrient_refId_and_refDb(
            @Named("refId") String refId,
            @Named("refDb") String refDb) {
        nutrientObject.setRefId(refId);
        nutrientObject.setRefDb(refDb);
    }
    
    @Given("a nutrient code:<code>")
    @Alias("a nutrient code:$code")
    public void given_a_nutrient_code(@Named("code") String code) {
        nutrientObject.setCode(code);
    }

    @Given("a nutrient object with code:'<code>', name:'<name>', description:'<description>'" 
            + ", originFlag:'<originFlag>', unitType:'<unitType>', numDec:'<numDec>'"
            + ", refId:'<refId>', refDb:'<refDb>'")
    //@Alias("a nutrient object with code:'$code', name:'$name', description:'$description'")
    public void given_a_nutrient_object(@Named("code") String code,
            @Named("name") String name, @Named("description") String description,
            @Named("originFlag") Integer originFlag,
            @Named("unitType") String unitType,
            @Named("numDec") Byte numDec,
            @Named("refId") String refId,
            @Named("refDb") String refDb) {
        nutrientObject.setCode(code);
        nutrientObject.setName(name);
        nutrientObject.setDescription(description);
        nutrientObject.setOriginFlag(originFlag);
        nutrientObject.setUnitType(unitType);
        nutrientObject.setNumDec(numDec);
        nutrientObject.setRefId(refId);
        nutrientObject.setRefDb(refDb);
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
        String objectString = NutrixDataUtil.convertObjectToJson(nutrientObject);
        
        System.out.println("=====@ " + objectString);
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

    @Then("nutrient object should be retrieved successful")
    public void thenNutrientObjectShouldBeRetrievedSuccessful() {
        Assert.assertTrue(response.getStatusCode() == 200);

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Assert.assertEquals(nutrientObject.getCode(), jsonPath.getString("code"));
    }
    
    @Then("nutrient object should be insert successful")
    public void thenNutrientObjectShouldBeInsertSuccessful() {
        Assert.assertTrue(response.getStatusCode() == 200);

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        //Assert.assertTrue(NutrixDataUtil.verifyUUID(jsonPath.getString("id")));
        Assert.assertEquals(nutrientObject.getName(), jsonPath.getString("name"));
        Assert.assertEquals(nutrientObject.getCode(), jsonPath.getString("code"));
        Assert.assertEquals(nutrientObject.getDescription(), jsonPath.getString("description"));
        Assert.assertEquals(String.valueOf(nutrientObject.getOriginFlag()), jsonPath.getString("originFlag"));
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
