package cz.cvut.rsp.help.school.exception;

public class RateException extends RuntimeException {

    public RateException() {
        super();
    }

    public RateException(String message) {
        super(message);
    }

    public RateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RateException(Throwable cause) {
        super(cause);
    }

}
