package cz.cvut.rsp.help.school.exception;

public class CannotSelectDateFromThePastException extends RuntimeException {

    public CannotSelectDateFromThePastException() {
        super();
    }

    public CannotSelectDateFromThePastException(String message) {
        super(message);
    }

    public CannotSelectDateFromThePastException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotSelectDateFromThePastException(Throwable cause) {
        super(cause);
    }

}
