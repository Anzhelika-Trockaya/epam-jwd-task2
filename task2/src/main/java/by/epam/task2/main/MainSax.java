package by.epam.task2.main;

import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.builder.CandiesSaxBuilder;
import by.epam.task2.entity.AbstractCandy;
import by.epam.task2.util.ResourcePathUtil;
import by.epam.task2.validator.XmlFileValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Set;

public class MainSax {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws ParseXMLException {

        String filename = ResourcePathUtil.getResourcePath("candies.xml");
        String schemaFileName = ResourcePathUtil.getResourcePath("candies.xsd");
        XmlFileValidator validator = XmlFileValidator.getInstance();
        if(validator.isCorrect(filename, schemaFileName)) {
            CandiesSaxBuilder saxBuilder = new CandiesSaxBuilder();
            saxBuilder.buildSetCandies(filename);
            Set<AbstractCandy> candies = saxBuilder.getCandies();
            for (AbstractCandy candy : candies) {
                System.out.println(candy);
            }
        }
    }
}
