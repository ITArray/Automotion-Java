package net.itarray.automotion.tools.driver;

import net.itarray.automotion.internal.UIElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static net.itarray.automotion.internal.UIElement.asElements;

public class PageValidator {

    private final static Logger LOG = LoggerFactory.getLogger(PageValidator.class);

    public static boolean elementsAreAlignedHorizontally(List<WebElement> webElements) {
        return privateElementsAreAlignedHorizontally(asElements(webElements));
    }

    private static boolean privateElementsAreAlignedHorizontally(List<UIElement> elements) {
        boolean aligned = false;

        for (int i = 1; i < elements.size(); i++) {
            UIElement previousElement = elements.get(i - 1);
            UIElement currentElement = elements.get(i);

            if (!previousElement.hasRightElement(currentElement)) {
                aligned = false;
                LOG.debug("Wrong item on position: " + i + ".\nPrevious element: " + previousElement.getName() + ".\nCurrent element: " + currentElement.getName() +  ".\nCoord of previous item is [" + previousElement.getX() + "," + previousElement.getY() + "]." +
                        "\nCoord of current item is [" + currentElement.getX() + "," + currentElement.getY() + "]");
                break;
            }

            aligned = true;
        }

        return aligned;
    }

    public static boolean elementsAreAlignedVertically(List<WebElement> webElements) {
        return privateElementsAreAlignedVertically(asElements(webElements));
    }

    private static boolean privateElementsAreAlignedVertically(List<UIElement> elements) {
        boolean aligned = false;

        for (int i = 1; i < elements.size(); i++) {
            UIElement previousElement = elements.get(i - 1);
            UIElement currentElement = elements.get(i);

            if (!previousElement.hasBelowElement(currentElement)) {
                aligned = false;
                LOG.debug("Wrong item on position: " + i + ".\nPrevious element: " + currentElement.getName() + ".\nCurrent element: " + currentElement.getName() +  ".\nCoord of previous item is [" + previousElement.getX() + "," + previousElement.getY() + "]." +
                        "\nCoord of current item is [" + currentElement.getX() + "," + currentElement.getY() + "]");
                break;
            }

            aligned = true;
        }

        return aligned;
    }

    public static boolean elementsAreAlignedProperly(List<WebElement> elements) {
        boolean aligned = false;

        for (int i = 1; i < elements.size(); i++) {
            WebElement elementPrevious = elements.get(i - 1);
            WebElement elementCurrent = elements.get(i);

            Dimension sizeElementPrevious = elementPrevious.getSize();

            Point positionElementPrevious = elementPrevious.getLocation();
            Point positionElementCurrent = elementCurrent.getLocation();

            if ((positionElementCurrent.getY() >= positionElementPrevious.getY() + sizeElementPrevious.getHeight()) ||
                    positionElementCurrent.getX() >= positionElementPrevious.getX() + sizeElementPrevious.getWidth() ||
                    ((positionElementCurrent.getX() < positionElementPrevious.getX() + sizeElementPrevious.getWidth())
                            && positionElementCurrent.getY() >= positionElementPrevious.getY() + sizeElementPrevious.getHeight())) {
                aligned = true;
            } else {
                aligned = false;
                LOG.debug("Wrong item on position: " + i + ".\nPrevious element text is: " + elementPrevious.getText() + ".\nCurrent element text is: " + elementCurrent.getText() +  ".\nCoord of previous item is [" + positionElementPrevious.getX() + "," + positionElementPrevious.getY() + "]." +
                        "\nCoord of current item is [" + positionElementCurrent.getX() + "," + positionElementCurrent.getY() + "]");
                break;
            }
        }

        return aligned;
    }
}
