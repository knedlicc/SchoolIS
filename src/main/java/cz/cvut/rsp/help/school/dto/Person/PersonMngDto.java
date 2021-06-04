package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.dto.BaseDto;
import cz.cvut.rsp.help.school.model.Person;

public class PersonMngDto extends BaseDto {

    private String role;
    private String email;
    private String username;
    private String firstName;
    private String lastName;


    public static PersonMngDto from(Person person) {
        final PersonMngDto personDto = new PersonMngDto();
        personDto.setFrom(person);
        return personDto;
    }

    public void setFrom(Person person) {
        super.setFrom(person);
        this.setEmail(person.getEmail());
        this.setUsername(person.getUsername());
        this.setFirstName(person.getFirstName());
        this.setLastName(person.getLastName());
        this.setRole(person.getRole().toString());
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
}
