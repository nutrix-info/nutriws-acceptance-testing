package net.cokkee.nutrix.accptest.stories;

import net.cokkee.nutrix.accptest.steps.NutrixNutrientSteps;

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
