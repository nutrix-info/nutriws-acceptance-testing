package net.cokkee.nutrix.accptest.stories;

import net.cokkee.nutrix.accptest.steps.NutrixExampleSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

/**
 *
 * @author drupalex
 */
public class NutrixExampleScenario extends NutrixAbstractScenario {
    
    @Override
	public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new NutrixExampleSteps());
	}
}
