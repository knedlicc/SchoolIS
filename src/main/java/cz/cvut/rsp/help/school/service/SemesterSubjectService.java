package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.dao.PersonDao;
import cz.cvut.rsp.help.school.dao.SemesterDao;
import cz.cvut.rsp.help.school.dao.SemesterSubjectDao;
import cz.cvut.rsp.help.school.dto.Person.PersonDto;
import cz.cvut.rsp.help.school.dao.SemesterSubjectStudentDao;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

@Service
public class SemesterSubjectService {

    private final SemesterSubjectDao semesterSubjectDao;

    private final SemesterDao semesterDao;

    private final SemesterSubjectStudentDao semesterSubjectStudentDao;
    private final PersonDao personDao;

    @Autowired
    public SemesterSubjectService(SemesterSubjectDao semesterSubjectDao, SemesterDao semesterDao,
            SemesterSubjectStudentDao semesterSubjectStudentDao, PersonDao personDao) {
        this.semesterSubjectDao = semesterSubjectDao;
        this.semesterDao = semesterDao;
        this.semesterSubjectStudentDao = semesterSubjectStudentDao;
        this.personDao = personDao;
    }

    @Transactional(readOnly = true)
    public List<SemesterSubjectStudent> getPersonSemesterSubjects(Long schoolId, Long personId) {
        final Long semesterId = this.semesterDao.findLastIdInSchool(schoolId)
                .orElseThrow(() -> new EntityNotFoundException(Semester.class, "school_id", schoolId));

        // TODO: Merge with teachers subjects list made by Andrey (bp5)

        return this.semesterSubjectDao.findStudentSubjectsInSemester(semesterId, personId);
    }

    @Transactional(readOnly = true)
    public List<SemesterSubjectStudent> getSubjectStudents(Long semesterId, Long subjectId) {
        return semesterSubjectDao.findStudentsBySubjectAndSemester(semesterId, subjectId);
    }

    @Transactional(readOnly = true)
    public Optional<SemesterSubject> find(Long id) {
        return semesterSubjectDao.find(id);
    }

    @Transactional(readOnly = true)
    public List<SemesterSubjectStudent> findStudentSubjectsInSemester(Long semesterId, Long studentId) {
        return semesterSubjectDao.findStudentSubjectsInSemester(semesterId, studentId);
    }

    @Transactional
    public void persist(SemesterSubject subject) {
        Objects.requireNonNull(subject);
        semesterSubjectDao.persist(subject);
    }

    @Transactional
    public void addStudent(Person person, SemesterSubject subject) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(subject);
        SemesterSubjectStudent student = Generator.generateSemesterSubjectStudent(person, subject);
        subject.addStudent(student);
        semesterSubjectStudentDao.persist(student);
        semesterSubjectDao.update(subject);
        personDao.update(person);
    }

    @Transactional
    public void removeStudent(Person person, SemesterSubject subject) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(subject);

        SemesterSubjectStudent student = subject.findStudent(person);
        subject.removeStudent(student);
        semesterSubjectStudentDao.remove(student);
    }

}
