package by.epam.task2.parser.builder;

import by.epam.task2.exception.ParseXMLException;
import by.epam.task2.util.ResourcePathUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class CandiesJaxbBuilderTest extends AbstractCandiesBuilderTest {

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
        builder = new CandiesJaxbBuilder();
        builder.buildSetCandies(fileName);
        candies = builder.getCandies();
    }
}
