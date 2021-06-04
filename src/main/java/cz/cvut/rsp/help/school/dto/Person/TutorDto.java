package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.dto.BaseDto;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.subject.Subject;


import java.util.ArrayList;
import java.util.List;

public class TutorDto extends BaseDto {
    private String string_id;
    private String name;

    private String email;

    private List<Subject> subjectList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getStringId() {
        return string_id;
    }

    public void setId(String id) {
        this.string_id = id;
    }

    public static TutorDto from(Person person) {
        final TutorDto personDto = new TutorDto();
        personDto.name = person.getFirstName() +" "+ person.getLastName();
        personDto.email = person.getEmail();
        personDto.string_id = person.getId().toString();
        return personDto;
    }

}
