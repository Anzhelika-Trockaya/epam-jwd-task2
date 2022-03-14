package by.epam.task2.entity;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(name = "value")
public class Value implements Serializable {
    private static final long serialVersionUID = -5759372411514854808L;
    private int proteins;
    private int fats;
    private int carbohydrates;

    public Value() {
    }

    public Value(int proteins, int fats, int carbohydrates) {
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Value value = (Value) o;
        return proteins == value.proteins &&
                fats == value.fats &&
                carbohydrates == value.carbohydrates;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + proteins;
        result = prime * result + fats;
        result = prime * result + carbohydrates;
        return result;
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className);
        builder.append("{proteins=").append(proteins).
                append(", fats=").append(fats).
                append(", carbohydrates=").append(carbohydrates).
                append('}');
        return builder.toString();
    }
}
