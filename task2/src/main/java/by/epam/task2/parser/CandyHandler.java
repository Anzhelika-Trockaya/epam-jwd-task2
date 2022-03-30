package by.epam.task2.parser;

import by.epam.task2.entity.*;
import by.epam.task2.exception.ParseXMLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.YearMonth;
import java.util.*;

public class CandyHandler extends DefaultHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ELEMENT_CHOCOLATE_CANDY = CandyXmlTag.CHOCOLATE_CANDY.getName();
    private static final String ELEMENT_CARAMEL_CANDY = CandyXmlTag.CARAMEL_CANDY.getName();
    private static final String ELEMENT_INGREDIENTS = CandyXmlTag.INGREDIENTS.getName();
    private static final String ELEMENT_INGREDIENT = CandyXmlTag.INGREDIENT.getName();
    private final EnumSet<CandyXmlTag> withText;
    private final Set<AbstractCandy> candies;
    private List<Ingredient> currentIngredients;
    private AbstractCandy currentCandy;
    private Ingredient currentIngredient;
    private ChocolateCandy currentChocolateCandy;
    private CaramelCandy currentCaramelCandy;
    private CandyXmlTag currentXmlTag;

    public CandyHandler() {
        candies = new HashSet<>();
        withText = EnumSet.range(CandyXmlTag.CANDY_NAME, CandyXmlTag.ENERGY);
    }

    public Set<AbstractCandy> getCandies() {
        return candies;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (ELEMENT_CHOCOLATE_CANDY.equals(qName)) {
            currentChocolateCandy = new ChocolateCandy();
            String vendorCodeAttributeName = CandyXmlAttribute.VENDOR_CODE.getName();
            int vendorCodeIndex = attrs.getIndex(vendorCodeAttributeName);
            currentChocolateCandy.setVendorCode(attrs.getValue(vendorCodeIndex));
            if (attrs.getLength() == 2) {
                String fillingAttributeName = CandyXmlAttribute.FILLING.getName();
                int fillingIndex = attrs.getIndex(fillingAttributeName);
                currentChocolateCandy.setFilling(attrs.getValue(fillingIndex));
            }
            currentCandy = currentChocolateCandy;
        } else if (ELEMENT_CARAMEL_CANDY.equals(qName)) {
            currentCandy = new CaramelCandy();
            currentCandy.setVendorCode(attrs.getValue(0));
        } else if (ELEMENT_INGREDIENTS.equals(qName)) {
            currentIngredients = new ArrayList<>();
        } else if (ELEMENT_INGREDIENT.equals(qName)) {
            currentIngredient = new Ingredient();
        } else {
            try {
                CandyXmlTag temp = CandyXmlTag.getCandyXmlTag(qName);
                if (withText.contains(temp)) {
                    currentXmlTag = temp;
                }
            } catch (ParseXMLException exception) {
                LOGGER.warn("Unknown start element '" + qName + "'.", exception);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (ELEMENT_CHOCOLATE_CANDY.equals(qName)) {
            candies.add(currentChocolateCandy);
        } else if (ELEMENT_CARAMEL_CANDY.equals(qName)) {
            candies.add(currentCaramelCandy);
        } else if (ELEMENT_INGREDIENT.equals(qName)) {
            currentIngredients.add(currentIngredient);
        } else if (ELEMENT_INGREDIENTS.equals(qName)) {
            currentCandy.setIngredients(currentIngredients);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case CANDY_NAME:
                    currentCandy.setName(data);
                    break;
                case CHOCOLATE_TYPE: {
                    currentChocolateCandy = (ChocolateCandy) currentCandy;
                    try {
                        currentChocolateCandy.setChocolateType(ChocolateType.getChocolateType(data));
                    } catch (ParseXMLException exception) {
                        LOGGER.warn(exception);
                    }
                }
                break;
                case FLAVOR: {
                    currentCaramelCandy = (CaramelCandy) currentCandy;
                    currentCaramelCandy.setFlavor(data);
                }
                break;
                case LOLLIPOP: {
                    currentCaramelCandy = (CaramelCandy) currentCandy;
                    currentCaramelCandy.setLollipop(Boolean.parseBoolean(data));
                }
                break;
                case INGREDIENT_NAME:
                    currentIngredient.setName(data);
                    break;
                case WEIGHT:
                    currentIngredient.setWeight(Integer.parseInt(data));
                    break;
                case CARBOHYDRATES: {
                    Value value = currentCandy.getValue();
                    value.setCarbohydrates(Integer.parseInt(data));
                    currentCandy.setValue(value);
                }
                break;
                case FATS: {
                    Value value = currentCandy.getValue();
                    value.setFats(Integer.parseInt(data));
                    currentCandy.setValue(value);
                }
                break;
                case PROTEINS: {
                    Value value = currentCandy.getValue();
                    value.setProteins(Integer.parseInt(data));
                    currentCandy.setValue(value);
                }
                break;
                case ENERGY:
                    currentCandy.setEnergy(Integer.parseInt(data));
                    break;
                case PRODUCTION: {
                    try {
                        currentCandy.setProduction(Production.getProduction(data));
                    } catch (ParseXMLException exception) {
                        LOGGER.warn(exception);
                    }
                }
                break;
                case EXPIRATION_DATE:
                    currentCandy.setExpirationDate(YearMonth.parse(data));
                    break;
                default: {
                    LOGGER.error("Unknown tag " + currentXmlTag.name());
                    throw new EnumConstantNotPresentException(
                            currentXmlTag.getDeclaringClass(), currentXmlTag.name());
                }
            }
        }
        currentXmlTag = null;
    }
}
