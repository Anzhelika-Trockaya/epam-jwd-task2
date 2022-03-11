package by.epam.task2.validator;

import by.epam.task2.parser.CandyErrorHandler;
import by.epam.task2.exception.ParseXMLException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BaseValidatorMain {
    public static void main(String[] args) {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String fileSimpleName = "candies.xml";
        String schemaSimpleName = "candies.xsd";
        try {
            String fileName = getResourcePath(fileSimpleName);
            String schemaName = getResourcePath(schemaSimpleName);
            SchemaFactory factory = SchemaFactory.newInstance(language);
            File schemaLocation = new File(schemaName);
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileName);
            validator.setErrorHandler(new CandyErrorHandler());
            validator.validate(source);
        } catch (SAXException | IOException | ParseXMLException e) {
            System.err.println(fileSimpleName + " is not correct or valid: " + e.getMessage());
        }
    }

    private static String getResourcePath(String simpleFileName) throws ParseXMLException {
        final int pathStartPosition = 6;
        ClassLoader loader = BaseValidatorMain.class.getClassLoader();
        URL resource = loader.getResource(simpleFileName);
        if (resource == null) {
            throw new ParseXMLException("Resource " + simpleFileName + " is not found");
        }
        return resource.toString().substring(pathStartPosition);
    }
}
