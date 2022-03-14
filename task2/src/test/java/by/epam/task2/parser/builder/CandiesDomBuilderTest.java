package by.epam.task2.parser.builder;

import by.epam.task2.entity.AbstractCandy;
import by.epam.task2.entity.ChocolateCandy;
import by.epam.task2.exception.ParseXMLException;
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
    public void buildSetCandiesTest() throws ParseXMLException {
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
        for(AbstractCandy candy:candies){
            if(candy.getVendorCode().equals(vendorCode)){
                return candy;
            }
        }
        throw new ParseXMLException("Candy with vendorCode="+vendorCode+" does not exist.");
    }

    @Test
    public void testAttributesOrderFillingVendorCode() throws ParseXMLException {
        ChocolateCandy actualCandy = (ChocolateCandy) getCandyWithVendorCode("A-848586");
        String actualFilling = actualCandy.getFilling();
        String expectedFilling = "Hazelnut";
        assertEquals(actualFilling,expectedFilling);
    }

    @Test
    public void testAttributesOrderVendorCodeFilling() throws ParseXMLException {
        ChocolateCandy actualCandy = (ChocolateCandy) getCandyWithVendorCode("A-777814");
        String actualFilling = actualCandy.getFilling();
        String expectedFilling = "Peanut";
        assertEquals(actualFilling,expectedFilling);
    }

    @Test
    public void testAttributesVendorCodeOnly() throws ParseXMLException {
        ChocolateCandy actualCandy = (ChocolateCandy) getCandyWithVendorCode("A-1111111");
        String actualFilling = actualCandy.getFilling();
        assertNull(actualFilling);
    }

}
