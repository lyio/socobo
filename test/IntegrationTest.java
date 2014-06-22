import org.junit.Ignore;
import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class IntegrationTest {


    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(9001, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:9001");
            assertThat(browser.pageSource()).contains("Your new application is ready.");
        });
    }

    @Test
    @Ignore
    public void testProductsListing() {
        running(testServer(9001, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:9001/recipes");
            assertThat(browser.pageSource()).contains("Recipe list");
        });
    }

}
