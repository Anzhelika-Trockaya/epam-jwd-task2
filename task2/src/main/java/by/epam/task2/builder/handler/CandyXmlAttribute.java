package by.epam.task2.builder.handler;

public enum CandyXmlAttribute {
    VENDOR_CODE("vendor-code"),
    FILLING("filling");
    private String name;

    CandyXmlAttribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
