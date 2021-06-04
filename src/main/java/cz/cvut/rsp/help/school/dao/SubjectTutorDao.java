package cz.cvut.rsp.help.school.dao;



import cz.cvut.rsp.help.school.model.subject.SubjectTutor;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class SubjectTutorDao extends AbstractDao<SubjectTutor> {

    public SubjectTutorDao() {
        super(SubjectTutor.class);
    }


    public List<SubjectTutor> findByPerson(Long person_id) {
        try {
            return em.createNamedQuery("SubjectTutor.findByPerson", SubjectTutor.class).setParameter("tutor",person_id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
