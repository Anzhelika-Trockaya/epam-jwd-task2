package by.epam.task2.parser;

public enum CandyXmlAttribute {
    VENDOR_CODE("vendor-code"),
    FILLING("filling");
    private final String name;

    CandyXmlAttribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
