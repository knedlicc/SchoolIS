package cz.cvut.rsp.help.school.model.subject;

import cz.cvut.rsp.help.school.model.AbstractEntity;
import cz.cvut.rsp.help.school.model.Person;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "SubjectTutor.findByPerson", query = "SELECT u FROM SubjectTutor u WHERE u.tutor.id = :tutor")
})
public class SubjectTutor extends AbstractEntity {


    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "person_id")
    private Person tutor;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Person getTutor() {
        return tutor;
    }

    public void setTutor(Person tutor) {
        this.tutor = tutor;
    }
}
