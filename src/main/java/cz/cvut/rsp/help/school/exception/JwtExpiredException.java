package cz.cvut.rsp.help.school.exception;

public class JwtExpiredException extends RuntimeException {

    public JwtExpiredException() {
        super("Session time expired");
    }
}
