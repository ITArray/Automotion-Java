import http.helpers.EnvironmentHelper;
import net.itarray.automotion.validation.ResponsiveUIValidator;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import util.driver.DriverHelper;
import util.driver.WebDriverFactory;
import net.itarray.automotion.validation.properties.Padding;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Ignore
public class ResponsiveValidatorNewDSLTest {

    private static WebDriver driver;

    public static void main(String[] args) {
        ResponsiveValidatorNewDSLTest test = new ResponsiveValidatorNewDSLTest();
        try {
            test.testThatResponsiveValidatorWorks();
        } finally {
            test.tearDown();
        }
    }
    
    @Test
    public void testThatResponsiveValidatorWorks() {
        Map<String, String> sysProp = new HashMap<>();
        //sysProp.put("BROWSER", "Chrome");
        //sysProp.put("IS_LOCAL", "true");
        sysProp.put("IS_HEADLESS", "true");
        sysProp.put(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/" + System.getProperty("user.name") + "/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");
        EnvironmentHelper.setEnv(sysProp);
        WebDriverFactory driverFactory = new WebDriverFactory();
        driver = driverFactory.getDriver();
        driver.get("http://visual.itarray.net");
        driver.manage().window().maximize();

        TestPage page = new TestPage(driver);

        ResponsiveUIValidator responsiveUIValidator = new ResponsiveUIValidator(driver);

        responsiveUIValidator.setLinesColor(Color.BLACK);
        SoftAssertions softly = new SoftAssertions();

        boolean success1 = responsiveUIValidator.snaphost("Validation of Top Slider Element")
                .findElement(page.topSlider(), "Top Slider")
                .sameOffsetLeftAs(page.gridContainer(), "Grid Container")
                .sameOffsetBottomAs(page.topTextBlock(), "Text Block")
                .changeMetricsUnitsTo(ResponsiveUIValidator.Units.PX)
                .widthBetween(300, 500)
                .sameSizeAs(page.gridElements())
                .equalLeftRightOffset()
                .equalTopBottomOffset()
                .insideOf(page.mainContainer(), "Main container", new Padding(10, 50, 10, 20))
                .validate();

        softly.assertThat(success1).isEqualTo(true).overridingErrorMessage("Failed validation of Top Slider element");

        boolean success0 = responsiveUIValidator.snaphost("Validation of Grid view")
                .findElement(page.gridContainer(), "Grid Container")
                .equalLeftRightOffset()
                .validate();

        softly.assertThat(success0).isEqualTo(true).overridingErrorMessage("Failed validation of Grid Container");

        boolean success01 = responsiveUIValidator.snaphost("Validation of Main container")
                .findElement(page.mainContainer(), "Main Container")
                .equalLeftRightOffset()
                .validate();

        softly.assertThat(success01).isEqualTo(true).overridingErrorMessage("Failed validation of Main Container");


        boolean success2 = responsiveUIValidator.snaphost("Validation of Top Text block")
                .findElement(page.topTextBlock(), "Top Text block")
                .sameOffsetRightAs(page.gridContainer(), "Grid Container")
                .sameOffsetTopAs(page.topSlider(), "Top Slider")
                .validate();

        softly.assertThat(success2).isEqualTo(true).overridingErrorMessage("Failed validation of Top Text block");

        boolean success3 = responsiveUIValidator.snaphost("Validation of a grid view")
                .findElements(page.gridElements())
                .alignedAsGrid(4, 3)
                .withSameSize()
                .areNotOverlappedWithEachOther()
                .sameTopOffset()
                .equalLeftRightOffset()
                .equalTopBottomOffset()
                .validate();

        softly.assertThat(success3).isEqualTo(true).overridingErrorMessage("Failed validation of Grid");

        for (WebElement card : page.gridElements()) {
            boolean success = responsiveUIValidator.snaphost("Validation of style for each of cards in a grid view")
                    .findElement(card.findElement(By.className("project-details")), "Project details block")
                    .withCssValue("background", "#f8f8f8")
                    .withCssValue("color", "#6f6f6f")
                    .notOverlapWith(card.findElement(By.className("gallery-hover-4col")), "Image Container")
                    .sameWidthAs(card.findElement(By.className("gallery-hover-4col")), "Image Container")
                    .validate();
            softly.assertThat(success).isEqualTo(true).overridingErrorMessage("Failed validation of Grid in a list");
        }

        int[] zoomRange = {50, 70, 100, 120, 150};

        for (int val : zoomRange) {
            DriverHelper.zoomInOutPage(driver, val);
            boolean success = responsiveUIValidator.snaphost("Validate on page zoom " + val + "%")
                    .findElement(page.mainContainer(), "Main container")
                    .equalLeftRightOffset()
                    .sameWidthAs(page.gridContainer(), "Grid Container")
                    .validate();

            softly.assertThat(success).isEqualTo(true).overridingErrorMessage("Failed validation of Container");
        }

        responsiveUIValidator.generateReport("Home Page");

        softly.assertAll();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}