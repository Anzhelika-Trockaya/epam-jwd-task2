package by.epam.task2.util;

import by.epam.task2.exception.ParseXMLException;

import java.net.URL;

public class ResourcePathUtil {
    public static String getResourcePath(String resourceName) throws ParseXMLException {
        final int pathStartPosition = 6;
        ClassLoader loader = ResourcePathUtil.class.getClassLoader();
        URL resource = loader.getResource(resourceName);
        if (resource == null) {
            throw new ParseXMLException("Resource " + resourceName + " is not found");
        }
        return resource.toString().substring(pathStartPosition);
    }
}
