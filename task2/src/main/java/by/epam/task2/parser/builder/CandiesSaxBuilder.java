package by.epam.task2.parser.builder;

import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.CandyHandler;
import by.epam.task2.util.ResourcePathUtil;
import by.epam.task2.validator.XmlFileValidator;
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
        reader.setContentHandler(candyHandler);
    }

    public void buildSetCandies(String fileName) throws ParseXMLException {
        String schemaFileName = ResourcePathUtil.getResourcePath(AbstractCandiesBuilder.SCHEMA_RESOURCE_NAME);
        XmlFileValidator validator = XmlFileValidator.getInstance();
        if (validator.isCorrect(fileName, schemaFileName)) {
            try {
                reader.parse(fileName);
            } catch (IOException | SAXException exception) {
                LOGGER.error("Exception when build Set of candies", exception);
                throw new ParseXMLException(exception);
            }
            candies = candyHandler.getCandies();
            LOGGER.info("Set of Candies build. " + candies);
        } else {
            LOGGER.info("File '" + fileName + "' does not match schema '" + schemaFileName + "'");
            throw new ParseXMLException("File '" + fileName + "' does not match schema '" + schemaFileName + "'");
        }
    }
}
