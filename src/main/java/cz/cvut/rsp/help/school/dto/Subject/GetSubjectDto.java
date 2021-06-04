package cz.cvut.rsp.help.school.dto.Subject;

import java.util.Set;
import java.util.stream.Collectors;

import cz.cvut.rsp.help.school.dto.BaseDto;
import cz.cvut.rsp.help.school.dto.Person.AbstractPersonDto;
import cz.cvut.rsp.help.school.model.subject.Subject;
import cz.cvut.rsp.help.school.model.subject.SubjectCompletion;
import cz.cvut.rsp.help.school.model.subject.SubjectTutor;
import cz.cvut.rsp.help.school.model.subject.SubjectType;

public class GetSubjectDto extends BaseDto {

    private static class TutorDto extends AbstractPersonDto {
        public static TutorDto from(SubjectTutor subjectTutor) {
            final TutorDto dto = new TutorDto();
            dto.setFrom(subjectTutor.getTutor());
            return dto;
        }
    }

    private Long school;

    private Set<TutorDto> tutors;

    private String code;

    private String name;

    private Integer credits;

    private SubjectType type;

    private SubjectCompletion completion;

    private Long semesterId;

    private Long semesterSubjectId;

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public Set<TutorDto> getTutors() {
        return this.tutors;
    }

    public void setTutors(Set<TutorDto> tutors) {
        this.tutors = tutors;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return this.credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public SubjectType getType() {
        return this.type;
    }

    public void setType(SubjectType type) {
        this.type = type;
    }

    public SubjectCompletion getCompletion() {
        return this.completion;
    }

    public void setCompletion(SubjectCompletion completion) {
        this.completion = completion;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public GetSubjectDto setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
        return this;
    }

    public Long getSemesterSubjectId() {
        return semesterSubjectId;
    }

    public GetSubjectDto setSemesterSubjectId(Long semesterSubjectId) {
        this.semesterSubjectId = semesterSubjectId;
        return this;
    }

    public void setFrom(Subject subject) {
        super.setFrom(subject);
        this.setSchool(subject.getSchool().getId());
        this.setTutors(subject.getTutors().stream().map(TutorDto::from).collect(Collectors.toSet()));
        this.setCode(subject.getCode());
        this.setName(subject.getName());
        this.setCredits(subject.getCredits());
        this.setType(subject.getType());
        this.setCompletion(subject.getCompletion());
    }

    public static GetSubjectDto from(Subject subject) {
        final GetSubjectDto subjectDto = new GetSubjectDto();
        subjectDto.setFrom(subject);
        return subjectDto;
    }
}
