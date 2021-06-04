package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.App;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import cz.cvut.rsp.help.school.service.SystemInitializer;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = App.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class) })
class SemesterSubjectDaoTest {

    @Autowired
    private SemesterSubjectDao semesterSubjectDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private SemesterDao semesterDao;

    @Autowired
    private SemesterSubjectStudentDao semesterSubjectStudentDao;


    @Test
    public void findStudentSubjectsInSemester_semesterIsNotPresent_expectedToReturnEmpty() {
        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchool(0, 0);
        final Long nonExistentSemesterId = 999L;
        person.setSchool(school);
        personDao.persist(person);
        final boolean expectedIsEmpty = true;

        final List<SemesterSubjectStudent> semesterSubjects = semesterSubjectDao
                .findStudentSubjectsInSemester(nonExistentSemesterId, person.getId());

        assertEquals(expectedIsEmpty, semesterSubjects.isEmpty());
    }

    @Test
    public void findStudentSubjectsInSemester_semesterIsPresent_expectedToFindStudentSubjectsInLastSemester() {
        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchoolWithPersons(5, 5, Collections.singletonList(person));
        personDao.persist(person);
        final boolean expectedIsEmpty = false;
        final Long lastSemesterId = semesterDao.findLastIdInSchool(school.getId()).get();

        final List<SemesterSubjectStudent> semesterSubjects = semesterSubjectDao
                .findStudentSubjectsInSemester(lastSemesterId, person.getId());

        assertEquals(expectedIsEmpty, semesterSubjects.isEmpty());
    }

    @Test
    public void addSemesterStudentToSemesterSubjectAddsSemesterStudent() {
        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchoolWithPersons(5, 5, Collections.singletonList(person));
        final Semester semester = Generator.generateSemester(school,1,Collections.singletonList(person));
        final SemesterSubject subject = Generator.generateSemesterSubject(semester);
        final SemesterSubjectStudent student = Generator.generateSemesterSubjectStudent(person,subject);
        personDao.persist(person);
        semesterDao.persist(semester);
        semesterSubjectDao.persist(subject);
        semesterSubjectStudentDao.persist(student);

        Optional<SemesterSubjectStudent> student1 = semesterSubjectStudentDao.find(student.getId());
        assertEquals(student1.get().getSemesterSubject(),subject);
    }


    @Test
    public void remove() {
        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchoolWithPersons(5, 5, Collections.singletonList(person));
        final Semester semester = Generator.generateSemester(school,1,Collections.singletonList(person));
        final SemesterSubject subject = Generator.generateSemesterSubject(semester);
        final SemesterSubjectStudent student = Generator.generateSemesterSubjectStudent(person,subject);
        personDao.persist(person);
        semesterDao.persist(semester);
        semesterSubjectDao.persist(subject);
        semesterSubjectStudentDao.persist(student);

        Optional<SemesterSubjectStudent> student1 = semesterSubjectStudentDao.find(student.getId());
        assertEquals(student1.get().getSemesterSubject(),subject);

        subject.removeStudent(student);
        semesterSubjectStudentDao.remove(student);
        semesterSubjectDao.update(subject);

        Optional<SemesterSubjectStudent> student2 = semesterSubjectStudentDao.find(student.getId());
        assertTrue(student2.isEmpty());

    }



    @Test
    public void findAllSubjects() {
        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchoolWithPersons(5, 5, Collections.singletonList(person));
        final Semester semester = Generator.generateSemester(school,1,Collections.singletonList(person));
        final SemesterSubject subject = Generator.generateSemesterSubject(semester);
        final SemesterSubjectStudent student = Generator.generateSemesterSubjectStudent(person,subject);
        personDao.persist(person);
        semesterDao.persist(semester);
        semesterSubjectDao.persist(subject);
        semesterSubjectStudentDao.persist(student);


        Optional<List<SemesterSubjectStudent>> list = semesterSubjectStudentDao.findStudentSubjectsInSemester(semester,person);

        assertTrue(list.get().contains(student));

    }


}
