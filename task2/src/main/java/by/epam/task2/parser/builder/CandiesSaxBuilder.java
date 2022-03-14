package by.epam.task2.parser.builder;

import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.CandyErrorHandler;
import by.epam.task2.parser.CandyHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class CandiesSaxBuilder extends AbstractCandiesBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CandyHandler candyHandler = new CandyHandler();
    private final XMLReader reader;

    public CandiesSaxBuilder() throws ParseXMLException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException exception) {
            LOGGER.error("CandiesSaxBuilder not created", exception);
            throw new ParseXMLException(exception);
        }
        //reader.setErrorHandler(new CandyErrorHandler());
        reader.setContentHandler(candyHandler);
    }

    public void buildSetCandies(String fileName) throws ParseXMLException {
        try {
            reader.parse(fileName);
        } catch (IOException | SAXException exception) {
            LOGGER.error("Exception when build Set of candies", exception);
            throw new ParseXMLException(exception);
        }
        candies = candyHandler.getCandies();
        LOGGER.info("Set of Candies build. " + candies);
    }
}
