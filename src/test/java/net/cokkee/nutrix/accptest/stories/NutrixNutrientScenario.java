/*
Anh chàng đạo diễn này ghép nối "Steps" với kịch bản?
Câu hỏi: kịch bản ở đâu? đâu có bóng dáng trong file này?
*/
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
        
    /*
        this.getClass().getSimpleName() -> cho ra chuỗi: NutrixNutrientScenario
        "net/cokkee/nutrix/accptest/stories/" +
                    this.getClass().getSimpleName() + "*.story" =>
        
        "net/cokkee/nutrix/accptest/stories/NutrixNutrientScenario*.story" =>
        Đây chính là đường dẫn đến tệp Story
        */
    @Override
	protected List<String> storyPaths() {
        return new StoryFinder().findPaths(
                CodeLocations.codeLocationFromClass(getClass()),
                "net/cokkee/nutrix/accptest/stories/" +
                    this.getClass().getSimpleName() + "*.story", "");
	}
}
