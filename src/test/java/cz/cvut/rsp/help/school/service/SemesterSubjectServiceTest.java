package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.App;
import cz.cvut.rsp.help.school.dao.*;
import cz.cvut.rsp.help.school.dto.SemesterSubjectDto;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = App.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class)})
public class SemesterSubjectServiceTest {

    @Autowired
    private SemesterSubjectDao semesterSubjectDao;

    @Autowired
    private SemesterDao semesterDao;
    @Autowired
    private SemesterSubjectStudentDao studentDao;
    @Autowired
    private PersonDao personDao;

    private SemesterSubjectService semesterSubjectService;

    private SemesterSubjectStudentService semesterSubjectStudentService;




    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.semesterSubjectService = new SemesterSubjectService(semesterSubjectDao,semesterDao,studentDao, personDao);
        this.semesterSubjectStudentService = new SemesterSubjectStudentService(studentDao,semesterSubjectDao,semesterDao,personDao);
    }


    @Test
    public void addStudentAddsStudentToSemesterSubject(){

        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchoolWithPersons(5, 5, Collections.singletonList(person));
        final Semester semester = Generator.generateSemester(school,1,Collections.singletonList(person));
        final SemesterSubject subject = Generator.generateSemesterSubject(semester);


        personDao.persist(person);
        semesterDao.persist(semester);
        semesterSubjectService.persist(subject);
        final Optional<List<SemesterSubject>> former = semesterSubjectStudentService.findStudentSubjectsInSemester(semester,person);
        semesterSubjectService.addStudent(person,subject);

        final Optional<List<SemesterSubject>> result = semesterSubjectStudentService.findStudentSubjectsInSemester(semester,person);

        assertTrue(result.get().contains(subject));
        assertEquals(former.get().size()+1, result.get().size());
    }


    @Test
    public void removeStudentRemovesStudentFromSemesterSubject(){

        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchoolWithPersons(5, 5, Collections.singletonList(person));
        final Semester semester = Generator.generateSemester(school,1,Collections.singletonList(person));
        final SemesterSubject subject = Generator.generateSemesterSubject(semester);


        personDao.persist(person);
        semesterDao.persist(semester);
        semesterSubjectService.persist(subject);
        final Optional<List<SemesterSubject>> former = semesterSubjectStudentService.findStudentSubjectsInSemester(semester,person);
        semesterSubjectService.addStudent(person,subject);

        final Optional<List<SemesterSubject>> result = semesterSubjectStudentService.findStudentSubjectsInSemester(semester,person);

        assertTrue(result.get().contains(subject));
        assertEquals(former.get().size() + 1, result.get().size());

        semesterSubjectService.removeStudent(person,subject);

        final Optional<List<SemesterSubject>> result2 = semesterSubjectStudentService.findStudentSubjectsInSemester(semester,person);

        assertTrue(result2.isEmpty());

        semesterSubjectService.addStudent(person,subject);
        final Optional<List<SemesterSubject>> result3 = semesterSubjectStudentService.findStudentSubjectsInSemester(semester,person);
        assertTrue(result3.get().contains(subject));
        assertEquals(result3.get().size(),former.get().size() + 1);

    }

    @Test
    public void getScheduleReturnsCorrectSchedule(){

        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchoolWithPersons(5, 5, Collections.singletonList(person));
        final Semester semester = Generator.generateSemester(school,1,Collections.singletonList(person));
        final SemesterSubject subject = Generator.generateSemesterSubject(semester);


        personDao.persist(person);
        semesterDao.persist(semester);
        semesterSubjectService.persist(subject);
        final Optional<List<SemesterSubject>> former = semesterSubjectStudentService.findStudentSubjectsInSemester(semester,person);
        semesterSubjectService.addStudent(person,subject);

        final Optional<List<SemesterSubject>> result = semesterSubjectStudentService.findStudentSubjectsInSemester(semester,person);

        assertTrue(result.get().contains(subject));
        assertEquals(former.get().size() + 1, result.get().size());

        SemesterSubjectDto[][] schedule = semesterSubjectStudentService.getSchedule(semester,person);

        assertEquals(schedule[subject.getPeriod()][subject.getNumberOfDayInWeek()].getName(), subject.getSubject().getName());

        final SemesterSubject subject1 = Generator.generateSemesterSubject(semester);
        semesterSubjectService.persist(subject1);
        semesterSubjectService.addStudent(person,subject1);

        SemesterSubjectDto[][] schedule1 = semesterSubjectStudentService.getSchedule(semester,person);

        assertEquals(schedule1[subject.getPeriod()][subject.getNumberOfDayInWeek()].getName(), subject.getSubject().getName());
        assertEquals(schedule1[subject1.getPeriod()][subject1.getNumberOfDayInWeek()].getName(), subject1.getSubject().getName());

    }


}
