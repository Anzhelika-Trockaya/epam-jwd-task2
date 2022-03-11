package by.epam.task2.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;

public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {
    public YearMonth unmarshal(String date) {
        return YearMonth.parse(date);
    }

    public String marshal(YearMonth date) {
        return date.toString();
    }
}
