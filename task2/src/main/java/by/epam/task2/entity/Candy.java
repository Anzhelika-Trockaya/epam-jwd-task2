package by.epam.task2.entity;

import by.epam.task2.parser.YearMonthAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"name", "production", "expirationDate", "ingredients", "energy", "value"}, name = "candy")
public abstract class Candy implements Serializable {
    private static final long serialVersionUID = -3277035596610997101L;
    private String vendorCode;
    private String name;

    private YearMonth expirationDate;
    private int energy;
    private Value value;

    private List<Ingredient> ingredients;
    private Production production;

    {
        ingredients = new ArrayList<>();
        value=new Value();
    }

    public Candy() {
    }

    public Candy(String vendorCode, String name, YearMonth expirationDate, int energy, Value value, List<Ingredient> ingredients, Production production) {
        this.vendorCode = vendorCode;
        this.name = name;
        this.expirationDate = expirationDate;
        this.energy = energy;
        this.value = value;
        this.ingredients = ingredients;
        this.production = production;
    }

    //todo @XmlID
    @XmlAttribute(name = "vendor-code")
    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    @XmlElement(name = "expiration-date")
    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    @XmlElement(name = "energy")
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @XmlElement(name = "value")
    public Value getValue() {
        return new Value(value.getProteins(), value.getFats(), value.getCarbohydrates());
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @XmlElementWrapper(name = "ingredients")
    @XmlElement(name = "ingredient")
    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients != null ? new ArrayList<>(ingredients) : new ArrayList<>();
    }

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Candy candy = (Candy) o;
        if (candy.expirationDate == null ? expirationDate != null : !(candy.expirationDate.equals(expirationDate))) {
            return false;
        }
        if (candy.ingredients == null ? ingredients != null : !(candy.ingredients.equals(ingredients))) {
            return false;
        }
        return energy == candy.energy &&
                vendorCode.equals(candy.vendorCode) &&
                name.equals(candy.name) &&
                value.equals(candy.value) &&
                production == candy.production;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + vendorCode.hashCode();
        result = prime * result + name.hashCode();
        result = prime * result + (expirationDate == null ? 0 : expirationDate.hashCode());
        result = prime * result + energy;
        result = prime * result + value.hashCode();
        result = prime * result + (ingredients == null ? 0 : ingredients.hashCode());
        result = prime * result + production.hashCode();
        return result;
    }

    @Override
    public String toString() {//fixme: or getString???
        return "vendorCode='" + vendorCode + '\'' +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                ", energy=" + energy +
                ", value=" + value +
                ", ingredients=" + ingredients +
                ", production=" + production;
    }
}
