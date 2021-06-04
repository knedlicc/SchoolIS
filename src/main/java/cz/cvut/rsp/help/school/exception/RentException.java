package cz.cvut.rsp.help.school.exception;

public class RentException extends RuntimeException {

    public RentException() {
        super();
    }

    public RentException(String message) {
        super(message);
    }

    public RentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentException(Throwable cause) {
        super(cause);
    }

}
