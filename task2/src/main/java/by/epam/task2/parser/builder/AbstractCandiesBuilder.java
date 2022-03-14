package by.epam.task2.parser.builder;

import by.epam.task2.entity.AbstractCandy;
import by.epam.task2.exception.ParseXMLException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCandiesBuilder {
    protected Set<AbstractCandy> candies = new HashSet<>();

    public Set<AbstractCandy> getCandies() {
        return candies;
    }

    public abstract void buildSetCandies(String fileName) throws ParseXMLException;
}
