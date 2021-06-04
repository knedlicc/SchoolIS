package cz.cvut.rsp.help.school.model.semester;

import cz.cvut.rsp.help.school.model.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SubjectExam extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "subject_id")
    private SemesterSubject semesterSubject;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Basic(optional = false)
    @Column(nullable = false)
    private int capacity;

    private String note;

    public SubjectExam() {

    }

    public SemesterSubject getSemesterSubject() {
        return semesterSubject;
    }

    public void setSemesterSubject(SemesterSubject subject) {
        this.semesterSubject = subject;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
