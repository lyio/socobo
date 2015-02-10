package integration;

import org.junit.Ignore;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

@Ignore
public class IntegrationTest {


    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(9001, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:9001");
            assertThat(browser.pageSource()).contains("html");
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
