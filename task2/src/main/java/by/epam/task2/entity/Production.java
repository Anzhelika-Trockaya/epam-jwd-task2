package by.epam.task2.entity;

import by.epam.task2.exception.ParseXMLException;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "production")
@XmlEnum
public enum Production {
    @XmlEnumValue("Kommunarka")
    KOMMUNARKA("Kommunarka"),
    @XmlEnumValue("Spartak")
    SPARTAK("Spartak"),
    @XmlEnumValue("Roshen")
    ROSHEN("Roshen"),
    @XmlEnumValue("Peter Ronnen")
    PETER_RONNEN("Peter Ronnen"),
    @XmlEnumValue("Konti")
    KONTI("Konti"),
    @XmlEnumValue("Rikki")
    RIKKI("Rikki"),
    @XmlEnumValue("Solyariki")
    SOLYARIKI("Solyariki"),
    @XmlEnumValue("Chupa Chups")
    CHUPA_CHUPS("Chupa Chups"),
    @XmlEnumValue("Snickers")
    SNICKERS("Snickers");
    private String name;

    Production(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Production getProduction(String name) throws ParseXMLException {
        for (Production production : Production.values()) {
            if(name.equals(production.getName())){
                return production;
            }
        }
        throw new ParseXMLException("Unknown production name '"+name+"'");
    }
}
