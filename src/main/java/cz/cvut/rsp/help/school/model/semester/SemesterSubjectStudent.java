package cz.cvut.rsp.help.school.model.semester;

import cz.cvut.rsp.help.school.model.AbstractEntity;
import cz.cvut.rsp.help.school.model.Person;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = SemesterSubjectStudent.findStudentSubjectsInSemester2, query = "SELECT sss FROM SemesterSubjectStudent sss WHERE sss.semesterSubject.semester.id = :semester_id AND sss.student.id = :student_id"),
        @NamedQuery(name = "SemesterSubjectStudent.findStudentSubjectsInSemester", query = "SELECT sss FROM SemesterSubjectStudent sss WHERE sss.semesterSubject.semester = :semester AND sss.student = :student"),
        @NamedQuery(name = SemesterSubjectStudent.findSemesterSubjectStudentPrecise, query = "SELECT sss FROM SemesterSubjectStudent sss WHERE sss.semesterSubject.semester.id = :semester_id AND sss.semesterSubject.subject.id = :subject_id AND sss.student.id = :student_id") })
public class SemesterSubjectStudent extends AbstractEntity {

    public final static String findStudentSubjectsInSemester2 = "SemesterSubjectStudent.findStudentSubjectsInSemester2";
    public final static String findStudentSubjectsInSemester = "SemesterSubjectStudent.findStudentSubjectssInSemester";
    public final static String findSemesterSubjectStudentPrecise = "SemesterSubjectStudent.findSemesterSubjectStudentPrecise";

    @ManyToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "semester_subject_id")
    private SemesterSubject semesterSubject;

    @ManyToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "student_id")
    private Person student;

    @Enumerated(EnumType.STRING)
    private Mark mark;

    public SemesterSubjectStudent() {
        this.mark = Mark.NO_MARK;
    }

    public SemesterSubject getSemesterSubject() {
        return semesterSubject;
    }

    public SemesterSubjectStudent setSemesterSubject(SemesterSubject semesterSubject) {
        this.semesterSubject = semesterSubject;
        return this;
    }

    public Person getStudent() {
        return student;
    }

    public SemesterSubjectStudent setStudent(Person student) {
        this.student = student;
        return this;
    }

    public Mark getMark() {
        return mark;
    }

    public SemesterSubjectStudent setMark(Mark mark) {
        this.mark = mark;
        return this;
    }

    public static SemesterSubjectStudent from(SemesterSubject semesterSubject, Person student) {
        return new SemesterSubjectStudent().setSemesterSubject(semesterSubject).setStudent(student);
    }

}
