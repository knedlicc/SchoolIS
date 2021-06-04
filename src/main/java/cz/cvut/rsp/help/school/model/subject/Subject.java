package cz.cvut.rsp.help.school.model.subject;

import cz.cvut.rsp.help.school.model.AbstractEntity;
import cz.cvut.rsp.help.school.model.School;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "subject_id")
    private Set<SubjectTutor> tutors;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "subject_id")
    private Set<SubjectAssignment> assignments;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String code;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer credits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectCompletion completion;


    public Subject() {
        this.tutors = new HashSet<>();
        this.assignments = new HashSet<>();
    }


    public School getSchool() {
        return school;
    }

    public Subject setSchool(School school) {
        this.school = school;
        return this;
    }

    public Set<SubjectTutor> getTutors() {
        return tutors;
    }

    public Subject setTutors(Set<SubjectTutor> tutors) {
        this.tutors = tutors;
        return this;
    }

    public Set<SubjectAssignment> getAssignments() {
        return assignments;
    }

    public Subject setAssignments(Set<SubjectAssignment> assignments) {
        this.assignments = assignments;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Subject setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCredits() {
        return credits;
    }

    public Subject setCredits(Integer credits) {
        this.credits = credits;
        return this;
    }

    public SubjectType getType() {
        return type;
    }

    public Subject setType(SubjectType type) {
        this.type = type;
        return this;
    }

    public SubjectCompletion getCompletion() {
        return completion;
    }

    public Subject setCompletion(SubjectCompletion completion) {
        this.completion = completion;
        return this;
    }
}
