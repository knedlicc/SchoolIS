package cz.cvut.rsp.help.school.exception;

public class ScheduleMeetingException extends RuntimeException {

    public ScheduleMeetingException() {
        super();
    }

    public ScheduleMeetingException(String message) {
        super(message);
    }

    public ScheduleMeetingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleMeetingException(Throwable cause) {
        super(cause);
    }

}
