package cz.cvut.rsp.help.school.model.semester;

import cz.cvut.rsp.help.school.model.AbstractEntity;
import cz.cvut.rsp.help.school.model.School;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
    @NamedQuery(name = Semester.findLastSemesterIdInSchool, query = "SELECT s.id FROM Semester s WHERE s.school.id = :school_id ORDER BY s.year DESC, s.semester DESC")
})
public class Semester extends AbstractEntity {

    public final static String findLastSemesterIdInSchool = "Semester.findLastSemesterIdInSchool";


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
    private School school;

    @Basic(optional = false)
    private Integer year;

    @Basic(optional = false)
    private Integer semester;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "semester_id")
    private Set<SemesterSubject> subjects;


    public Semester() {
        this.subjects = new HashSet<>();
    }


    public School getSchool() {
        return school;
    }

    public Semester setSchool(School school) {
        this.school = school;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Semester setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getSemester() {
        return semester;
    }

    public Semester setSemester(Integer semester) {
        this.semester = semester;
        return this;
    }

    public Set<SemesterSubject> getSubjects() {
        return subjects;
    }

    public Semester setSubjects(Set<SemesterSubject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public void addSubject (SemesterSubject subject){
        this.subjects.add(subject);
    }

}
