package by.epam.task2.parser.builder;

import by.epam.task2.entity.*;
import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.CandyXmlAttribute;
import by.epam.task2.parser.CandyXmlTag;
import by.epam.task2.util.ResourcePathUtil;
import by.epam.task2.validator.XmlFileValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CandiesDomBuilder extends AbstractCandiesBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DocumentBuilder docBuilder;

    public CandiesDomBuilder() throws ParseXMLException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error("CandiesDomBuilder not created", e);
            throw new ParseXMLException(e);
        }
    }

    @Override
    public void buildSetCandies(String fileName) throws ParseXMLException {
        String schemaFileName = ResourcePathUtil.getResourcePath(AbstractCandiesBuilder.SCHEMA_RESOURCE_NAME);
        XmlFileValidator validator = XmlFileValidator.getInstance();
        if (validator.isCorrect(fileName, schemaFileName)) {
            Document doc;
            try {
                doc = docBuilder.parse(fileName);
                Element root = doc.getDocumentElement();
                NodeList caramelCandiesList = root.getElementsByTagName(CandyXmlTag.CARAMEL_CANDY.getName());
                NodeList chocolateCandiesList = root.getElementsByTagName(CandyXmlTag.CHOCOLATE_CANDY.getName());
                for (int i = 0; i < caramelCandiesList.getLength(); i++) {
                    Element candyElement = (Element) caramelCandiesList.item(i);
                    AbstractCandy candy = buildCaramelCandy(candyElement);
                    candies.add(candy);
                }
                for (int i = 0; i < chocolateCandiesList.getLength(); i++) {
                    Element candyElement = (Element) chocolateCandiesList.item(i);
                    AbstractCandy candy = buildChocolateCandy(candyElement);
                    candies.add(candy);
                }
                LOGGER.info("Set of candies is build. " + candies);
            } catch (IOException | SAXException | ParseXMLException exception) {
                LOGGER.error("Exception when build Set of candies", exception);
                throw new ParseXMLException(exception);
            }
        } else {
            LOGGER.info("File '" + fileName + "' does not match schema '" + schemaFileName + "'");
            throw new ParseXMLException("File '" + fileName + "' does not match schema '" + schemaFileName + "'");
        }
    }

    private AbstractCandy buildChocolateCandy(Element candyElement) throws ParseXMLException {
        ChocolateCandy candy = new ChocolateCandy();
        buildCandy(candy, candyElement);
        String fillingAttr = CandyXmlAttribute.FILLING.getName();
        if (candyElement.hasAttribute(fillingAttr)) {
            candy.setFilling(candyElement.getAttribute(fillingAttr));
        }
        candy.setChocolateType(getElementChocolateType(candyElement));
        return candy;
    }

    private AbstractCandy buildCaramelCandy(Element candyElement) throws ParseXMLException {
        CaramelCandy candy = new CaramelCandy();
        buildCandy(candy, candyElement);
        candy.setFlavor(getElementTextContent(candyElement, CandyXmlTag.FLAVOR.getName()));
        candy.setLollipop(getElementBooleanContent(candyElement, CandyXmlTag.LOLLIPOP.getName()));
        return candy;
    }


    private void buildCandy(AbstractCandy candy, Element candyElement) throws ParseXMLException {
        candy.setVendorCode(candyElement.getAttribute(CandyXmlAttribute.VENDOR_CODE.getName()));
        candy.setName(getElementTextContent(candyElement, CandyXmlTag.CANDY_NAME.getName()));
        candy.setProduction(getElementProduction(candyElement));
        candy.setExpirationDate(getElementYearMonthContent(candyElement, CandyXmlTag.EXPIRATION_DATE.getName()));
        candy.setIngredients(buildIngredientsList(candyElement));
        candy.setEnergy(getElementIntContent(candyElement, CandyXmlTag.ENERGY.getName()));
        candy.setValue(buildValue(candyElement));
    }

    private Value buildValue(Element candyElement) {
        NodeList valueList = candyElement.getElementsByTagName(CandyXmlTag.VALUE.getName());
        Element valueElement = (Element) valueList.item(0);
        Value value = new Value();
        value.setCarbohydrates(getElementIntContent(valueElement, CandyXmlTag.CARBOHYDRATES.getName()));
        value.setFats(getElementIntContent(valueElement, CandyXmlTag.FATS.getName()));
        value.setProteins(getElementIntContent(valueElement, CandyXmlTag.PROTEINS.getName()));
        return value;
    }

    private List<Ingredient> buildIngredientsList(Element element) {
        NodeList nodeList = element.getElementsByTagName(CandyXmlTag.INGREDIENTS.getName());
        Element ingredientsNode = (Element) nodeList.item(0);
        NodeList ingredientNodeList = ingredientsNode.getElementsByTagName(CandyXmlTag.INGREDIENT.getName());
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientNodeList.getLength(); i++) {
            ingredients.add(buildIngredient(ingredientNodeList.item(i)));
        }
        return ingredients;
    }

    private Ingredient buildIngredient(Node node) {
        Ingredient ingredient = new Ingredient();
        Element element = (Element) node;
        String name = getElementTextContent(element, CandyXmlTag.INGREDIENT_NAME.getName());
        int weight = getElementIntContent(element, CandyXmlTag.WEIGHT.getName());
        ingredient.setName(name);
        ingredient.setWeight(weight);
        return ingredient;
    }

    private int getElementIntContent(Element element, String tagName) {
        String stringInt = getElementTextContent(element, tagName);
        return Integer.parseInt(stringInt);
    }


    private String getElementTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }

    private Production getElementProduction(Element element) throws ParseXMLException {
        String productionName = getElementTextContent(element, CandyXmlTag.PRODUCTION.getName());
        return Production.getProduction(productionName);
    }

    private ChocolateType getElementChocolateType(Element element) throws ParseXMLException {
        String typeName = getElementTextContent(element, CandyXmlTag.CHOCOLATE_TYPE.getName());
        return ChocolateType.getChocolateType(typeName);
    }

    private YearMonth getElementYearMonthContent(Element element, String tagName) {
        String yearMonthString = getElementTextContent(element, tagName);
        return YearMonth.parse(yearMonthString);
    }

    private boolean getElementBooleanContent(Element candyElement, String tagName) {
        String booleanString = getElementTextContent(candyElement, tagName);
        return Boolean.parseBoolean(booleanString);
    }
}
