package by.epam.task2.builder.impl;

import by.epam.task2.builder.CandiesBuilder;
import by.epam.task2.builder.handler.CandyHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class CandiesSaxBuilder extends CandiesBuilder {
    private final CandyHandler candyHandler = new CandyHandler();
    private XMLReader reader;

    public CandiesSaxBuilder() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try{
            SAXParser saxParser = factory.newSAXParser();
            reader= saxParser.getXMLReader();
        }catch(ParserConfigurationException| SAXException exception){
            //fixme log? exception?
        }
        //fixme reader.setErrorHandler(new CandyErrorHandler());
        reader.setContentHandler(candyHandler);
    }

    public void buildSetCandies(String fileName) {
        try {
            reader.parse(fileName);
        } catch (IOException | SAXException e) {
            e.printStackTrace(); //fixme log
        }
        candies = candyHandler.getCandies();
    }
}
