package cz.cvut.rsp.help.school.exception;

public class CharacterLimitExceededException extends RuntimeException {

    public CharacterLimitExceededException(String message) {
        super(message);
    }
}

