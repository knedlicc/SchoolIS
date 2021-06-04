package cz.cvut.rsp.help.school.exception;

public class CannotRateBeforeTheEndOfContractException extends RuntimeException{

    public CannotRateBeforeTheEndOfContractException() {
        super();
    }

    public CannotRateBeforeTheEndOfContractException(String message) {
        super(message);
    }

    public CannotRateBeforeTheEndOfContractException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotRateBeforeTheEndOfContractException(Throwable cause) {
        super(cause);
    }

}
