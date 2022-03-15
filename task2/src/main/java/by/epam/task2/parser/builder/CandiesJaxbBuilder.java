package by.epam.task2.parser.builder;

import by.epam.task2.entity.Candies;
import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.util.ResourcePathUtil;
import by.epam.task2.validator.XmlFileValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CandiesJaxbBuilder extends AbstractCandiesBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Unmarshaller unmarshaller;

    public CandiesJaxbBuilder() throws ParseXMLException {
        try {
            JAXBContext context = JAXBContext.newInstance(Candies.class);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException jaxbException) {
            LOGGER.error("CandiesJaxbBuilder not created. ", jaxbException);
            throw new ParseXMLException(jaxbException);
        }
    }

    @Override
    public void buildSetCandies(String fileName) throws ParseXMLException {
        String schemaFileName = ResourcePathUtil.getResourcePath(AbstractCandiesBuilder.SCHEMA_RESOURCE_NAME);
        XmlFileValidator validator = XmlFileValidator.getInstance();
        if (validator.isCorrect(fileName, schemaFileName)) {
            try {
                InputStream fileInputStream = new FileInputStream(fileName);
                Candies candiesObject = (Candies) unmarshaller.unmarshal(fileInputStream);
                candies = candiesObject.getCandies();
                LOGGER.info("Set of candies is build. " + candies);
            } catch (FileNotFoundException | JAXBException exception) {
                LOGGER.error("Exception when build Set of candies", exception);
                throw new ParseXMLException(exception);
            }
        } else {
            LOGGER.info("File '" + fileName + "' does not match schema '" + schemaFileName + "'");
            throw new ParseXMLException("File '" + fileName + "' does not match schema '" + schemaFileName + "'");
        }
    }
}
