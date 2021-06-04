package cz.cvut.rsp.help.school.dto;

import cz.cvut.rsp.help.school.dto.Person.AbstractPersonDto;
import cz.cvut.rsp.help.school.model.Person;


public class WhoAmIDto extends AbstractPersonDto {

    private String role;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public static WhoAmIDto from(Person person) {
        final WhoAmIDto whoAmIDto = new WhoAmIDto();
        whoAmIDto.setId(person.getId());
        whoAmIDto.setEmail(person.getEmail());
        whoAmIDto.setUsername(person.getUsername());
        whoAmIDto.setRole(person.getRole().getName());
        return whoAmIDto;
    }

}
