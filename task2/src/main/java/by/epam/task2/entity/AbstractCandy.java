package by.epam.task2.entity;

import by.epam.task2.parser.YearMonthAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "candy")
public abstract class AbstractCandy implements Serializable {
    private static final long serialVersionUID = -3277035596610997101L;
    private String vendorCode;
    private String name;
    private YearMonth expirationDate;
    private int energy;
    private final Value value;
    private List<Ingredient> ingredients;
    private Production production;

    public AbstractCandy() {
        ingredients = new ArrayList<>();
        value = new Value();
    }

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
        this.value.setCarbohydrates(value.getCarbohydrates());
        this.value.setProteins(value.getProteins());
        this.value.setFats(value.getFats());
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
        AbstractCandy candy = (AbstractCandy) o;
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
        result = prime * result + (production == null ? 0 : production.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("vendorCode='").append(vendorCode).append('\'').
                append(", name='").append(name).append('\'').
                append(", expirationDate=").append(expirationDate).
                append(", energy=").append(energy).
                append(", value=").append(value).
                append(", ingredients=").append(ingredients).
                append(", production=").append(production);
        return builder.toString();
    }
}
