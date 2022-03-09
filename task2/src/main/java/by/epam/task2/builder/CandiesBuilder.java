package by.epam.task2.builder;

import by.epam.task2.entity.Candy;

import java.util.HashSet;
import java.util.Set;

public abstract class CandiesBuilder {
    protected Set<Candy> candies = new HashSet<>();
    public Set<Candy> getCandies() {
        return candies;
    }
    public abstract void buildSetCandies(String fileName);
}
