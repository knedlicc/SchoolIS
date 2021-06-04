package cz.cvut.rsp.help.school.model.semester;

import cz.cvut.rsp.help.school.model.AbstractEntity;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.subject.Subject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = SemesterSubject.findStudentsBySubjectAndSemester, query = "SELECT sss.students FROM SemesterSubject sss WHERE sss.semester.id = :semester_id AND sss.subject.id = :subject_id"),
        @NamedQuery(name = SemesterSubject.findBySemesterIdAndSubjectId, query = "SELECT ss.id FROM SemesterSubject ss WHERE ss.semester.id = :semester_id AND ss.subject.id = :subject_id") })
@Entity
public class SemesterSubject extends AbstractEntity {

    public final static String findStudentsBySubjectAndSemester = "SemesterSubject.findStudentsBySubjectAndSemester";
    public final static String findBySemesterIdAndSubjectId = "SemesterSubject.findBySemesterIdAndSubjectId";

    @ManyToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "semester_subject_id")
    private Set<SemesterSubjectStudent> students;

    @Basic(optional = false)
    @Column(nullable = true)
    private Integer period;

    @Basic(optional = false)
    @Column(nullable = true)
    private Integer numberOfDayInWeek;

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "subject_id")
    private Set<SubjectExam> exams;

    public SemesterSubject() {
        this.students = new HashSet<>();
        this.exams = new HashSet<>();
    }

    public Semester getSemester() {
        return semester;
    }

    public SemesterSubject setSemester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public Subject getSubject() {
        return subject;
    }

    public SemesterSubject setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public Set<SemesterSubjectStudent> getStudents() {
        return students;
    }

    public SemesterSubject setStudents(Set<SemesterSubjectStudent> students) {
        this.students = students;
        return this;
    }

    public Integer getNumberOfDayInWeek() {
        return this.numberOfDayInWeek;
    }

    public void setNumberOfDayInWeek(Integer dayNumber) {
        this.numberOfDayInWeek = dayNumber;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public static SemesterSubject from(Semester semester, Subject subject) {
        return new SemesterSubject().setSemester(semester).setSubject(subject);
    }

    public void addStudent(SemesterSubjectStudent student) {
        this.students.add(student);
    }

    public void removeStudent(SemesterSubjectStudent student) {
        this.students.remove(student);
    }

    public SemesterSubjectStudent findStudent(Person person) {
        for (SemesterSubjectStudent sss : this.students) {
            if (sss.getStudent().getId().equals(person.getId()))
                return sss;
        }
        return null;
    }

    public Set<SubjectExam> getExams() {
        return exams;
    }

    public void addExam(SubjectExam s) {
        exams.add(s);
    }

}
