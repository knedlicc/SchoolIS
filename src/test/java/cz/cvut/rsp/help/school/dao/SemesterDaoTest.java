package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.App;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.service.SystemInitializer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = App.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class)})
class SemesterDaoTest {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SemesterDao semesterDao;


    @Test
    public void findLastIdInSchool_semesterIsNotPresent_expectedToReturnEmpty() {
        final School school = Generator.generateSchool(0, 0);

        schoolDao.persist(school);

        assertTrue(semesterDao.findLastIdInSchool(school.getId()).isEmpty());
    }

    @Test
    public void findLastIdInSchool_semesterIsPresent_expectedToFindLastSemester() {
        final int nSemesters = 5;
        final School school = Generator.generateSchool(5, nSemesters);
        // prob there's a smarter way to get last element but tbh who cares ¯\_(ツ)_/¯
        final Semester semester = school.getSemesters()
            .stream()
            .filter(semester1 -> semester1.getYear() >= nSemesters / 2)
            .reduce((semester1, semester2) -> semester1.getSemester() > semester2.getSemester() ? semester1 : semester2)
            .get();

        schoolDao.persist(school);

        final Long lastSemester = semesterDao.findLastIdInSchool(school.getId()).get();
        assertEquals(semester.getId(), lastSemester);
    }

}
