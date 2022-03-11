package by.epam.task2.jaxb;

import by.epam.task2.entity.Candies;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CandyXMLConverter {
    private static CandyXMLConverter instance = new CandyXMLConverter();

    private CandyXMLConverter() {
    }

    public static CandyXMLConverter getInstance() {
        return instance;
    }

    public String marshalCandy(Candies candies) throws JAXBException, IOException{
        ByteArrayOutputStream outputStream;
        JAXBContext context;
        Marshaller marshaller;

        outputStream = new ByteArrayOutputStream();

            context = JAXBContext.newInstance(Candies.class);
        marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(candies, outputStream);
        outputStream.close();

        return outputStream.toString();
    }

    public Candies unmarshalCandy(String candiesXMLString) throws JAXBException {
        ByteArrayInputStream inputStream;
        JAXBContext context;
        Unmarshaller unmarshaller;

        inputStream = new ByteArrayInputStream(candiesXMLString.getBytes());
        context = JAXBContext.newInstance(Candies.class);
        unmarshaller = context.createUnmarshaller();

        return (Candies) unmarshaller.unmarshal(inputStream);
    }
}
