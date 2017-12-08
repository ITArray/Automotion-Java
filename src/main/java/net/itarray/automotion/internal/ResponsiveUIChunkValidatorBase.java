package net.itarray.automotion.internal;

import net.itarray.automotion.internal.geometry.ConnectedIntervals;
import net.itarray.automotion.internal.geometry.Interval;
import net.itarray.automotion.internal.geometry.Scalar;
import net.itarray.automotion.internal.geometry.Vector;
import net.itarray.automotion.validation.ChunkUIElementValidator;
import net.itarray.automotion.validation.UISnapshot;
import net.itarray.automotion.validation.Units;
import org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import util.validator.ResponsiveUIValidator;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static net.itarray.automotion.internal.UIElement.*;
import static net.itarray.automotion.internal.geometry.Interval.interval;

public class ResponsiveUIChunkValidatorBase extends ResponsiveUIValidatorBase implements ChunkUIElementValidator {

    private final List<UIElement> rootElements;

    public ResponsiveUIChunkValidatorBase(UISnapshot snapshot, List<WebElement> webElements, boolean allowEmpty) {
        super(snapshot);
        if (!allowEmpty && webElements.isEmpty()) {
            String message = "Set root web element";
            addError(message);
        } else {
            if (!getDriver().isAppiumContext()) {
                try {
                    //((JavascriptExecutor) getDriver().getDriver()).executeScript("arguments[0].scrollIntoView();", webElements.get(0));
                    //((JavascriptExecutor) getDriver().getDriver()).executeScript("javascript:window.scrollBy(0,250);");
                    ((JavascriptExecutor) getDriver().getDriver()).executeScript("document.documentElement.style.overflow = 'hidden'");
                } catch (Exception e) {}
            }
        }
        rootElements = asElements(webElements);
        doSnapshot();
    }

    @Override
    public ResponsiveUIChunkValidatorBase drawMap() {
        super.drawMap();
        return this;
    }

    @Override
    public ResponsiveUIChunkValidatorBase dontDrawMap() {
        super.dontDrawMap();
        return this;
    }

    /**
     * Change units to Pixels or % (Units.PX, Units.PERCENT)
     *
     * @param units
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase changeMetricsUnitsTo(ResponsiveUIValidator.Units units) {
        return changeMetricsUnitsTo(units.asNewUnits());
    }

    @Override
    public ResponsiveUIChunkValidatorBase changeMetricsUnitsTo(Units units) {
        getReport().changeMetricsUnitsTo(units);
        return this;
    }

    /**
     * Verify that elements are aligned in a grid view width specified amount of columns
     *
     * @param horizontalGridSize
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase alignedAsGrid(int horizontalGridSize) {
        validateGridAlignment(rootElements, horizontalGridSize, 0);
        return this;
    }

    /**
     * Verify that elements are aligned in a grid view width specified amount of columns and rows
     *
     * @param horizontalGridSize
     * @param verticalGridSize
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase alignedAsGrid(int horizontalGridSize, int verticalGridSize) {
        validateGridAlignment(rootElements, horizontalGridSize, verticalGridSize);
        return this;
    }

    @Override
    public ChunkUIElementValidator areAlignedAsGridCells() {
        validateAlignedAsGridCells(rootElements);
        return this;
    }

    public void validateAlignedAsGridCells(List<UIElement> rootElements) {
        ConnectedIntervals columns = new ConnectedIntervals(rootElements.stream().map(e -> e.getXInterval()).collect(Collectors.toList()));
        ConnectedIntervals rows = new ConnectedIntervals(rootElements.stream().map(e -> e.getYInterval()).collect(Collectors.toList()));
        for (UIElement element : rootElements) {
            Interval xInterval = element.getXInterval();
            Interval xCell = columns.get(columns.indexOf(xInterval));
            Interval yInterval = element.getYInterval();
            Interval yCell = rows.get(rows.indexOf(yInterval));
            if (!(xInterval.equals(xCell) && yInterval.equals(yCell))) {
                errors.add(String.format("banane"));
            }
        }
    }

    /**
     * Verify that every element in the list is not overlapped with another element from this list
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase doNotOverlap() {
        validateElementsAreNotOverlapped(rootElements);
        return this;
    }

    /**
     * Verify that elements in the list have the same size
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase haveEqualSize() {
        validateSameSize(asNumberedList(rootElements));
        return this;
    }

    /**
     * Verify that elements in the list have the same width
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase haveEqualWidth() {
        validateSameWidth(asNumberedList(rootElements));
        return this;
    }

    /**
     * Verify that elements in the list have the same height
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase haveEqualHeight() {
        validateSameHeight(asNumberedList(rootElements));
        return this;
    }

    /**
     * Verify that elements in the list have not the same size
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase haveDifferentSizes() {
        validateHaveDifferentSizes(rootElements);
        return this;
    }

    /**
     * Verify that elements in the list have not the same width
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase haveDifferentWidths() {
        validateHaveDifferentWidths(rootElements);
        return this;
    }

    /**
     * Verify that elements in the list have not the same height
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase haveDifferentHeights() {
        validateNotSameHeight(rootElements);
        return this;
    }

    /**
     * Verify that elements in the list have the right offset
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase areRightAligned() {
        validateRightAlignedWithChunk(asNumberedList(rootElements));
        return this;
    }

    /**
     * Verify that elements in the list have the same left offset
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase areLeftAligned() {
        validateLeftAlignedWithChunk(asNumberedList(rootElements));
        return this;
    }

    /**
     * Verify that elements in the list have the same top offset
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase areTopAligned() {
        validateTopAlignedWithChunk(asNumberedList(rootElements));
        return this;
    }

    /**
     * Verify that elements in the list have the same bottom offset
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase areBottomAligned() {
        validateBottomAlignedWithChunk(asNumberedList(rootElements));
        return this;
    }

    /**
     * Verify that every element in the list have equal right and left offset (aligned horizontally in center)
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase areCenteredOnPageVertically() {
        validateCenteredOnPageVertically(rootElements);
        return this;
    }

    /**
     * Verify that every element in the list have equal top and bottom offset (aligned vertically in center)
     *
     * @return ResponsiveUIChunkValidator
     */
    @Override
    public ResponsiveUIChunkValidatorBase areCenteredOnPageHorizontally() {
        validateCenteredOnPageHorizontally(rootElements);
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
    public ResponsiveUIChunkValidatorBase areInsideOf(WebElement containerElement, String readableContainerName) {
        validateInsideOfContainer(asElement(containerElement, readableContainerName), rootElements);
        return this;
    }

    private void validateElementsAreNotOverlapped(List<UIElement> elements) {
        for (int firstIndex = 0; firstIndex < elements.size(); firstIndex++) {
            UIElement first = elements.get(firstIndex);
            for (int secondIndex = firstIndex+1; secondIndex < elements.size(); secondIndex++) {
                UIElement second = elements.get(secondIndex);
                if (first.overlaps(second)) {
                    errors.add("Elements are overlapped", first);
                    break;
                }
            }
        }
    }

    private void validateGridAlignment(List<UIElement> elements, int columns, int rows) {
        SortedMap<Scalar, Integer> map = new TreeMap<>();
        for (UIElement element : elements) {
            int oldCount = map.getOrDefault(element.getY(), 0);
            map.put(element.getY(), oldCount + 1);
        }

        int mapSize = map.size();
        if (rows > 0) {
            if (mapSize != rows) {
                addError(String.format("Elements in a grid are not aligned properly. Looks like grid has wrong amount of rows. Expected is %d. Actual is %d", rows, mapSize));
            }
        }

        if (columns > 0) {
            int errorLastLine = 0;
            int rowCount = 1;
            for (Map.Entry<Scalar, Integer> entry : map.entrySet()) {
                if (rowCount <= mapSize) {
                    int actualInARow = entry.getValue();
                    if (actualInARow != columns) {
                        errorLastLine++;
                        if (errorLastLine > 1 || actualInARow > columns) {
                            addError(String.format("Elements in a grid are not aligned properly in row #%d. Expected %d elements in a row. Actually it's %d", rowCount, columns, actualInARow));
                        }
                    }
                    rowCount++;
                }
            }
        }
    }

    private void validateRightAlignedWithChunk(List<UIElement> elements) {
        int oldErrorsSize = errors.getMessages().size();
        for (int i = 0; i < elements.size() - 1; i++) {
            UIElement element = elements.get(i);
            UIElement elementToCompare = elements.get(i + 1);
            element.validateRightAlignedWith(elementToCompare, errors);
        }
        if (errors.getMessages().size() != oldErrorsSize) {
            Vector onLine = elements.get(0).getCorner();
            drawVerticalLine(onLine);
        }
    }

    private void validateLeftAlignedWithChunk(List<UIElement> elements) {
        int oldErrorsSize = errors.getMessages().size();
        for (int i = 0; i < elements.size() - 1; i++) {
            UIElement element = elements.get(i);
            UIElement elementToCompare = elements.get(i + 1);
            element.validateLeftAlignedWith(elementToCompare, errors);
        }
        if (errors.getMessages().size() != oldErrorsSize) {
            drawVerticalLine(elements.get(0).getOrigin());
        }
    }

    private void validateTopAlignedWithChunk(List<UIElement> elements) {
        int oldErrorsSize = errors.getMessages().size();
        for (int i = 0; i < elements.size() - 1; i++) {
            UIElement element = elements.get(i);
            UIElement elementToCompare = elements.get(i + 1);
            element.validateTopAlignedWith(elementToCompare, errors);
        }
        if (errors.getMessages().size() != oldErrorsSize) {
            drawHorizontalLine(elements.get(0).getOrigin());
        }
    }

    private void validateBottomAlignedWithChunk(List<UIElement> elements) {
        int oldErrorsSize = errors.getMessages().size();
        for (int i = 0; i < elements.size() - 1; i++) {
            UIElement element = elements.get(i);
            UIElement elementToCompare = elements.get(i + 1);
            element.validateBottomAlignedWith(elementToCompare, this.errors);
        }
        if (errors.getMessages().size() != oldErrorsSize) {
            drawHorizontalLine(elements.get(0).getCorner());
        }
    }

    private void validateSameWidth(List<UIElement> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            UIElement element = elements.get(i);
            UIElement elementToCompare = elements.get(i + 1);
            if (!element.hasSameWidthAs(elementToCompare, getContext())) {
                errors.add(String.format("Element %s has different width than element %s.", element.getQuotedName(), elementToCompare.getQuotedName()), element);
                errors.add(String.format("Element %s has different width than element %s.", elementToCompare.getQuotedName(), element.getQuotedName()), elementToCompare);
            }
        }
    }

    private void validateSameHeight(List<UIElement> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            UIElement element = elements.get(i);
            UIElement elementToCompare = elements.get(i + 1);
            if (!element.hasSameHeightAs(elementToCompare, getContext())) {
                errors.add(String.format("Element %s has different height than element %s.", element.getQuotedName(), elementToCompare.getQuotedName()), element);
                errors.add(String.format("Element %s has different height than element %s.", elementToCompare.getQuotedName(), element.getQuotedName()), elementToCompare);
            }
        }
    }

    private void validateSameSize(List<UIElement> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            UIElement element = elements.get(i);
            UIElement elementToCompare = elements.get(i + 1);
            if (!element.hasSameSizeAs(elementToCompare, getContext())) {
                // todo: one error message + visual feedback
                errors.add(String.format("Element %s has different size than element %s.", element.getQuotedName(), elementToCompare.getQuotedName()), element);
                errors.add(String.format("Element %s has different size than element %s.", elementToCompare.getQuotedName(), element.getQuotedName()), elementToCompare);
            }

        }
    }

    private void validateHaveDifferentSizes(List<UIElement> elements) {
        for (int firstIndex = 0; firstIndex < elements.size(); firstIndex++) {
            UIElement element = elements.get(firstIndex);
            for (int secondIndex = firstIndex+1; secondIndex < elements.size(); secondIndex++) {
                UIElement elementToCompare = elements.get(secondIndex);
                if (element.hasSameSizeAs(elementToCompare, getContext())) {
                    errors.add(String.format("Element #%d has same size. Element size is: [%s, %s]", (firstIndex + 1), element.getWidth(), element.getHeight()), element);
                    errors.add(String.format("Element #%d has same size. Element size is: [%s, %s]", (secondIndex + 1), elementToCompare.getWidth(), elementToCompare.getHeight()), elementToCompare);
                }
            }
        }
    }

    private void validateHaveDifferentWidths(List<UIElement> elements) {
        for (int firstIndex = 0; firstIndex < elements.size(); firstIndex++) {
            UIElement element = elements.get(firstIndex);
            for (int secondIndex = firstIndex+1; secondIndex < elements.size(); secondIndex++) {
                UIElement elementToCompare = elements.get(secondIndex);
                if (element.hasSameWidthAs(elementToCompare, getContext())) {
                    errors.add(String.format("Element #%d has same width. Element width is: [%s, %s]", (firstIndex + 1), element.getWidth(), element.getHeight()), element);
                    errors.add(String.format("Element #%d has same width. Element width is: [%s, %s]", (secondIndex + 2), elementToCompare.getWidth(), elementToCompare.getHeight()), elementToCompare);
                }
            }
        }
    }

    private void validateNotSameHeight(List<UIElement> elements) {
        for (int firstIndex = 0; firstIndex < elements.size(); firstIndex++) {
            UIElement element = elements.get(firstIndex);
            for (int secondIndex = firstIndex+1; secondIndex < elements.size(); secondIndex++) {
                UIElement elementToCompare = elements.get(secondIndex);
                if (element.hasSameHeightAs(elementToCompare, getContext())) {
                    errors.add(String.format("Element #%d has same height. Element height is: [%s, %s]", (firstIndex + 1), element.getWidth(), element.getHeight()), element);
                    errors.add(String.format("Element #%d has same height. Element height is: [%s, %s]", (secondIndex + 2), elementToCompare.getWidth(), elementToCompare.getHeight()), elementToCompare);
                }
            }
        }
    }

    private void validateCenteredOnPageVertically(List<UIElement> elements) {
        for (UIElement element : elements) {
            element.validateCenteredOnVertically(page, getContext(), errors);
        }
    }

    private void validateCenteredOnPageHorizontally(List<UIElement> elements) {
        for (UIElement element : elements) {
            element.validateCenteredOnHorizontally(page, getContext(), errors);
        }
    }

    private void validateInsideOfContainer(UIElement containerElement, List<UIElement> elements) {
        for (UIElement element : elements) {
            element.validateInsideOfContainer(containerElement, errors);
        }
    }

    @Override
    protected String getNameOfToBeValidated() {
        return "Root Element";
    }

    @Override
    protected void storeRootDetails(JSONObject rootDetails) {
    }

    @Override
    protected void drawRootElement() {
        if (!rootElements.isEmpty()) {
            drawElement(rootElements.get(0));
        }
    }
}