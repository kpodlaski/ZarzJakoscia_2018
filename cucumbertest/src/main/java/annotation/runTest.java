package annotation;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Krzysztof Podlaski on 14.05.2018.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:target/cucumber"}
)

//@Cucumber.Options(format = {"pretty", "html:target/cucumber"})
public class runTest {
}
