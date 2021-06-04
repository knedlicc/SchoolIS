package cz.cvut.rsp.help.school.dto;

import cz.cvut.rsp.help.school.model.semester.SemesterSubject;

public class SemesterSubjectDto extends BaseDto {

    private Long id;
    private String name;
    private String code;
    private String tutor;

    public SemesterSubjectDto(SemesterSubject semesterSubject) {
        this.id = semesterSubject.getSubject().getId();
        this.name = semesterSubject.getSubject().getName();
        this.code = semesterSubject.getSubject().getCode();
    }

    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getTutor() {
        return tutor;
    }

    public String getCode() {
        return code;
    }
}
