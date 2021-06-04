package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.dto.BaseDto;
import cz.cvut.rsp.help.school.model.Person;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


public class AuthDto extends BaseDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;



    public AuthDto() {
    }

    public AuthDto(String email, String password) {
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


    public Person asPerson() {
        Person person = new Person();
        person.setEmail(this.getEmail());
        person.setRawPassword(this.getPassword());

        return person;
    }

}
