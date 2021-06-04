package cz.cvut.rsp.help.school.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.cvut.rsp.help.school.dao.SemesterDao;
import cz.cvut.rsp.help.school.dto.Person.ListTeacherSubjectDto;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import cz.cvut.rsp.help.school.model.subject.Subject;
import cz.cvut.rsp.help.school.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerTest {

    @Mock
    private SubjectService subjectServiceMock;

    @Mock
    private SemesterSubjectService semesterSubjectServiceMock;

    @Mock
    private PersonService personService;

    @Mock
    private SemesterService semesterService;

    @Mock
    private SemesterSubjectStudentService semesterSubjectStudentService;

    @Mock
    private SemesterDao semesterDao;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new SubjectController(this.subjectServiceMock, this.semesterSubjectServiceMock,
                        this.personService, this.semesterService, this.semesterSubjectStudentService, this.semesterDao))
                .build();
    }

    @Test
    public void list_forTeacher_ok() throws Exception {
        Person person = Generator.generatePerson();
        person.setRole(Role.TEACHER);
        School school = new School();
        person.setSchool(school);
        Subject subject = Generator.generateSubject(school, null);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Subject> list = new ArrayList<>();
        list.add(subject);

        Mockito.when(personService.getCurrentPerson()).thenReturn(person);
        Mockito.when(subjectServiceMock.listSubjects(person.getId())).thenReturn(list);

        MvcResult mvcResult = this.mockMvc.perform(get("/v1/subjects")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        ListTeacherSubjectDto listSubjectDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString()
                .substring(1, mvcResult.getResponse().getContentAsString().length() - 1), ListTeacherSubjectDto.class);
        assertNotNull(listSubjectDto);
        assertEquals(subject.getName(), listSubjectDto.getName());
    }

    @Test
    public void list_forUser_expectListOfSemesterSubjects() throws Exception {
        final Person p = Generator.generatePerson();
        p.setRole(Role.USER);
        final School school = new School();
        p.setSchool(school);
        final Semester semester = Generator.generateSemester(school, 0, null);
        final Subject subject = Generator.generateSubject(school, null);
        final SemesterSubject semesterSubject = SemesterSubject.from(semester, subject);
        final SemesterSubjectStudent semesterSubjectStudent = SemesterSubjectStudent.from(semesterSubject, p);

        when(personService.getCurrentPerson()).thenReturn(p);
        when(semesterSubjectServiceMock.getPersonSemesterSubjects(p.getSchool().getId(), p.getId()))
                .thenReturn(Collections.singletonList(semesterSubjectStudent));

        this.mockMvc.perform(get("/v1/subjects")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code", is(subject.getCode())));
    }

}
