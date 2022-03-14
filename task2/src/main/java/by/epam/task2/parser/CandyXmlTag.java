package by.epam.task2.parser;

import by.epam.task2.exception.ParseXMLException;

public enum CandyXmlTag {
    CANDIES("candies"),
    CARAMEL_CANDY("caramel-candy"),
    CHOCOLATE_CANDY("chocolate-candy"),
    INGREDIENTS("ingredients"),
    INGREDIENT("ingredient"),
    VALUE("value"),
    CANDY_NAME("name"),
    CHOCOLATE_TYPE("chocolate-type"),
    FLAVOR("flavor"),
    LOLLIPOP("lollipop"),
    INGREDIENT_NAME("ingredient-name"),
    WEIGHT("weight"),
    CARBOHYDRATES("carbohydrates"),
    FATS("fats"),
    PROTEINS("proteins"),
    PRODUCTION("production"),
    EXPIRATION_DATE("expiration-date"),
    ENERGY("energy");
    private final String name;

    CandyXmlTag(String name) {
        this.name = name;
    }

    public static CandyXmlTag getCandyXmlTag(String name) throws ParseXMLException {
        for (CandyXmlTag tag : CandyXmlTag.values()) {
            if (name.equals(tag.getName())) {
                return tag;
            }
        }
        throw new ParseXMLException("Unknown tag <" + name + ">");
    }

    public String getName() {
        return name;
    }

}
