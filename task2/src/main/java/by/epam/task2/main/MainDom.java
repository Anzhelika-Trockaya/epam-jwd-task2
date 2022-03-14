package by.epam.task2.main;

import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.builder.CandiesDomBuilder;
import by.epam.task2.entity.AbstractCandy;

import java.net.URL;
import java.util.Set;

public class MainDom {
    public static void main(String[] args) throws ParseXMLException {
        URL resource =  MainDom.class.getClassLoader().getResource("candies.xml");
        String filename = resource.toString().substring(6);
        CandiesDomBuilder domBuilder = new CandiesDomBuilder();
        domBuilder.buildSetCandies(filename);
        Set<AbstractCandy> candies= domBuilder.getCandies();
        for(AbstractCandy candy:candies){
            System.out.println(candy);
        }
    }
}
