package tests;

import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static utils.TestData.BASE_URL;

public class NavigationTest extends BaseTest{
    @Test
    public void testBaseUrl() {
        getPage().navigate(BASE_URL);

        assertThat(getPage()).hasURL(BASE_URL);
    }
}
