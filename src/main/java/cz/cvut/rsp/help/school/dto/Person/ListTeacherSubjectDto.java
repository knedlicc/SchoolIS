package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.dto.BaseDto;
import cz.cvut.rsp.help.school.model.subject.Subject;
import cz.cvut.rsp.help.school.model.subject.SubjectCompletion;
import cz.cvut.rsp.help.school.model.subject.SubjectType;

public class ListTeacherSubjectDto extends BaseDto {

    private Long schoolId;

    private String code;

    private String name;

    private Integer credits;

    private SubjectType type;

    private SubjectCompletion completion;

    public ListTeacherSubjectDto() {
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public ListTeacherSubjectDto setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ListTeacherSubjectDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public ListTeacherSubjectDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCredits() {
        return credits;
    }

    public ListTeacherSubjectDto setCredits(Integer credits) {
        this.credits = credits;
        return this;
    }

    public SubjectType getType() {
        return type;
    }

    public ListTeacherSubjectDto setType(SubjectType type) {
        this.type = type;
        return this;
    }

    public SubjectCompletion getCompletion() {
        return completion;
    }

    public ListTeacherSubjectDto setCompletion(SubjectCompletion completion) {
        this.completion = completion;
        return this;
    }

    public static ListTeacherSubjectDto from(Subject semesterSubject) {
        final ListTeacherSubjectDto listSubjectDto = new ListTeacherSubjectDto();
        listSubjectDto.setFrom(semesterSubject);
        listSubjectDto.setSchoolId(semesterSubject.getSchool().getId());
        listSubjectDto.setCode(semesterSubject.getCode());
        listSubjectDto.setName(semesterSubject.getName());
        listSubjectDto.setCredits(semesterSubject.getCredits());
        listSubjectDto.setType(semesterSubject.getType());
        listSubjectDto.setCompletion(semesterSubject.getCompletion());
        return listSubjectDto;
    }

}
