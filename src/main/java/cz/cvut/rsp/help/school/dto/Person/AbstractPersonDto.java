package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.dto.BaseDto;
import cz.cvut.rsp.help.school.model.Person;

public abstract class AbstractPersonDto extends BaseDto {

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFrom(Person person) {
        super.setFrom(person);
        this.setEmail(person.getEmail());
        this.setUsername(person.getUsername());
        this.setFirstName(person.getFirstName());
        this.setLastName(person.getLastName());
    }

}
