package by.epam.task2.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(name = "ingredient")
public class Ingredient implements Serializable {
    private static final long serialVersionUID = -9035250561575720291L;
    private String name;
    private int weight;

    public Ingredient() {
    }

    public Ingredient(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @XmlElement(name = "ingredient-name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Ingredient that = (Ingredient) o;
        return weight == that.weight &&
                name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + weight;
        return result;
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringBuilder builder = new StringBuilder(className);
        builder.append("{name='").append(name).
                append("', weight=").append(weight).
                append('}');
        return builder.toString();
    }
}
