package by.epam.task2.parser.builder;

import by.epam.task2.entity.*;
import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.CandyXmlAttribute;
import by.epam.task2.parser.CandyXmlTag;
import by.epam.task2.util.ResourcePathUtil;
import by.epam.task2.validator.XmlFileValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CandiesStaxBuilder extends AbstractCandiesBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final XMLInputFactory inputFactory;

    public CandiesStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetCandies(String fileName) throws ParseXMLException {
        String schemaFileName = ResourcePathUtil.getResourcePath(AbstractCandiesBuilder.SCHEMA_RESOURCE_NAME);
        XmlFileValidator validator = XmlFileValidator.getInstance();
        if (validator.isCorrect(fileName, schemaFileName)) {
            XMLStreamReader reader;
            String name;
            try (FileInputStream inputStream = new FileInputStream(new File(fileName))) {
                reader = inputFactory.createXMLStreamReader(inputStream);
                while (reader.hasNext()) {
                    int type = reader.next();
                    if (type == XMLStreamConstants.START_ELEMENT) {
                        name = reader.getLocalName();
                        CandyXmlTag tag = CandyXmlTag.getCandyXmlTag(name);
                        if (CandyXmlTag.CARAMEL_CANDY == tag) {
                            AbstractCandy candy = buildCandy(new CaramelCandy(), reader);
                            candies.add(candy);
                        } else if (CandyXmlTag.CHOCOLATE_CANDY == tag) {
                            AbstractCandy candy = buildCandy(new ChocolateCandy(), reader);
                            candies.add(candy);
                        }
                    }
                }
            } catch (XMLStreamException | IOException | ParseXMLException exception) {
                LOGGER.error("Exception when build Set of candies", exception);
                throw new ParseXMLException(exception);
            }
            LOGGER.info("Set of candies is build. " + candies);
        } else {
            LOGGER.info("File '" + fileName + "' does not match schema '" + schemaFileName + "'");
            throw new ParseXMLException("File '" + fileName + "' does not match schema '" + schemaFileName + "'");
        }
    }

    private AbstractCandy buildCandy(AbstractCandy candy, XMLStreamReader reader) throws XMLStreamException, ParseXMLException {
        candy.setVendorCode(reader.getAttributeValue(null, CandyXmlAttribute.VENDOR_CODE.getName()));
        String fillingAttrValue = reader.getAttributeValue(null, CandyXmlAttribute.FILLING.getName());
        if (fillingAttrValue != null) {
            ChocolateCandy chocolateCandy = (ChocolateCandy) candy;
            chocolateCandy.setFilling(fillingAttrValue);
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    CandyXmlTag tag = CandyXmlTag.getCandyXmlTag(name);
                    switch (tag) {
                        case CANDY_NAME -> candy.setName(getXMLText(reader));
                        case PRODUCTION -> candy.setProduction(Production.getProduction(getXMLText(reader)));
                        case EXPIRATION_DATE -> candy.setExpirationDate(YearMonth.parse(getXMLText(reader)));
                        case ENERGY -> candy.setEnergy(Integer.parseInt(getXMLText(reader)));
                        case INGREDIENTS -> candy.setIngredients(getXMLIngredients(reader));
                        case VALUE -> candy.setValue(getXMLValue(reader));
                        case CHOCOLATE_TYPE -> {
                            ChocolateCandy chocolateCandy = (ChocolateCandy) candy;
                            chocolateCandy.setChocolateType(ChocolateType.getChocolateType(getXMLText(reader)));
                        }
                        case FLAVOR -> {
                            CaramelCandy caramelCandy = (CaramelCandy) candy;
                            caramelCandy.setFlavor(getXMLText(reader));
                        }
                        case LOLLIPOP -> {
                            CaramelCandy caramelCandy = (CaramelCandy) candy;
                            caramelCandy.setLollipop(Boolean.parseBoolean(getXMLText(reader)));
                        }
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    CandyXmlTag tag = CandyXmlTag.getCandyXmlTag(name);
                    if (CandyXmlTag.CARAMEL_CANDY == tag || CandyXmlTag.CHOCOLATE_CANDY == tag) {
                        return candy;
                    }
                }
            }
        }
        throw new ParseXMLException("End tag of " + candy.getClass().getSimpleName() + " is not found.");
    }

    private Value getXMLValue(XMLStreamReader reader) throws XMLStreamException, ParseXMLException {
        Value value = new Value();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    CandyXmlTag tag = CandyXmlTag.getCandyXmlTag(name);
                    switch (tag) {
                        case CARBOHYDRATES -> value.setCarbohydrates(Integer.parseInt(getXMLText(reader)));
                        case FATS -> value.setFats(Integer.parseInt(getXMLText(reader)));
                        case PROTEINS -> value.setProteins(Integer.parseInt(getXMLText(reader)));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (CandyXmlTag.VALUE == CandyXmlTag.getCandyXmlTag(name)) {
                        return value;
                    }
                }
            }
        }
        throw new XMLStreamException("End tag of Ingredient is not found");
    }

    private List<Ingredient> getXMLIngredients(XMLStreamReader reader) throws XMLStreamException, ParseXMLException {
        List<Ingredient> ingredients = new ArrayList<>();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    CandyXmlTag tag = CandyXmlTag.getCandyXmlTag(name);
                    if (CandyXmlTag.INGREDIENT == tag) {
                        Ingredient ingredient = getXMLIngredient(reader);
                        ingredients.add(ingredient);
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    CandyXmlTag tag = CandyXmlTag.getCandyXmlTag(name);
                    if (CandyXmlTag.INGREDIENTS == tag) {
                        return ingredients;
                    }
                }
            }
        }
        throw new XMLStreamException("End tag of Ingredients is not found");
    }

    private Ingredient getXMLIngredient(XMLStreamReader reader) throws XMLStreamException, ParseXMLException {
        Ingredient ingredient = new Ingredient();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    CandyXmlTag tag = CandyXmlTag.getCandyXmlTag(name);
                    switch (tag) {
                        case INGREDIENT_NAME -> ingredient.setName(getXMLText(reader));
                        case WEIGHT -> ingredient.setWeight(Integer.parseInt(getXMLText(reader)));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    CandyXmlTag tag = CandyXmlTag.getCandyXmlTag(name);
                    if (CandyXmlTag.INGREDIENT == tag) {
                        return ingredient;
                    }
                }
            }
        }
        throw new XMLStreamException("End tag of Ingredient is not found");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
