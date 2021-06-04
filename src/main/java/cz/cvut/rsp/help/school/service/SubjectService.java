package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.dao.SemesterDao;
import cz.cvut.rsp.help.school.dao.SubjectDao;
import cz.cvut.rsp.help.school.dao.SubjectExamDao;
import cz.cvut.rsp.help.school.dao.SubjectTutorDao;
import cz.cvut.rsp.help.school.dto.exam.NewExamDto;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.subject.Subject;
import cz.cvut.rsp.help.school.model.semester.SubjectExam;
import cz.cvut.rsp.help.school.model.subject.SubjectTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectDao dao;

    private final SubjectTutorDao tutorDao;

    private final SubjectExamDao subjectExamDao;

    private final SemesterDao semesterDao;

    private final PersonService personService;

    @Autowired
    public SubjectService(SubjectDao dao, SubjectTutorDao tutorDao, SubjectExamDao subjectExamDao, SemesterDao semesterDao, PersonService personService) {
        this.dao = dao;
        this.tutorDao = tutorDao;
        this.subjectExamDao = subjectExamDao;
        this.semesterDao = semesterDao;
        this.personService = personService;
    }

    @Transactional(readOnly = true)
    public Subject find(Long id) {
        return this.dao.find(id).orElseThrow(() -> new EntityNotFoundException(Subject.class, "id", id));
    }

    @Transactional
    public void persist(Subject subject) {
        Objects.requireNonNull(subject);
//        teachers.forEach(Objects::requireNonNull);
//        subject.getTutors().addAll(teachers);
//        for(SubjectTutor p : teachers){
//            if(p.getTutor().getRole().equals(Role.TEACHER)){
//                subject.getTutors().add(p);
//            }
//        }
        dao.persist(subject);
    }

    public SemesterSubject findSemesterSubjectById(Long subjectId){
        Long schoolId = personService.getCurrentPerson().getSchool().getId();
        Long semesterId = semesterDao.findLastIdInSchool(schoolId).orElseThrow((() -> new EntityNotFoundException(Semester.class, "school id", schoolId)));
        Semester sem = semesterDao.find(semesterId).orElseThrow((() -> new EntityNotFoundException(Semester.class, "id", semesterId)));
        return sem.getSubjects().stream().filter((s) -> s.getSubject().getId().equals(subjectId)).findFirst().orElseThrow((() -> new EntityNotFoundException(SemesterSubject.class, "subject id", subjectId)));
    }

    @Transactional
    public void createExam(Long subjectId, NewExamDto examDto) {

        SubjectExam subexam = new SubjectExam();
        subexam.setLocalDateTime(examDto.getLocalDateTime());
        subexam.setCapacity(examDto.getCapacity());
        subexam.setNote(examDto.getNote());

        SemesterSubject semesterSubject = findSemesterSubjectById(subjectId);

        subexam.setSemesterSubject(semesterSubject);
        semesterSubject.addExam(subexam);
        subjectExamDao.persist(subexam);

    }

    @Transactional
    public List<Subject> findSubjectsOfTeacher(Long teacher_id) {
        List<SubjectTutor> tutor = tutorDao.findByPerson(teacher_id);
        if (tutor.size() > 0) {
            List<Subject> subjects = dao.findAll();
            subjects = subjects.stream().filter(s -> s.getTutors().contains(tutor.get(0))).collect(Collectors.toList());
            return subjects;
        } else {
            throw new EntityNotFoundException(SubjectTutor.class, "tutor", teacher_id);
        }

    }

    @Transactional
    public List<Subject> listSubjects(Long id) {
        return dao.findSubjects(id);
    }

}
