package by.epam.task2.builder.impl;

import by.epam.task2.builder.CandiesBuilder;
import by.epam.task2.entity.*;
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

public class CandiesDomBuilder extends CandiesBuilder {
    private DocumentBuilder docBuilder;//fixme final?

    public CandiesDomBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            //todo log
        }
    }

    @Override
    public void buildSetCandies(String fileName) {
        Document doc;
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList caramelCandiesList = root.getElementsByTagName("caramel-candy");
            NodeList chocolateCandiesList = root.getElementsByTagName("chocolate-candy");
            for (int i = 0; i < caramelCandiesList.getLength(); i++) {
                Element candyElement = (Element) caramelCandiesList.item(i);
                Candy candy = buildCaramelCandy(candyElement);
                candies.add(candy);
            }
            for (int i = 0; i < chocolateCandiesList.getLength(); i++) {
                Element candyElement = (Element) chocolateCandiesList.item(i);
                Candy candy = buildChocolateCandy(candyElement);
                candies.add(candy);
            }
        } catch (IOException | SAXException exception) {
            //todo log
        }
    }

    private Candy buildChocolateCandy(Element candyElement) {
        ChocolateCandy candy = new ChocolateCandy();
        buildCandy(candy, candyElement);
        if(candyElement.hasAttribute("filling")){
            candy.setFilling(candyElement.getAttribute( "filling"));
        }
        candy.setChocolateType(getElementChocolateType(candyElement));
        return candy;
    }

    private Candy buildCaramelCandy(Element candyElement) {
        CaramelCandy candy = new CaramelCandy();
        buildCandy(candy, candyElement);
        candy.setFlavor(getElementTextContent(candyElement, "flavor"));
        candy.setLollipop(getElementBooleanContent(candyElement, "lollipop"));
        return candy;
    }


    private void buildCandy(Candy candy, Element candyElement) {
        candy.setVendorCode(candyElement.getAttribute("vendor-code"));
        candy.setName(getElementTextContent(candyElement, "name"));
        candy.setProduction(getElementProductionValue(candyElement));
        candy.setExpirationDate(getElementYearMonthContent(candyElement, "expiration-date"));
        candy.setIngredients(getIngredientsList(candyElement));
        candy.setEnergy(getElementIntContent(candyElement, "energy"));
        candy.setValue(buildValue(candyElement));
    }

    private Value buildValue(Element candyElement) {
        NodeList valueList = candyElement.getElementsByTagName("value");
        Element valueElement = (Element) valueList.item(0);
        Value value = new Value();
        value.setCarbohydrates(getElementIntContent(valueElement, "carbohydrates"));
        value.setFats(getElementIntContent(valueElement, "fats"));
        value.setProteins(getElementIntContent(valueElement, "proteins"));
        return value;
    }

    private List<Ingredient> getIngredientsList(Element element) {
        NodeList nodeList = element.getElementsByTagName("ingredients");
        Element ingredientsNode = (Element)nodeList.item(0);
        NodeList ingredientNodeList = ingredientsNode.getElementsByTagName("ingredient");
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientNodeList.getLength(); i++) {
            ingredients.add(buildIngredient(ingredientNodeList.item(i)));
        }
        return ingredients;
    }

    private Ingredient buildIngredient(Node node) {
        Ingredient ingredient = new Ingredient();
        Element element = (Element) node;
        String name = getElementTextContent(element, "ingredient-name");
        int weight = getElementIntContent(element, "weight");
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

    private Production getElementProductionValue(Element element) {//todo T method
        String productionName = getElementTextContent(element, "production");
        return Production.getProduction(productionName);
    }

    private ChocolateType getElementChocolateType(Element element) {
        String typeName = getElementTextContent(element, "chocolate-type");
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
