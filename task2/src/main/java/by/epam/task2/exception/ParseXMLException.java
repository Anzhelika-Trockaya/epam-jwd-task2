package by.epam.task2.exception;

public class ParseXMLException extends Exception {
    private static final long serialVersionUID = -1463022771765787099L;

    public ParseXMLException() {
        super();
    }

    public ParseXMLException(String message) {
        super(message);
    }

    public ParseXMLException(String message, Exception exception) {
        super(message, exception);
    }

    public ParseXMLException(Exception exception) {
        super(exception);
    }
}
