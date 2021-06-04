package cz.cvut.rsp.help.school.dto;

import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import cz.cvut.rsp.help.school.model.subject.*;

public class ListSubjectDto extends BaseDto {

    private Long schoolId;

    private String code;

    private String name;

    private Integer credits;

    private SubjectType type;

    private SubjectCompletion completion;

    private String mark;

    public ListSubjectDto() {
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public ListSubjectDto setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ListSubjectDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public ListSubjectDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCredits() {
        return credits;
    }

    public ListSubjectDto setCredits(Integer credits) {
        this.credits = credits;
        return this;
    }

    public SubjectType getType() {
        return type;
    }

    public ListSubjectDto setType(SubjectType type) {
        this.type = type;
        return this;
    }

    public SubjectCompletion getCompletion() {
        return completion;
    }

    public ListSubjectDto setCompletion(SubjectCompletion completion) {
        this.completion = completion;
        return this;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setFrom(Subject subject) {
        super.setFrom(subject);
        this.setSchoolId(subject.getSchool().getId());
        this.setCode(subject.getCode());
        this.setName(subject.getName());
        this.setCredits(subject.getCredits());
        this.setType(subject.getType());
        this.setCompletion(subject.getCompletion());
    }

    public static ListSubjectDto from(SemesterSubject semesterSubject) {
        final ListSubjectDto listSubjectDto = new ListSubjectDto();
        listSubjectDto.setFrom(semesterSubject.getSubject());
        return listSubjectDto;
    }

    public static ListSubjectDto from(SemesterSubjectStudent semesterSubjectStudent) {
        final ListSubjectDto listSubjectDto = new ListSubjectDto();
        listSubjectDto.setFrom(semesterSubjectStudent.getSemesterSubject().getSubject());
        listSubjectDto.setMark(semesterSubjectStudent.getMark().name());
        return listSubjectDto;
    }

}
