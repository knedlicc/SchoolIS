package cz.cvut.rsp.help.school.model;

import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.subject.Subject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class School extends AbstractEntity {

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
    private Set<Subject> subjects;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
    private Set<Semester> semesters;


    public School() {
        this.subjects = new HashSet<>();
        this.semesters = new HashSet<>();
    }


    public Set<Subject> getSubjects() {
        return subjects;
    }

    public School setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public School setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
        return this;
    }

}
