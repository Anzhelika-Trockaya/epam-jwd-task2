package by.epam.task2.util;

import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.validator.XmlFileValidator;

import java.net.URL;

public class ResourcePathUtil {
    public static String getResourcePath(String simpleFileName) throws ParseXMLException {
        final int pathStartPosition = 6;
        ClassLoader loader = XmlFileValidator.class.getClassLoader();
        URL resource = loader.getResource(simpleFileName);
        if (resource == null) {
            throw new ParseXMLException("Resource " + simpleFileName + " is not found");
        }
        return resource.toString().substring(pathStartPosition);
    }
}
