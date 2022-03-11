package by.epam.task2.builder;

import by.epam.task2.entity.*;

import java.time.YearMonth;
import java.util.List;

public class ChocolateCandyBuilder {
    private ChocolateCandy candy;

    public ChocolateCandyBuilder(String vendorCode, String name) {
        candy = new ChocolateCandy();
        candy.setVendorCode(vendorCode);
        candy.setName(name);
    }

    public ChocolateCandyBuilder buildExpirationDate(YearMonth expirationDate) {
        candy.setExpirationDate(expirationDate);
        return this;
    }

    public ChocolateCandyBuilder buildEnergy(int energy) {
        candy.setEnergy(energy);
        return this;
    }

    public ChocolateCandyBuilder buildValue(Value value) {
        candy.setValue(value);
        return this;
    }

    public ChocolateCandyBuilder buildIngredients(List<Ingredient> ingredients) {
        candy.setIngredients(ingredients);
        return this;
    }

    public ChocolateCandyBuilder buildProduction(Production production) {
        candy.setProduction(production);
        return this;
    }

    public ChocolateCandyBuilder buildFilling(String filling) {
        candy.setFilling(filling);
        return this;
    }

    public ChocolateCandyBuilder buildChocolateType(ChocolateType chocolateType) {
        candy.setChocolateType(chocolateType);
        return this;
    }

    public ChocolateCandy getChocolateCandy() {
        return candy;
    }
}
