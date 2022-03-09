package by.epam.task2.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="chocolate-type")
@XmlEnum
public enum ChocolateType {
    @XmlEnumValue("White")
    WHITE("White"),
    @XmlEnumValue("Milk")
    MILK("Milk"),
    @XmlEnumValue("Dark")
    DARK("Dark");
    private String name;

    ChocolateType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ChocolateType getChocolateType(String name) {
        for (ChocolateType chocolateType : ChocolateType.values()) {
            if(chocolateType.getName().equals(name)){
                return chocolateType;
            }
        }
        return null;//fixme: throw new XMLParserException();
    }
}
