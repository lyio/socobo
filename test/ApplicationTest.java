import controllers.Application;
import org.junit.Test;
import org.springframework.util.Assert;
import play.mvc.Result;
import play.twirl.api.Html;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;


/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class ApplicationTest {

    @Test
    public void renderTemplate() {
        final Html html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }

    @Test
    public void indexController() {
        final Result result = Application.index();
        Assert.notNull(result);
    }
}
