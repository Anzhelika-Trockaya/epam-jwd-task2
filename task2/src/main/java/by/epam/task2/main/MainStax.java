package by.epam.task2.main;

import by.epam.task2.entity.AbstractCandy;
import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.builder.CandiesStaxBuilder;

import java.net.URL;
import java.util.Set;

public class MainStax {
    public static void main(String[] args) throws ParseXMLException {
        URL resource =  MainSax.class.getClassLoader().getResource("candies.xml");
        String filename = resource.toString().substring(6);
        CandiesStaxBuilder staxBuilder= new CandiesStaxBuilder();
        staxBuilder.buildSetCandies(filename);
        Set<AbstractCandy> candies= staxBuilder.getCandies();
        for(AbstractCandy candy:candies){
            System.out.println(candy);
        }
    }
}
