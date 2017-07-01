package net.itarray.automotion.internal;

import http.helpers.TextFinder;
import net.itarray.automotion.validation.Units;
import net.itarray.automotion.validation.UIElementValidator;
import net.itarray.automotion.validation.UISnapshot;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import util.general.SystemHelper;
import net.itarray.automotion.validation.properties.Padding;

import java.awt.geom.Rectangle2D;
import java.util.List;

import static net.itarray.automotion.internal.UIElement.*;
import static net.itarray.automotion.validation.Constants.*;

public class UIValidatorBase extends ResponsiveUIValidatorBase implements UIElementValidator {

    private static final int MIN_OFFSET = -10000;

    private final OffsetLineCommands offsetLineCommands = new OffsetLineCommands();
    private final UIElement rootElement;


    public UIValidatorBase(UISnapshot snapshot, WebElement webElement, String readableNameOfElement) {
        super(snapshot);
        this.rootElement = asElement(webElement, readableNameOfElement);
    }

    @Override
    public UIValidatorBase drawMap() {
        super.drawMap();
        return this;
    }

    @Override
    public UIValidatorBase dontDrawMap() {
        super.dontDrawMap();
        return this;
    }

    /**
     * Change units to Pixels or % (Units.PX, Units.PERCENT)
     *
     * @param units
     * @return UIValidator
     */
    public UIValidatorBase changeMetricsUnitsTo(Units units) {
        getReport().changeMetricsUnitsTo(units);
        return this;
    }

    public UIValidatorBase changeMetricsUnitsTo(util.validator.ResponsiveUIValidator.Units units) {
        getReport().changeMetricsUnitsTo(units.asNewUnits());
        return this;
    }

    /**
     * Verify that element which located left to is correct
     *
     * @param element
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withLeftElement(WebElement element) {
        validateLeftElement(asElement(element));
        return this;
    }

    /**
     * Verify that element which located left to is correct with specified margins
     *
     * @param element
     * @param minMargin
     * @param maxMargin
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withLeftElement(WebElement element, int minMargin, int maxMargin) {
        validateLeftElement(asElement(element), getConvertedInt(minMargin, true), getConvertedInt(maxMargin, true));
        return this;
    }

    /**
     * Verify that element which located right to is correct
     *
     * @param element
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withRightElement(WebElement element) {
        validateRightElement(asElement(element));
        return this;
    }

    /**
     * Verify that element which located right to is correct with specified margins
     *
     * @param element
     * @param minMargin
     * @param maxMargin
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withRightElement(WebElement element, int minMargin, int maxMargin) {
        validateRightElement(asElement(element), getConvertedInt(minMargin, true), getConvertedInt(maxMargin, true));
        return this;
    }

    /**
     * Verify that element which located top to is correct
     *
     * @param element
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withTopElement(WebElement element) {
        validateAboveElement(asElement(element));
        return this;
    }

    /**
     * Verify that element which located top to is correct with specified margins
     *
     * @param element
     * @param minMargin
     * @param maxMargin
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withTopElement(WebElement element, int minMargin, int maxMargin) {
        validateAboveElement(asElement(element), getConvertedInt(minMargin, false), getConvertedInt(maxMargin, false));
        return this;
    }

    /**
     * Verify that element which located bottom to is correct
     *
     * @param element
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withBottomElement(WebElement element) {
        validateBelowElement(asElement(element));
        return this;
    }

    /**
     * Verify that element which located bottom to is correct with specified margins
     *
     * @param element
     * @param minMargin
     * @param maxMargin
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withBottomElement(WebElement element, int minMargin, int maxMargin) {
        validateBelowElement(asElement(element), getConvertedInt(minMargin, false), getConvertedInt(maxMargin, false));
        return this;
    }

    /**
     * Verify that element is NOT overlapped with specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase notOverlapWith(WebElement element, String readableName) {
        validateNotOverlappingWithElements(asElement(element, readableName));
        return this;
    }

    /**
     * Verify that element is overlapped with specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase overlapWith(WebElement element, String readableName) {
        validateOverlappingWithElements(asElement(element, readableName));
        return this;
    }

    /**
     * Verify that element is NOT overlapped with every element is the list
     *
     * @param elements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase notOverlapWith(List<WebElement> elements) {
        for (WebElement element : elements) {
            validateNotOverlappingWithElements(asElement(element));
        }
        return this;
    }

    /**
     * Verify that element has the same left offset as specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameOffsetLeftAs(WebElement element, String readableName) {
        validateLeftOffsetForElements(asElement(element, readableName));
        drawLeftOffsetLine();
        return this;
    }

    /**
     * Verify that element has the same left offset as every element is the list
     *
     * @param webElements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameOffsetLeftAs(List<WebElement> webElements) {
        for (UIElement element : asElements(webElements)) {
            validateLeftOffsetForElements(element);
        }
        drawLeftOffsetLine();
        return this;
    }

    /**
     * Verify that element has the same right offset as specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameOffsetRightAs(WebElement element, String readableName) {
        validateRightOffsetForElements(asElement(element, readableName));
        drawRightOffsetLine();
        return this;
    }

    /**
     * Verify that element has the same right offset as every element is the list
     *
     * @param elements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameOffsetRightAs(List<WebElement> elements) {
        for (WebElement element : elements) {
            validateRightOffsetForElements(asElement(element));
        }
        drawRightOffsetLine();
        return this;
    }

    /**
     * Verify that element has the same top offset as specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameOffsetTopAs(WebElement element, String readableName) {
        validateTopOffsetForElements(asElement(element, readableName));
        drawTopOffsetLine();
        return this;
    }

    /**
     * Verify that element has the same top offset as every element is the list
     *
     * @param elements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameOffsetTopAs(List<WebElement> elements) {
        for (WebElement element : elements) {
            validateTopOffsetForElements(asElement(element));
        }
        drawTopOffsetLine();
        return this;
    }

    /**
     * Verify that element has the same bottom offset as specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameOffsetBottomAs(WebElement element, String readableName) {
        validateBottomOffsetForElements(asElement(element, readableName));
        drawBottomOffsetLine();
        return this;
    }

    /**
     * Verify that element has the same bottom offset as every element is the list
     *
     * @param elements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameOffsetBottomAs(List<WebElement> elements) {
        for (WebElement element : elements) {
            validateBottomOffsetForElements(asElement(element));
        }
        drawBottomOffsetLine();
        return this;
    }

    /**
     * Verify that element has the same width as specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameWidthAs(WebElement element, String readableName) {
        validateSameWidth(asElement(element, readableName));
        return this;
    }

    /**
     * Verify that element has the same width as every element in the list
     *
     * @param elements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameWidthAs(List<WebElement> elements) {
        for (WebElement element : elements) {
            validateSameWidth(asElement(element));
        }
        return this;
    }

    /**
     * Verify that width of element is not less than specified
     *
     * @param width
     * @return UIValidator
     */
    @Override
    public UIValidatorBase minWidth(int width) {
        validateMinWidth(getConvertedInt(width, true));
        return this;
    }

    /**
     * Verify that width of element is not bigger than specified
     *
     * @param width
     * @return UIValidator
     */
    @Override
    public UIValidatorBase maxWidth(int width) {
        validateMaxWidth(getConvertedInt(width, true));
        return this;
    }

    /**
     * Verify that width of element is in range
     *
     * @param min
     * @param max
     * @return UIValidator
     */
    @Override
    public UIValidatorBase widthBetween(int min, int max) {
        validateMinWidth(getConvertedInt(min, true));
        validateMaxWidth(getConvertedInt(max, true));
        return this;
    }

    /**
     * Verify that element has the same height as specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameHeightAs(WebElement element, String readableName) {
        validateSameHeight(asElement(element, readableName));
        return this;
    }

    /**
     * Verify that element has the same height as every element in the list
     *
     * @param elements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameHeightAs(List<WebElement> elements) {
        for (WebElement element : elements) {
            validateSameHeight(asElement(element));
        }
        return this;
    }

    /**
     * Verify that height of element is not less than specified
     *
     * @param height
     * @return UIValidator
     */
    @Override
    public UIValidatorBase minHeight(int height) {
        validateMinHeight(getConvertedInt(height, false));
        return this;
    }

    /**
     * Verify that height of element is not bigger than specified
     *
     * @param height
     * @return UIValidator
     */
    @Override
    public UIValidatorBase maxHeight(int height) {
        validateMaxHeight(getConvertedInt(height, false));
        return this;
    }

    /**
     * Verify that element has the same size as specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameSizeAs(WebElement element, String readableName) {
        validateSameSize(asElement(element, readableName));
        return this;
    }

    /**
     * Verify that element has the same size as every element in the list
     *
     * @param elements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase sameSizeAs(List<WebElement> elements) {
        for (WebElement element : elements) {
            validateSameSize(asElement(element));
        }
        return this;
    }

    /**
     * Verify that element has not the same size as specified element
     *
     * @param element
     * @param readableName
     * @return UIValidator
     */
    @Override
    public UIValidatorBase notSameSizeAs(WebElement element, String readableName) {
        validateNotSameSize(asElement(element, readableName));
        return this;
    }

    /**
     * Verify that element has not the same size as every element in the list
     *
     * @param elements
     * @return UIValidator
     */
    @Override
    public UIValidatorBase notSameSizeAs(List<WebElement> elements) {
        for (WebElement element : elements) {
            validateNotSameSize(asElement(element));
        }
        return this;
    }

    /**
     * Verify that height of element is in range
     *
     * @param min
     * @param max
     * @return UIValidator
     */
    @Override
    public UIValidatorBase heightBetween(int min, int max) {
        validateMinHeight(getConvertedInt(min, false));
        validateMaxHeight(getConvertedInt(max, false));
        return this;
    }

    /**
     * Verify that min offset of element is not less than (min value is -10000)
     *
     * @param top
     * @param right
     * @param bottom
     * @param left
     * @return UIValidator
     */
    @Override
    public UIValidatorBase minOffset(int top, int right, int bottom, int left) {
        if (getConvertedInt(top, false) > MIN_OFFSET && getConvertedInt(right, true) > MIN_OFFSET && getConvertedInt(bottom, false) > MIN_OFFSET && getConvertedInt(left, true) > MIN_OFFSET) {
            validateMinOffset(getConvertedInt(top, false), getConvertedInt(right, true), getConvertedInt(bottom, false), getConvertedInt(left, true));
        }
        return this;
    }

    /**
     * Verify that max offset of element is not bigger than (min value is -10000)
     *
     * @param top
     * @param right
     * @param bottom
     * @param left
     * @return UIValidator
     */
    @Override
    public UIValidatorBase maxOffset(int top, int right, int bottom, int left) {
        if (getConvertedInt(top, false) > MIN_OFFSET && getConvertedInt(right, true) > MIN_OFFSET && getConvertedInt(bottom, false) > MIN_OFFSET && getConvertedInt(left, true) > MIN_OFFSET) {
            validateMaxOffset(getConvertedInt(top, false), getConvertedInt(right, true), getConvertedInt(bottom, false), getConvertedInt(left, true));
        }
        return this;
    }

    /**
     * Verify that element has correct CSS values
     *
     * @param cssProperty
     * @param args
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withCssValue(String cssProperty, String... args) {
        String cssValue = rootElement.getCssValue(cssProperty);

        if (!cssValue.equals("")) {
            for (String val : args) {
                val = !val.startsWith("#") ? val : SystemHelper.hexStringToARGB(val);
                if (!TextFinder.textIsFound(val, cssValue)) {
                    addError(String.format("Expected value of '%s' is '%s'. Actual value is '%s'", cssProperty, val, cssValue));
                }
            }
        } else {
            addError(String.format("Element '%s' does not have css property '%s'", rootElement.getName(), cssProperty));
        }
        return this;
    }

    /**
     * Verify that concrete CSS values are absent for specified element
     *
     * @param cssProperty
     * @param args
     * @return UIValidator
     */
    @Override
    public UIValidatorBase withoutCssValue(String cssProperty, String... args) {
        String cssValue = rootElement.getCssValue(cssProperty);

        if (!cssValue.equals("")) {
            for (String val : args) {
                val = !val.startsWith("#") ? val : SystemHelper.hexStringToARGB(val);
                if (TextFinder.textIsFound(val, cssValue)) {
                    addError(String.format("CSS property '%s' should not contain value '%s'. Actual value is '%s'", cssProperty, val, cssValue));
                }
            }
        } else {
            addError(String.format("Element '%s' does not have css property '%s'", rootElement.getName(), cssProperty));
        }
        return this;
    }

    /**
     * Verify that element has equal left and right offsets (e.g. Bootstrap container)
     *
     * @return UIValidator
     */
    @Override
    public UIValidatorBase equalLeftRightOffset() {
        validateEqualLeftRightOffset(rootElement);
        return this;
    }

    /**
     * Verify that element has equal top and bottom offset (aligned vertically in center)
     *
     * @return UIValidator
     */
    @Override
    public UIValidatorBase equalTopBottomOffset() {
        validateEqualTopBottomOffset(rootElement);
        return this;
    }

    /**
     * Verify that element(s) is(are) located inside of specified element
     *
     * @param containerElement
     * @param readableContainerName
     * @return ResponsiveUIValidator
     */
    @Override
    public UIValidatorBase insideOf(WebElement containerElement, String readableContainerName) {
        validateInsideOfContainer(asElement(containerElement, readableContainerName));
        return this;
    }

    @Override
    public UIValidatorBase insideOf(WebElement containerElement, String readableContainerName, Padding padding) {
        validateInsideOfContainer(asElement(containerElement, readableContainerName), padding);
        return this;
    }

    private void validateRightOffsetForElements(UIElement element) {
        if (!rootElement.hasEqualRightOffsetAs(element)) {
            addError(String.format("Element '%s' has not the same right offset as element '%s'", rootElement.getName(), element.getName()), element);
        }
    }

    private void validateLeftOffsetForElements(UIElement element) {
        if (!rootElement.hasEqualLeftOffsetAs(element)) {
            addError(String.format("Element '%s' has not the same left offset as element '%s'", rootElement.getName(), element.getName()), element);
        }
    }

    private void validateTopOffsetForElements(UIElement element) {
        if (!rootElement.hasEqualTopOffsetAs(element)) {
            addError(String.format("Element '%s' has not the same top offset as element '%s'", rootElement.getName(), element.getName()), element);
        }
    }

    private void validateBottomOffsetForElements(UIElement element) {
        if (!rootElement.hasEqualBottomOffsetAs(element)) {
            addError(String.format("Element '%s' has not the same bottom offset as element '%s'", rootElement.getName(), element.getName()), element);
        }
    }

    private void validateNotOverlappingWithElements(UIElement element) {
        if (rootElement.overlaps(element)) {
            addError(String.format("Element '%s' is overlapped with element '%s' but should not", rootElement.getName(), element.getName()), element);
        }
    }

    private void validateOverlappingWithElements(UIElement element) {
        if (!rootElement.overlaps(element)) {
            addError(String.format("Element '%s' is not overlapped with element '%s' but should be", rootElement.getName(), element.getName()), element);
        }
    }

    private void validateMaxOffset(int top, int right, int bottom, int left) {
        int rootElementRightOffset = rootElement.getRightOffset(pageSize);
        int rootElementBottomOffset = rootElement.getBottomOffset(pageSize);
        if (rootElement.getX() > left) {
            addError(String.format("Expected max left offset of element  '%s' is: %spx. Actual left offset is: %spx", rootElement.getName(), left, rootElement.getX()));
        }
        if (rootElement.getY() > top) {
            addError(String.format("Expected max top offset of element '%s' is: %spx. Actual top offset is: %spx", rootElement.getName(), top, rootElement.getY()));
        }
        if (rootElementRightOffset > right) {
            addError(String.format("Expected max right offset of element  '%s' is: %spx. Actual right offset is: %spx", rootElement.getName(), right, rootElementRightOffset));
        }
        if (rootElementBottomOffset > bottom) {
            addError(String.format("Expected max bottom offset of element  '%s' is: %spx. Actual bottom offset is: %spx", rootElement.getName(), bottom, rootElementBottomOffset));
        }
    }

    private void validateMinOffset(int top, int right, int bottom, int left) {
        int rootElementRightOffset = rootElement.getRightOffset(pageSize);
        int rootElementBottomOffset = rootElement.getBottomOffset(pageSize);
        if (rootElement.getX() < left) {
            addError(String.format("Expected min left offset of element  '%s' is: %spx. Actual left offset is: %spx", rootElement.getName(), left, rootElement.getX()));
        }
        if (rootElement.getY() < top) {
            addError(String.format("Expected min top offset of element  '%s' is: %spx. Actual top offset is: %spx", rootElement.getName(), top, rootElement.getY()));
        }
        if (rootElementRightOffset < right) {
            addError(String.format("Expected min top offset of element  '%s' is: %spx. Actual right offset is: %spx", rootElement.getName(), right, rootElementRightOffset));
        }
        if (rootElementBottomOffset < bottom) {
            addError(String.format("Expected min bottom offset of element  '%s' is: %spx. Actual bottom offset is: %spx", rootElement.getName(), bottom, rootElementBottomOffset));
        }
    }

    private void validateMaxHeight(int height) {
        if (!rootElement.hasMaxHeight(height)) {
            addError(String.format("Expected max height of element  '%s' is: %spx. Actual height is: %spx", rootElement.getName(), height, rootElement.getHeight()));
        }
    }

    private void validateMinHeight(int height) {
        if (!rootElement.hasMinHeight(height)) {
            addError(String.format("Expected min height of element '%s' is: %spx. Actual height is: %spx", rootElement.getName(), height, rootElement.getHeight()));
        }
    }

    private void validateMaxWidth(int width) {
        if (!rootElement.hasMaxWidth(width)) {
            addError(String.format("Expected max width of element '%s' is: %spx. Actual width is: %spx", rootElement.getName(), width, rootElement.getWidth()));
        }
    }

    private void validateMinWidth(int width) {
        if (!rootElement.hasMinWidth(width)) {
            addError(String.format("Expected min width of element '%s' is: %spx. Actual width is: %spx", rootElement.getName(), width, rootElement.getWidth()));
        }
    }

    private void validateSameWidth(UIElement element) {
        if (!rootElement.hasSameWidthAs(element)) {
            addError(String.format("Element '%s' has not the same width as %s. Width of '%s' is %spx. Width of element is %spx", rootElement.getName(), element.getName(), rootElement.getName(), rootElement.getWidth(), element.getWidth()), element);
        }
    }

    private void validateSameHeight(UIElement element) {
        if (!rootElement.hasSameHeightAs(element)) {
            addError(String.format("Element '%s' has not the same height as %s. Height of '%s' is %spx. Height of element is %spx", rootElement.getName(), element.getName(), rootElement.getName(), rootElement.getHeight(), element.getHeight()), element);
        }
    }

    private void validateSameSize(UIElement element) {
        if (!rootElement.hasSameSizeAs(element)) {
            addError(String.format("Element '%s' has not the same size as %s. Size of '%s' is %spx x %spx. Size of element is %spx x %spx", rootElement.getName(), element.getName(), rootElement.getName(), rootElement.getWidth(), rootElement.getHeight(), element.getWidth(), element.getHeight()), element);
        }
    }

    private void validateNotSameSize(UIElement element) {
        if (!element.hasEqualWebElement(rootElement)) {
            int h = element.getHeight();
            int w = element.getWidth();
            if (h == rootElement.getHeight() && w == rootElement.getWidth()) {
                addError(String.format("Element '%s' has the same size as %s. Size of '%s' is %spx x %spx. Size of element is %spx x %spx", rootElement.getName(), element.getName(), rootElement.getName(), rootElement.getWidth(), rootElement.getHeight(), w, h), element);
            }
        }
    }

    private void validateBelowElement(UIElement element, int minMargin, int maxMargin) {
        int marginBetweenRoot = element.getY() - rootElement.getCornerY();
        if (marginBetweenRoot < minMargin || marginBetweenRoot > maxMargin) {
            addError(String.format("Below element aligned not properly. Expected margin should be between %spx and %spx. Actual margin is %spx", minMargin, maxMargin, marginBetweenRoot), element);
        }
    }

    private void validateBelowElement(UIElement belowElement) {
        if (!rootElement.hasBelowElement(belowElement)) {
            addError("Below element aligned not properly", belowElement);
        }
    }

    private void validateAboveElement(UIElement element, int minMargin, int maxMargin) {
        int marginBetweenRoot = rootElement.getY() - element.getCornerY();
        if (marginBetweenRoot < minMargin || marginBetweenRoot > maxMargin) {
            addError(String.format("Above element aligned not properly. Expected margin should be between %spx and %spx. Actual margin is %spx", minMargin, maxMargin, marginBetweenRoot), element);
        }
    }

    private void validateAboveElement(UIElement aboveElement) {
        if (!rootElement.hasAboveElement(aboveElement)) {
            addError("Above element aligned not properly", aboveElement);
        }
    }

    private void validateRightElement(UIElement element, int minMargin, int maxMargin) {
        int marginBetweenRoot = element.getX() - rootElement.getCornerX();
        if (marginBetweenRoot < minMargin || marginBetweenRoot > maxMargin) {
            addError(String.format("Right element aligned not properly. Expected margin should be between %spx and %spx. Actual margin is %spx", minMargin, maxMargin, marginBetweenRoot), element);
        }
    }

    private void validateRightElement(UIElement rightElement) {
        if (!rootElement.hasRightElement(rightElement)) {
            addError("Right element aligned not properly", rightElement);
        }
    }

    private void validateLeftElement(UIElement leftElement, int minMargin, int maxMargin) {
        int marginBetweenRoot = rootElement.getX() - leftElement.getCornerX();
        if (marginBetweenRoot < minMargin || marginBetweenRoot > maxMargin) {
            addError(String.format("Left element aligned not properly. Expected margin should be between %spx and %spx. Actual margin is %spx", minMargin, maxMargin, marginBetweenRoot), leftElement);
        }
    }

    private void validateLeftElement(UIElement leftElement) {
        if (!rootElement.hasLeftElement(leftElement)) {
            addError("Left element aligned not properly", leftElement);
        }
    }

    private void validateEqualLeftRightOffset(UIElement element) {
        if (!element.hasEqualLeftRightOffset(pageSize)) {
            addError(String.format("Element '%s' has not equal left and right offset. Left offset is %dpx, right is %dpx", rootElement.getName(), element.getX(), element.getRightOffset(pageSize)), element);
        }
    }

    private void validateEqualTopBottomOffset(UIElement element) {
        if (!element.hasEqualTopBottomOffset(pageSize)) {
            addError(String.format("Element '%s' has not equal top and bottom offset. Top offset is %dpx, bottom is %dpx", rootElement.getName(), element.getY(), element.getBottomOffset(pageSize)), element);
        }
    }

    private void validateInsideOfContainer(UIElement containerElement) {
        if (!containerElement.contains(rootElement)) {
            addError(String.format("Element '%s' is not inside of '%s'", rootElement.getName(), containerElement.getName()), containerElement);
        }
    }

    private void validateInsideOfContainer(UIElement element, Padding padding) {
        int top = getConvertedInt(padding.getTop(), false);
        int right = getConvertedInt(padding.getRight(), true);
        int bottom = getConvertedInt(padding.getBottom(), false);
        int left = getConvertedInt(padding.getLeft(), true);

        Rectangle2D.Double paddedRootRectangle = new Rectangle2D.Double(
                rootElement.getX() - left,
                rootElement.getY() - top,
                rootElement.getWidth() + left + right,
                rootElement.getHeight() + top + bottom);

        int paddingTop = rootElement.getY() - element.getY();
        int paddingLeft = rootElement.getX() - element.getX();
        int paddingBottom = element.getCornerY() - rootElement.getCornerY();
        int paddingRight = element.getCornerX() - rootElement.getCornerX();

        if (!element.rectangle().contains(paddedRootRectangle)) {
            addError(String.format("Padding of element '%s' is incorrect. Expected padding: top[%d], right[%d], bottom[%d], left[%d]. Actual padding: top[%d], right[%d], bottom[%d], left[%d]",
                    rootElement.getName(), top, right, bottom, left, paddingTop, paddingRight, paddingBottom, paddingLeft), element);
        }
    }

    @Override
    protected String getNameOfToBeValidated() {
        return rootElement.getName();
    }

    @Override
    protected void storeRootDetails(JSONObject rootDetails) {
        rootDetails.put(X, rootElement.getX());
        rootDetails.put(Y, rootElement.getY());
        rootDetails.put(WIDTH, rootElement.getWidth());
        rootDetails.put(HEIGHT, rootElement.getHeight());
    }

    @Override
    protected void drawRootElement(DrawableScreenshot screenshot) {
        screenshot.drawRootElement(rootElement);
    }

    @Override
    protected void drawOffsets(DrawableScreenshot screenshot) {
        screenshot.drawOffsets(rootElement, offsetLineCommands);
    }


    private void drawLeftOffsetLine() {
        offsetLineCommands.drawLeftOffsetLine();
    }

    private void drawRightOffsetLine() {
        offsetLineCommands.drawRightOffsetLine();
    }

    private void drawTopOffsetLine() {
        offsetLineCommands.drawTopOffsetLine();
    }

    private void drawBottomOffsetLine() {
        offsetLineCommands.drawBottomOffsetLine();
    }


}