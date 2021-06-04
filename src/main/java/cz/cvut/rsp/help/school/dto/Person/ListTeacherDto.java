package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.model.Person;

public class ListTeacherDto extends AbstractPersonDto {
    public static ListTeacherDto from(Person person) {
        final ListTeacherDto dto = new ListTeacherDto();
        dto.setFrom(person);
        return dto;
    }
}
