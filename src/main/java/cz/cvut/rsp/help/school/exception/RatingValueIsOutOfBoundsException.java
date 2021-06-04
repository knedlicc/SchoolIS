package cz.cvut.rsp.help.school.exception;

public class RatingValueIsOutOfBoundsException extends RuntimeException{

    public RatingValueIsOutOfBoundsException() {
        super();
    }

    public RatingValueIsOutOfBoundsException(String message) {
        super(message);
    }

    public RatingValueIsOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RatingValueIsOutOfBoundsException(Throwable cause) {
        super(cause);
    }

}
