package by.epam.task2.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "chocolate-candy")
@XmlRootElement
public class ChocolateCandy extends AbstractCandy {
    private static final long serialVersionUID = 7617929853743504379L;
    private ChocolateType chocolateType;
    private String filling;

    public ChocolateCandy() {
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
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className);
        builder.append('{').append(super.toString()).
                append(", chocolateType=").append(chocolateType).
                append(", filling='").append(filling).append('\'').
                append('}');
        return builder.toString();
    }
}
