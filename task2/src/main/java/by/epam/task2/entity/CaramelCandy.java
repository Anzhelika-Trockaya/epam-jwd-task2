package by.epam.task2.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.YearMonth;
import java.util.List;

@XmlType(name = "caramel-candy")
@XmlRootElement
public class CaramelCandy extends AbstractCandy {
    private static final long serialVersionUID = 3796736371273322548L;
    private boolean isLollipop;
    private String flavor;

    public CaramelCandy() {
    }

    @XmlElement(name = "lollipop")
    public boolean isLollipop() {
        return isLollipop;
    }

    public void setLollipop(boolean lollipop) {
        isLollipop = lollipop;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        CaramelCandy that = (CaramelCandy) o;
        return isLollipop == that.isLollipop &&
                flavor == null ? that.flavor == null : flavor.equals(that.flavor);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = result * prime + (isLollipop ? 1 : 0);
        result = result * prime + (flavor == null ? 0 : flavor.hashCode());
        return result;
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className);
        builder.append('{').append(super.toString()).
                append(", isLollipop=").append(isLollipop).
                append(", flavor='").append(flavor).append('\'').
                append('}');
        return builder.toString();
    }

    public static class Builder {
        private final CaramelCandy candy;

        public Builder(String vendorCode, String name) {
            candy = new CaramelCandy();
            candy.setVendorCode(vendorCode);
            candy.setName(name);
        }

        public Builder buildExpirationDate(YearMonth expirationDate) {
            candy.setExpirationDate(expirationDate);
            return this;
        }

        public Builder buildEnergy(int energy) {
            candy.setEnergy(energy);
            return this;
        }

        public Builder buildValue(Value value) {
            candy.setValue(value);
            return this;
        }

        public Builder buildIngredients(List<Ingredient> ingredients) {
            candy.setIngredients(ingredients);
            return this;
        }

        public Builder buildProduction(Production production) {
            candy.setProduction(production);
            return this;
        }

        public Builder buildLollipop(boolean isLollipop) {
            candy.setLollipop(isLollipop);
            return this;
        }

        public Builder buildFlavor(String flavor) {
            candy.setFlavor(flavor);
            return this;
        }

        public CaramelCandy getCandy() {
            return candy;
        }
    }
}
