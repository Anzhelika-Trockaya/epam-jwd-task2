package by.epam.task2.validator;

import by.epam.task2.exception.ParseXMLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlFileValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static XmlFileValidator instance;

    private XmlFileValidator() {
    }

    public static XmlFileValidator getInstance() {
        if (instance == null) {
            instance = new XmlFileValidator();
        }
        return instance;
    }

    public boolean isCorrect(String xmlFileName, String schemaFileName) throws ParseXMLException {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        try {
            SchemaFactory factory = SchemaFactory.newInstance(language);
            File schemaLocation = new File(schemaFileName);
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlFileName);
            validator.validate(source);
            LOGGER.info("File '" + xmlFileName + "' matches schema '" + schemaFileName + "'");
            return true;
        } catch (SAXException saxException) {
            LOGGER.info("File '" + xmlFileName + "' does not match schema '" + schemaFileName + "'");
            return false;
        } catch (IOException ioException) {
            LOGGER.warn("Exception when validate file. File:'" + xmlFileName + "' Schema:'" + schemaFileName + "'");
            throw new ParseXMLException(ioException);
        }
    }

}
