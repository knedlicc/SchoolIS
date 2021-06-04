package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.Objects;
import java.util.List;
import java.util.Optional;

@Repository
public class SemesterSubjectStudentDao extends AbstractDao<SemesterSubjectStudent> {

    public SemesterSubjectStudentDao() {
        super(SemesterSubjectStudent.class);
    }

    @Override
    public void persist(SemesterSubjectStudent entity) {
        Objects.requireNonNull(entity);
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    public Optional<SemesterSubjectStudent> findBySemesterSubjectAndStudent(Long semesterSubjectId, Long studentId) {
        return this.findAll().stream().filter(s -> s.getSemesterSubject().getId().equals(semesterSubjectId)
                && s.getStudent().getId().equals(studentId)).findFirst();
    }

    // public List<SemesterSubjectStudent> findAll() {
    //     return em.createQuery("SELECT s FROM SemesterSubjectStudent", SemesterSubjectStudent.class).getResultList();
    // }

    public Optional<List<SemesterSubjectStudent>> findStudentSubjectsInSemester(Semester semester, Person person) {
        List<SemesterSubjectStudent> semesterSubjects = em
                .createNamedQuery("SemesterSubjectStudent.findStudentSubjectsInSemester", SemesterSubjectStudent.class)
                .setParameter("semester", semester).setParameter("student", person).getResultList();

        if (semesterSubjects.size() > 0) {
            return Optional.of(semesterSubjects);
        }

        return Optional.empty();
    }

    public void deleteSemesterSubjectStudent(Long semesterId, Long subjectId, Long studentId) {
        SemesterSubjectStudent semesterSubjectStudent = (SemesterSubjectStudent) em
                .createNamedQuery(SemesterSubjectStudent.findSemesterSubjectStudentPrecise)
                .setParameter("semester_id", semesterId).setParameter("subject_id", subjectId)
                .setParameter("student_id", studentId).getSingleResult();

        this.remove(semesterSubjectStudent);
    }

}
