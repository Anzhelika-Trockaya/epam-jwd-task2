package by.epam.task2.entity;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
public class Candies {
    Set<Candy> candies;

    public Set<Candy> getCandies() {
        return candies;
    }

    @XmlElementWrapper
    @XmlElements({
            @XmlElement(type = CaramelCandy.class, name = "caramel-candy"),
            @XmlElement(type = ChocolateCandy.class, name = "chocolate-candy")
    })
    public void setCandies(Set<Candy> candies) {
        this.candies = candies;
    }

    public void add(Candy product) {
        if (this.candies == null) {
            this.candies = new HashSet<>();
        }
        this.candies.add(product);

    }
}
