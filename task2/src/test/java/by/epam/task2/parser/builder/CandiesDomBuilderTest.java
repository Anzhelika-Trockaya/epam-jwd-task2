package by.epam.task2.parser.builder;

import by.epam.task2.entity.AbstractCandy;
import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.parser.builder.dataProvider.StaticDataProvider;
import by.epam.task2.util.ResourcePathUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

public class CandiesDomBuilderTest {
    public static final String SIMPLE_FILE_NAME = "candiesTest.xml";

    Set<AbstractCandy> candies;

    @Test
    @BeforeClass
    public void testBuildSetCandies() throws ParseXMLException {
        String fileName;
        try {
            fileName = ResourcePathUtil.getResourcePath(SIMPLE_FILE_NAME);
        } catch (ParseXMLException exception) {
            fail("Test failed. " + exception.getMessage());
            return;
        }
        CandiesDomBuilder domBuilder = new CandiesDomBuilder();
        domBuilder.buildSetCandies(fileName);
        candies = domBuilder.getCandies();
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

}
