package by.epam.task2.parser.builder;

import by.epam.task2.exception.ParseXMLException;

public class CandyBuilderFactory {
    private enum TypeParser{
        SAX, STAX, DOM
    }
    private CandyBuilderFactory(){}
    public static AbstractCandiesBuilder createCandyBuilder(String typeParser) throws ParseXMLException {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch(type){
            case DOM -> {
                return new CandiesDomBuilder();
            }
            case SAX -> {
                return new CandiesSaxBuilder();
            }
            case STAX -> {
                return new CandiesStaxBuilder();
            }
            default -> throw new ParseXMLException("Enum constant not present. "+type.getDeclaringClass()+" "+type.name());
        }

    }
}
