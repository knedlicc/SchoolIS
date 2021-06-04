package cz.cvut.rsp.help.school.dto;

import java.util.Objects;

import cz.cvut.rsp.help.school.dto.Person.AbstractPersonDto;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;

public final class SubjectStudentListDto extends AbstractPersonDto {
    
    private String mark;

    public String getMark() {
      return mark;
    }

    public void setMark(String mark) {
      this.mark = mark;
    }

    public void setFrom(SemesterSubjectStudent semesterSubjectStudent) {
        Objects.requireNonNull(semesterSubjectStudent);
        super.setFrom(semesterSubjectStudent.getStudent());
        this.setMark(semesterSubjectStudent.getMark().name());
    }

    public static SubjectStudentListDto from(SemesterSubjectStudent semesterSubjectStudent) {
        Objects.requireNonNull(semesterSubjectStudent);
        final SubjectStudentListDto studentListDto = new SubjectStudentListDto();
        studentListDto.setFrom(semesterSubjectStudent);
        return studentListDto;
    }

}
