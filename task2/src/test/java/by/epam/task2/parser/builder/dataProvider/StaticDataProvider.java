package by.epam.task2.parser.builder.dataProvider;

import by.epam.task2.entity.*;
import org.testng.annotations.DataProvider;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class StaticDataProvider {
    @DataProvider(name = "candies-data")
    public static Object[][] createCandiesData() {
        Object[][] data = new Object[5][1];
        data[0] = new Object[]{buildFirstCandy()};
        data[1] = new Object[]{buildSecondCandy()};
        data[2] = new Object[]{buildThirdCandy()};
        data[3] = new Object[]{buildForthCandy()};
        data[4] = new Object[]{buildFifthCandy()};
        return data;
    }

    private static ChocolateCandy buildFirstCandy() {
        ChocolateCandy.Builder builder = new ChocolateCandy.Builder("A-1111111", "Berezka");
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient water = new Ingredient("Water", 5);
        Ingredient cacao = new Ingredient("Cacao", 17);
        ingredients.add(water);
        ingredients.add(cacao);
        Value value = new Value(41, 12, 87);
        return builder.buildExpirationDate(YearMonth.of(2023, 3)).
                buildIngredients(ingredients).
                buildEnergy(1300).
                buildValue(value).
                buildProduction(Production.SPARTAK).
                buildChocolateType(ChocolateType.DARK).buildFilling(null).getCandy();
    }

    private static ChocolateCandy buildSecondCandy() {
        ChocolateCandy.Builder builder = new ChocolateCandy.Builder("A-848586", "Teddy bear in the meadow");
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient water = new Ingredient("Water", 10);
        Ingredient cacao = new Ingredient("Cacao", 25);
        ingredients.add(cacao);
        ingredients.add(water);
        Value value = new Value(325, 24, 550);
        return builder.buildExpirationDate(YearMonth.of(2023, 5)).
                buildIngredients(ingredients).
                buildEnergy(760).
                buildValue(value).
                buildProduction(Production.KOMMUNARKA).
                buildChocolateType(ChocolateType.MILK).buildFilling("Hazelnut").getCandy();
    }

    private static ChocolateCandy buildThirdCandy() {
        ChocolateCandy.Builder builder = new ChocolateCandy.Builder("A-777814", "Choconut");
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient water = new Ingredient("Water", 5);
        Ingredient cacao = new Ingredient("Cacao", 23);
        Ingredient peanut = new Ingredient("Peanut", 45);
        ingredients.add(cacao);
        ingredients.add(water);
        ingredients.add(peanut);
        Value value = new Value(12, 65, 642);
        return builder.buildExpirationDate(YearMonth.of(2023, 2)).
                buildIngredients(ingredients).
                buildEnergy(754).
                buildValue(value).
                buildProduction(Production.PETER_RONNEN).
                buildChocolateType(ChocolateType.DARK).buildFilling("Peanut").getCandy();
    }

    private static CaramelCandy buildForthCandy() {
        CaramelCandy.Builder builder = new CaramelCandy.Builder("A-123456", "Duchesse");
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient water = new Ingredient("Water", 25);
        Ingredient sugar = new Ingredient("Sugar", 14);
        ingredients.add(sugar);
        ingredients.add(water);
        Value value = new Value(14, 52, 147);
        return builder.buildExpirationDate(YearMonth.of(2022, 9)).
                buildIngredients(ingredients).
                buildEnergy(1452).
                buildValue(value).
                buildProduction(Production.KOMMUNARKA).
                buildFlavor("Duchesse").buildLollipop(false).getCandy();
    }

    private static CaramelCandy buildFifthCandy() {
        CaramelCandy.Builder builder = new CaramelCandy.Builder("A-353535", "Konti");
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient water = new Ingredient("Water", 14);
        Ingredient sugar = new Ingredient("Sugar", 47);
        Ingredient starchMolasses = new Ingredient("Starch molasses", 75);
        ingredients.add(water);
        ingredients.add(sugar);
        ingredients.add(starchMolasses);
        Value value = new Value(0, 14, 36);
        return builder.buildExpirationDate(YearMonth.of(2023, 10)).
                buildIngredients(ingredients).
                buildEnergy(380).
                buildValue(value).
                buildProduction(Production.KONTI).
                buildFlavor("Caramel").buildLollipop(true).getCandy();
    }
}
