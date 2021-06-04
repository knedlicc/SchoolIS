package cz.cvut.rsp.help.school.model.semester;

import cz.cvut.rsp.help.school.model.AbstractEntity;
import cz.cvut.rsp.help.school.model.subject.SubjectAssignment;

import javax.persistence.*;

@Entity
public class SemesterSubjectStudentAssignment extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "semester_subject_student_id")
    private SemesterSubjectStudent semesterSubjectStudent;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "subject_assignment_id")
    private SubjectAssignment subjectAssignment;

    @Enumerated(EnumType.STRING)
    private Mark mark;

}
