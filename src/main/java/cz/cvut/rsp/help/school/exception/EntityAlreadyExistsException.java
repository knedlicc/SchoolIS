package cz.cvut.rsp.help.school.exception;

import javax.persistence.EntityExistsException;


public class EntityAlreadyExistsException extends EntityExistsException {

    public EntityAlreadyExistsException(String className) {
        super(String.format("Entity of class [%s] already exists", className));
    }
}
