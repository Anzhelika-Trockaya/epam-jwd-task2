package by.epam.task2.parser.builder;

import by.epam.task2.entity.AbstractCandy;
import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.builder.dataProvider.StaticDataProvider;
import by.epam.task2.util.ResourcePathUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class CandiesSaxBuilderTest {
    public static final String CORRECT_RESOURCE_NAME = "candiesTest.xml";
    public static final String EMPTY_RESOURCE_NAME = "empty.xml";
    public static final String INCORRECT_RESOURCE_NAME = "incorrect.xml";
    CandiesSaxBuilder builder;
    Set<AbstractCandy> candies;

    @Test
    @BeforeClass
    public void testBuildSetCandies() throws ParseXMLException {
        String fileName;
        try {
            fileName = ResourcePathUtil.getResourcePath(CORRECT_RESOURCE_NAME);
        } catch (ParseXMLException exception) {
            fail("Test failed. " + exception.getMessage());
            return;
        }
        builder = new CandiesSaxBuilder();
        builder.buildSetCandies(fileName);
        candies = builder.getCandies();
        int expectedCandiesSize = 5;
        assertEquals(candies.size(), expectedCandiesSize);
    }

    public AbstractCandy getCandyWithVendorCode(String vendorCode) throws ParseXMLException {
        for (AbstractCandy candy : candies) {
            if (candy.getVendorCode().equals(vendorCode)) {
                return candy;
            }
        }
        throw new ParseXMLException("Candy with vendorCode=" + vendorCode + " does not exist.");
    }

    @Test(dataProvider = "candies-data", dataProviderClass = StaticDataProvider.class)
    public void testParsedData(AbstractCandy expectedCandy) throws ParseXMLException {
        String vendorCode = expectedCandy.getVendorCode();
        AbstractCandy actualCandy = getCandyWithVendorCode(vendorCode);
        assertEquals(expectedCandy, actualCandy);
    }

    @Test(expectedExceptions = ParseXMLException.class, expectedExceptionsMessageRegExp = "File '.*' does not match schema '.*'")
    public void testEmptyFile() throws ParseXMLException {
        String fileName;
        try {
            fileName = ResourcePathUtil.getResourcePath(EMPTY_RESOURCE_NAME);
        } catch (ParseXMLException exception) {
            fail("Test failed. " + exception.getMessage());
            return;
        }
        builder.buildSetCandies(fileName);
    }

    @Test(expectedExceptions = ParseXMLException.class, expectedExceptionsMessageRegExp = "File '.*' does not match schema '.*'")
    public void testIncorrectFile() throws ParseXMLException {
        String fileName;
        try {
            fileName = ResourcePathUtil.getResourcePath(INCORRECT_RESOURCE_NAME);
        } catch (ParseXMLException exception) {
            fail("Test failed. " + exception.getMessage());
            return;
        }
        builder.buildSetCandies(fileName);
    }

    @Test(expectedExceptions = ParseXMLException.class)
    public void testNonExistentFile() throws ParseXMLException {
        String fileName;
        try {
            File nonExistent = File.createTempFile("test", "txt");
            fileName = nonExistent.getAbsolutePath();
            if (!nonExistent.delete()) {
                fail("Test failed. File not deleted.");
                return;
            }
        } catch (IOException ioException) {
            fail("Test failed. " + ioException.getMessage());
            return;
        }
        builder.buildSetCandies(fileName);
    }
}
