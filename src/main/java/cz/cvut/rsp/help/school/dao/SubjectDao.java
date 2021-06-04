package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.subject.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubjectDao extends AbstractDao<Subject> {

    public SubjectDao() {
        super(Subject.class);
    }

    public List<Subject> findSubjects(Long id){
        try{
            return em.createNamedQuery("Person.findSubjects", Subject.class).setParameter("tutor",id).getResultList();
        } catch (Exception e){
            throw new EntityNotFoundException(Subject.class, "tutor", id);
        }
    }
}
