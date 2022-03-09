package by.epam.task2.builder.handler;

import by.epam.task2.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.YearMonth;
import java.util.*;

public class CandyHandler extends DefaultHandler {
    private static final String ELEMENT_CHOCOLATE_CANDY = CandyXmlTag.CHOCOLATE_CANDY.getName();
    private static final String ELEMENT_CARAMEL_CANDY = CandyXmlTag.CARAMEL_CANDY.getName();
    private static final String ELEMENT_INGREDIENTS = CandyXmlTag.INGREDIENTS.getName();
    private static final String ELEMENT_INGREDIENT = CandyXmlTag.INGREDIENT.getName();
    private final Set<Candy> candies;
    private List<Ingredient> currentIngredients;
    private Candy currentCandy;
    private Ingredient currentIngredient;
    private ChocolateCandy currentChocolateCandy;
    private CaramelCandy currentCaramelCandy;
    private CandyXmlTag currentXmlTag;
    private final EnumSet<CandyXmlTag> withText;

    public CandyHandler() {
        candies = new HashSet<>();
        withText = EnumSet.range(CandyXmlTag.CANDY_NAME, CandyXmlTag.ENERGY);
    }

    public Set<Candy> getCandies() {
        return candies;
    }

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
            currentCandy = currentChocolateCandy;//fixme warning
        } else if (ELEMENT_CARAMEL_CANDY.equals(qName)) {
            currentCandy = new CaramelCandy();
            currentCandy.setVendorCode(attrs.getValue(0));
        } else if (ELEMENT_INGREDIENTS.equals(qName)) {
            currentIngredients = new ArrayList<>();
        } else if (ELEMENT_INGREDIENT.equals(qName)) {
            currentIngredient = new Ingredient();
        } else {
            CandyXmlTag temp = CandyXmlTag.getCandyXmlTag(qName);
            if (withText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

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

    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case CANDY_NAME -> currentCandy.setName(data);
                case CHOCOLATE_TYPE -> {
                    currentChocolateCandy = (ChocolateCandy) currentCandy;
                    currentChocolateCandy.setChocolateType(ChocolateType.getChocolateType(data));
                    currentCandy = currentChocolateCandy;//todo лишняя строка?
                }
                case FLAVOR -> {
                    currentCaramelCandy = (CaramelCandy) currentCandy;
                    currentCaramelCandy.setFlavor(data);
                    currentCandy = currentCaramelCandy;//todo лишняя строка?
                }
                case LOLLIPOP -> {
                    currentCaramelCandy = (CaramelCandy) currentCandy;
                    currentCaramelCandy.setLollipop(Boolean.parseBoolean(data));
                    currentCandy = currentCaramelCandy;//todo лишняя строка?
                }
                case INGREDIENT_NAME -> currentIngredient.setName(data);
                case WEIGHT -> currentIngredient.setWeight(Integer.parseInt(data));
                case CARBOHYDRATES -> {
                    Value value = currentCandy.getValue();
                    value.setCarbohydrates(Integer.parseInt(data));
                    currentCandy.setValue(value);
                }
                case FATS -> {
                    Value value = currentCandy.getValue();
                    value.setFats(Integer.parseInt(data));
                    currentCandy.setValue(value);
                }
                case PROTEINS -> {
                    Value value = currentCandy.getValue();
                    value.setProteins(Integer.parseInt(data));
                    currentCandy.setValue(value);
                }
                case ENERGY -> currentCandy.setEnergy(Integer.parseInt(data));
                case PRODUCTION -> currentCandy.setProduction(Production.getProduction(data));
                case EXPIRATION_DATE -> currentCandy.setExpirationDate(YearMonth.parse(data));
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());//fixme wrap exception
            }
        }
        currentXmlTag = null;
    }
}
