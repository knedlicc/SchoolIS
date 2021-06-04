package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.dao.SemesterDao;
import cz.cvut.rsp.help.school.dao.SubjectDao;
import cz.cvut.rsp.help.school.dao.SubjectExamDao;
import cz.cvut.rsp.help.school.dao.SubjectTutorDao;
import cz.cvut.rsp.help.school.dto.exam.NewExamDto;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SubjectExam;
import cz.cvut.rsp.help.school.model.subject.Subject;
import cz.cvut.rsp.help.school.model.subject.SubjectTutor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SubjectServiceTest {

    @Mock
    private SubjectDao subjectDaoMock;

    @Mock
    private SubjectTutorDao subjectTutorDaoMock;

    @Mock
    private SubjectExamDao subjectExamDao;

    @Mock
    private SemesterDao semesterDao;

    @Mock
    private PersonService personService;

    private SubjectService sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.sut = new SubjectService(subjectDaoMock,subjectTutorDaoMock, subjectExamDao, semesterDao, personService);
    }

    @Test
    public void createExam_ok(){
        School s = Generator.generateSchool(1,1);
        Semester semester = s.getSemesters().iterator().next();

        Subject subject = s.getSubjects().iterator().next();
        sut.persist(subject);
        SemesterSubject semesterSubject = semester.getSubjects().iterator().next();
        SubjectExam subjectExam = new SubjectExam();
        subjectExam.setNote("test");
        subjectExam.setCapacity(10);
        subjectExam.setLocalDateTime(LocalDateTime.now());
        NewExamDto newExamDto = NewExamDto.from(subjectExam);

        sut.createExam(subject.getId(), newExamDto);

        Assert.assertEquals(semesterSubject.getExams().iterator().next().getNote(), "test");
        //semesterSubject.addExam();

    }

    @Test
    public void persistTest_ok() {
        final Subject subject = new Subject();
        final SubjectTutor subjectTutor = new SubjectTutor();
        final Person person = Generator.generatePerson();
        person.setRole(Role.TEACHER);
        subjectTutor.setTutor(person);
        subject.getTutors().add(subjectTutor);

        sut.persist(subject);

        final ArgumentCaptor<Subject> captor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectDaoMock).persist(captor.capture());
        assertEquals(captor.getValue().getId(), subject.getId());
        assertTrue(captor.getValue().getTutors().contains(subjectTutor));
    }

    @Test
    public void listSubjects_ok() {
        final Subject subject = new Subject();
        final Person person = Generator.generatePerson();
        person.setRole(Role.TEACHER);
        person.setId(234234L);
        final SubjectTutor subjectTutor = new SubjectTutor();
        subjectTutor.setSubject(subject);
        subjectTutor.setTutor(person);
        subject.getTutors().add(subjectTutor);
        List<Subject> expected = new ArrayList<>();
        expected.add(subject);

        when(subjectTutorDaoMock.find(any())).thenReturn(java.util.Optional.of(subjectTutor));
        when(subjectDaoMock.findSubjects(person.getId())).thenReturn(expected);

        sut.persist(subject);
        List<Subject> list = sut.listSubjects(subjectTutor.getTutor().getId());

        final ArgumentCaptor<Subject> captor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectDaoMock, times(1)).persist(captor.capture());
        verify(subjectDaoMock).findSubjects(person.getId());

        assertEquals(list.size(), 1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void find_subjectDoesNotExist_throwsAnEntityNotFoundException() {
        when(subjectDaoMock.find(anyLong())).thenReturn(Optional.empty());
        this.sut.find(1L);
    }
}
