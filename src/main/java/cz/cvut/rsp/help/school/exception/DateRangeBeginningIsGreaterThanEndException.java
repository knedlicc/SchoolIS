package cz.cvut.rsp.help.school.exception;

public class DateRangeBeginningIsGreaterThanEndException extends RuntimeException {

    public DateRangeBeginningIsGreaterThanEndException() {
        super();
    }

    public DateRangeBeginningIsGreaterThanEndException(String message) {
        super(message);
    }

    public DateRangeBeginningIsGreaterThanEndException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateRangeBeginningIsGreaterThanEndException(Throwable cause) {
        super(cause);
    }

}
