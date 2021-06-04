package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SemesterSubjectDao extends AbstractDao<SemesterSubject> {

    public SemesterSubjectDao() {
        super(SemesterSubject.class);
    }


    public List<SemesterSubjectStudent> findStudentSubjectsInSemester(Long semesterId, Long studentId) {
        return em.createNamedQuery(SemesterSubjectStudent.findStudentSubjectsInSemester2, SemesterSubjectStudent.class)
                .setParameter("semester_id", semesterId).setParameter("student_id", studentId).getResultList();
    }

    public List<SemesterSubjectStudent> findStudentsBySubjectAndSemester(Long semesterId, Long subjectId){
        return em.createNamedQuery(SemesterSubject.findStudentsBySubjectAndSemester, SemesterSubjectStudent.class)
                .setParameter("subject_id", subjectId).setParameter("semester_id", semesterId).getResultList();
    }

}
