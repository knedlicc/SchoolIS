package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.model.Person;

public class ListStudentsDto extends AbstractPersonDto {
    public static ListStudentsDto from(Person person) {
        final ListStudentsDto dto = new ListStudentsDto();
        dto.setFrom(person);
        return dto;
    }
}
