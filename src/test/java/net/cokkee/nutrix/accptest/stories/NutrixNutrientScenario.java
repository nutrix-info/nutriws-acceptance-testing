package net.cokkee.nutrix.accptest.stories;

import java.util.List;
import net.cokkee.nutrix.accptest.steps.NutrixNutrientSteps;

import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

/**
 *
 * @author drupalex
 */
public class NutrixNutrientScenario extends NutrixAbstractScenario {
    
    @Override
	public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new NutrixNutrientSteps());
	}
}
