package by.epam.task2.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.YearMonth;
import java.util.List;

@XmlType(name = "caramel-candy")
@XmlRootElement
public class CaramelCandy extends Candy {
    private static final long serialVersionUID = 3796736371273322548L;
    private boolean isLollipop;
    private String flavor;

    public CaramelCandy() {
    }

    public CaramelCandy(String vendorCode, String name, YearMonth expirationDate, int energy, Value value, List<Ingredient> ingredients, Production production, boolean isLollipop, String flavor) {
        super(vendorCode, name, expirationDate, energy, value, ingredients, production);
        this.isLollipop = isLollipop;
        this.flavor = flavor;
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
        return this.getClass().getSimpleName() +
                "{" + super.toString() +
                ", isLollipop=" + isLollipop +
                ", flavor='" + flavor + '\'' +
                '}';
    }
}
