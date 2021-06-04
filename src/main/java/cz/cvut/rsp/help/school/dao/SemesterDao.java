package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class SemesterDao extends AbstractDao<Semester> {

    public SemesterDao() {
        super(Semester.class);
    }


    public Optional<Long> findLastIdInSchool(Long schoolId) {
        try {
            return Optional.of(em.createNamedQuery(Semester.findLastSemesterIdInSchool, Long.class)
                .setMaxResults(1)
                .setParameter("school_id", schoolId)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
