package by.epam.task2.builder;

import by.epam.task2.entity.CaramelCandy;
import by.epam.task2.entity.Ingredient;
import by.epam.task2.entity.Production;
import by.epam.task2.entity.Value;

import java.time.YearMonth;
import java.util.List;

public class CaramelCandyBuilder {
    private CaramelCandy candy;

    public CaramelCandyBuilder(String vendorCode, String name) {
        candy = new CaramelCandy();
        candy.setVendorCode(vendorCode);
        candy.setName(name);
    }

    public CaramelCandyBuilder buildExpirationDate(YearMonth expirationDate) {
        candy.setExpirationDate(expirationDate);
        return this;
    }

    public CaramelCandyBuilder buildEnergy(int energy) {
        candy.setEnergy(energy);
        return this;
    }

    public CaramelCandyBuilder buildValue(Value value) {
        candy.setValue(value);
        return this;
    }

    public CaramelCandyBuilder buildIngredients(List<Ingredient> ingredients) {
        candy.setIngredients(ingredients);
        return this;
    }

    public CaramelCandyBuilder buildProduction(Production production) {
        candy.setProduction(production);
        return this;
    }

    public CaramelCandyBuilder buildLollipop(boolean isLollipop) {
        candy.setLollipop(isLollipop);
        return this;
    }

    public CaramelCandyBuilder buildFlavor(String flavor) {
        candy.setFlavor(flavor);
        return this;
    }

    public CaramelCandy getCaramelCandy() {
        return candy;
    }
}
