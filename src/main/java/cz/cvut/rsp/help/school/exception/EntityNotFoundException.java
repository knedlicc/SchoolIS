package cz.cvut.rsp.help.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends javax.persistence.EntityNotFoundException {

    public EntityNotFoundException(String className, String parameter, String value) {
        super(String.format("Entity of class [%s] with parameter [%s] and value [%s] was not found", className,
                parameter, value));
    }

    public EntityNotFoundException(Class<?> className, String parameter, Object value) {
        this(className.getSimpleName(), parameter, value.toString());
    }

    public EntityNotFoundException(Class<?> className) {
        super(className.getSimpleName());
    }

}
