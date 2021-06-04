package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.dto.BaseDto;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;


public class AddUserDto extends BaseDto {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String role;



    public AddUserDto() {
    }

    public AddUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Person asPerson() {
        Person person = new Person();
        person.setEmail(this.getEmail());
        person.setRawPassword(this.getPassword());
        person.setRole(Role.valueOf(this.getRole()));
        person.setFirstName(this.name);
        person.setLastName(this.surname);

        return person;
    }



    public static AddUserDto from(Person person) {
        final AddUserDto personDto = new AddUserDto();
        personDto.setFrom(person);
        return personDto;
    }

    public void setFrom(Person person) {
        Objects.requireNonNull(person);
        super.setFrom(person);
        this.setEmail(person.getEmail());
        this.setName(person.getFirstName());
        this.setSurname(person.getLastName());
        this.setRole(person.getRole().getName().replace("ROLE_", ""));
        this.setPassword(person.getRawPassword());
    }
}
