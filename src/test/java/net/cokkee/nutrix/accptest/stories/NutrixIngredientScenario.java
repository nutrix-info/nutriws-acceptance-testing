package net.cokkee.nutrix.accptest.stories;

import net.cokkee.nutrix.accptest.steps.NutrixIngredientSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

/**
 *
 * @author drupalex
 */
public class NutrixIngredientScenario extends NutrixAbstractScenario {

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new NutrixIngredientSteps());
    }
}
