package cz.cvut.rsp.help.school.model.subject;

import cz.cvut.rsp.help.school.model.AbstractEntity;

import javax.persistence.*;

@Entity
public class SubjectAssignment extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "subject_id")
    private Subject subject;

}
