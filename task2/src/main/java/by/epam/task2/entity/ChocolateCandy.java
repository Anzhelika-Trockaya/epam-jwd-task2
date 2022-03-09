package by.epam.task2.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.YearMonth;
import java.util.List;

@XmlType(name = "chocolate-candy")
@XmlRootElement
public class ChocolateCandy extends Candy {
    private static final long serialVersionUID = 7617929853743504379L;
    private ChocolateType chocolateType;
    private String filling;

    public ChocolateCandy() {
    }

    public ChocolateCandy(String vendorCode, String name, YearMonth expirationDate, int energy, Value value, List<Ingredient> ingredients, Production production, ChocolateType chocolateType, String filling) {
        super(vendorCode, name, expirationDate, energy, value, ingredients, production);
        this.chocolateType = chocolateType;
        this.filling = filling;
    }

    public ChocolateCandy(String vendorCode, String name, YearMonth expirationDate, int energy, Value value, List<Ingredient> ingredients, Production production, ChocolateType chocolateType) {
        super(vendorCode, name, expirationDate, energy, value, ingredients, production);
        this.chocolateType = chocolateType;
    }

    @XmlElement(name = "chocolate-type")
    public ChocolateType getChocolateType() {
        return chocolateType;
    }

    public void setChocolateType(ChocolateType chocolateType) {
        this.chocolateType = chocolateType;
    }

    @XmlAttribute
    public String getFilling() {
        return filling;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        ChocolateCandy that = (ChocolateCandy) o;
        return chocolateType == that.chocolateType &&
                filling == null ? that.filling == null : filling.equals(that.filling);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = result * prime + chocolateType.hashCode();
        result = result * prime + (filling == null ? 0 : filling.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{" + super.toString() +
                ", chocolateType=" + chocolateType +
                ", filling='" + filling + '\'' +
                '}';
    }
}
