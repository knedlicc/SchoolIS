package cz.cvut.rsp.help.school.api;

import cz.cvut.rsp.help.school.api.v1.SemesterSubjectController;
import cz.cvut.rsp.help.school.dao.*;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.service.SemesterSubjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SemesterSubjectControllerTest {

    @Mock
    private SemesterSubjectService semesterSubjectService;

    @Autowired
    private SemesterDao semesterDao;
    @Autowired
    private SemesterSubjectStudentDao studentDao;
    @Autowired
    private SemesterSubjectDao subjectDao;
    @Autowired
    private PersonDao personDao;

    @InjectMocks
    private SemesterSubjectController semesterSubjectController;

    private MockMvc mockMvc;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    //    this.mockMvc = MockMvcBuilders.standaloneSetup(new SemesterSubjectController(se)).build();
    }

    @Test
    public void addStudentAddsStudent () throws Exception {
        final Person person = Generator.generatePerson();
        final School school = Generator.generateSchoolWithPersons(5, 5, Collections.singletonList(person));
        final Semester semester = Generator.generateSemester(school,1,Collections.singletonList(person));
        final SemesterSubject subject = Generator.generateSemesterSubject(semester);

        personDao.persist(person);
        semesterDao.persist(semester);
        subjectDao.persist(subject);

        mockMvc.perform(post("/v1/semester/subjects"+ subject.getId()));


    }
}
