package by.epam.task2.entity;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
public class Candies {
    private Set<AbstractCandy> candies;

    public Candies() {
        this.candies = new HashSet<>();
    }

    public Set<AbstractCandy> getCandies() {
        return candies;
    }

    @XmlElements({
            @XmlElement(type = CaramelCandy.class, name = "caramel-candy"),
            @XmlElement(type = ChocolateCandy.class, name = "chocolate-candy")
    })
    public void setCandies(Set<AbstractCandy> candies) {
        this.candies = candies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Candies that = (Candies) o;
        return this.candies != null ? this.candies.equals(that.candies) : that.candies == null;
    }

    @Override
    public int hashCode() {
        return candies != null ? candies.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getSimpleName());
        stringBuilder.append("{candies=").append(candies).append('}');
        return stringBuilder.toString();
    }
}
