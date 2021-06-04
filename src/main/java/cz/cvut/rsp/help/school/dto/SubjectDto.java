package cz.cvut.rsp.help.school.dto;


import cz.cvut.rsp.help.school.model.subject.Subject;

public class SubjectDto extends BaseDto {


    private String school;


    private String tutors;


    private String assignments;

    private String name;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTutors() {
        return tutors;
    }

    public void setTutors(String tutors) {
        this.tutors = tutors;
    }

    public String getAssignments() {
        return assignments;
    }

    public void setAssignments(String assignments) {
        this.assignments = assignments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SubjectDto from(Subject subject) {
        final SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSchool(subject.getSchool().toString());
        subjectDto.setTutors(subject.getTutors().toString());
        subjectDto.setAssignments(subject.getAssignments().toString());
        return subjectDto;
    }
}
