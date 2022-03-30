package by.epam.task2.parser.builder;

import by.epam.task2.exception.ParseXMLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CandyBuilderFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    private enum TypeParser {
        SAX,
        STAX,
        DOM,
        JAXB
    }

    private CandyBuilderFactory() {
    }

    public static AbstractCandiesBuilder createCandyBuilder(String typeParser) throws ParseXMLException {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM : {
                LOGGER.info("Created new CandiesDomBuilder");
                return new CandiesDomBuilder();
            }
            case SAX : {
                LOGGER.info("Created new CandiesSaxBuilder");
                return new CandiesSaxBuilder();
            }
            case STAX : {
                LOGGER.info("Created new CandiesStaxBuilder");
                return new CandiesStaxBuilder();
            }
            case JAXB : {
                LOGGER.info("Created new CandiesJaxbBuilder");
                return new CandiesJaxbBuilder();
            }
            default : {
                LOGGER.error("Enum constant not present. " + type.getDeclaringClass() + " " + type.name());
                throw new ParseXMLException("Enum constant not present. " + type.getDeclaringClass() + " " + type.name());
            }
        }
    }
}
