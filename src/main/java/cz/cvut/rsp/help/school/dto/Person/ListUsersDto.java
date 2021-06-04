package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.model.Person;

public class ListUsersDto extends AbstractPersonDto {
    public static ListUsersDto from(Person person) {
        final ListUsersDto dto = new ListUsersDto();
        dto.setFrom(person);
        return dto;
    }
}
