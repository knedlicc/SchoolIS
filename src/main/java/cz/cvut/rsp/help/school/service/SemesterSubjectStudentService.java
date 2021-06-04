package cz.cvut.rsp.help.school.service;

import java.util.Objects;

import cz.cvut.rsp.help.school.dao.PersonDao;
import cz.cvut.rsp.help.school.dao.SemesterSubjectDao;
import cz.cvut.rsp.help.school.dto.semester.SemesterSubjectStudentDto;
import cz.cvut.rsp.help.school.exception.EntityAlreadyExistsException;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.semester.Mark;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.dao.SemesterDao;
import cz.cvut.rsp.help.school.dao.SemesterSubjectStudentDao;
import cz.cvut.rsp.help.school.dto.SemesterSubjectDto;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SemesterSubjectStudentService {

    private final SemesterSubjectStudentDao dao;
    private final SemesterSubjectDao semesterSubjectDao;
    private final SemesterDao semesterDao;
    private final PersonDao personDao;

    @Autowired
    public SemesterSubjectStudentService(SemesterSubjectStudentDao dao, SemesterSubjectDao semesterSubjectDao,
            SemesterDao semesterDao, PersonDao personDao) {
        this.dao = dao;
        this.semesterSubjectDao = semesterSubjectDao;
        this.semesterDao = semesterDao;
        this.personDao = personDao;
    }

    @Transactional(readOnly = true)
    public List<SemesterSubjectStudent> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<SemesterSubjectStudent> find(Long id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(SemesterSubjectStudent student) {
        Objects.requireNonNull(student);
        dao.persist(student);
    }

    @Transactional(readOnly = true)
    public Optional<List<SemesterSubject>> findStudentSubjectsInSemester(Semester semester, Person person) {
        List<SemesterSubjectStudent> subjectStudentList = dao.findStudentSubjectsInSemester(semester, person)
                .orElseThrow(() -> new EntityNotFoundException(SemesterSubjectStudent.class));
        List<SemesterSubject> subjectList = new ArrayList<SemesterSubject>();
        for (int i = 0; i < subjectStudentList.size(); i++) {
            subjectList.add(subjectStudentList.get(i).getSemesterSubject());
        }
        return Optional.of(subjectList);
    }

    @Transactional
    public void addMarkToStudent(SemesterSubjectStudentDto semesterSubjectStudentDto) {
        SemesterSubjectStudent semesterSubjectStudent = dao
                .findBySemesterSubjectAndStudent(semesterSubjectStudentDto.getSemesterSubjectId(),
                        semesterSubjectStudentDto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(SemesterSubjectStudent.class));
        semesterSubjectStudent.setMark(Mark.valueOf(semesterSubjectStudentDto.getMark()));
        dao.update(semesterSubjectStudent);
        // if (toCheck != null && toCheck.getMark() != null) {
        // throw new EntityAlreadyExistsException("SemesterSubjectStudentDto");
        // }
        // SemesterSubjectStudent semesterSubjectStudentToSave = new
        // SemesterSubjectStudent();
        // Person student =
        // personDao.find(semesterSubjectStudentDto.getStudentId());
        // SemesterSubject semesterSubject =
        // semesterSubjectDao.find(semesterSubjectStudentDto.getSemesterSubjectId());
        // String mark = semesterSubjectStudentDto.getMark();

        // if (student == null) {
        // throw new EntityNotFoundException(Person.class, "id",
        // String.valueOf(semesterSubjectStudentDto.getStudentId()));
        // } else if (semesterSubject == null) {
        // throw new EntityNotFoundException(SemesterSubject.class, "id",
        // String.valueOf(semesterSubjectStudentDto.getSemesterSubjectId()));
        // }

        // semesterSubjectStudentToSave.setMark(Mark.valueOf(mark));
        // semesterSubjectStudentToSave.setStudent(student);
        // semesterSubjectStudentToSave.setSemesterSubject(semesterSubject);

        // dao.persist(semesterSubjectStudentToSave);
    }

    public void addSubject(SemesterSubject subject, SemesterSubjectStudent student) {
        Objects.requireNonNull(subject);
        Objects.requireNonNull(student);
        subject.addStudent(student);
        semesterSubjectDao.update(subject);
    }

    @Transactional
    public void removeSubject(SemesterSubject subject, SemesterSubjectStudent student) {
        Objects.requireNonNull(subject);
        Objects.requireNonNull(student);
        subject.removeStudent(student);
        semesterSubjectDao.update(subject);
    }

    public SemesterSubjectDto[][] getSchedule(Semester semester, Person person) {
        Optional<List<SemesterSubject>> listOfSubjects = this.findStudentSubjectsInSemester(semester, person);
        SemesterSubjectDto[][] schedule = new SemesterSubjectDto[5][10];
        if (listOfSubjects.isPresent()) {
            for (int i = 0; i < listOfSubjects.get().size(); i++) {
                Integer period = listOfSubjects.get().get(i).getPeriod();
                Integer dayNumber = listOfSubjects.get().get(i).getNumberOfDayInWeek();
                if (period != null && dayNumber != null) {
                    schedule[dayNumber][period] = new SemesterSubjectDto(listOfSubjects.get().get(i));
                }
            }
            return schedule;
        }
        return null;
    }

    @Transactional
    public void deleteSemesterSubjectStudent(Long semesterId, Long subjectId, Long studentId) {
        this.dao.deleteSemesterSubjectStudent(semesterId, subjectId, studentId);
    }
}
