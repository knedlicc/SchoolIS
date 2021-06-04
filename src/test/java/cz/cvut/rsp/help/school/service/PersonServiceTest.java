package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.config.ApiSecurityConfig;
import cz.cvut.rsp.help.school.dao.PersonDao;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * This test does not use Spring.
 * <p>
 * It showcases how services can be unit tested without being dependent on the
 * application framework or database.
 */
public class PersonServiceTest {

    private final PasswordEncoder passwordEncoder = ApiSecurityConfig.passwordEncoder();

    @Mock
    private PersonDao personDaoMock;

    private PersonService sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.sut = new PersonService(personDaoMock, passwordEncoder);
    }

    @Test
    public void persistEncodesPersonPassword() {
        final Person person = Generator.generatePerson();
        final String rawPassword = person.getRawPassword();
        sut.persist(person);

        final ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personDaoMock).persist(captor.capture());
        assertTrue(passwordEncoder.matches(rawPassword, captor.getValue().getPassword()));
    }

    @Test
    public void findAllTeachersTest_ok() {
        final Person p = Generator.generatePerson();
        p.setRole(Role.TEACHER);
        final Person p2 = Generator.generatePerson();
        p2.setRole(Role.USER);

        sut.persist(p);
        sut.persist(p2);
        final Collection<Person> list = sut.findAllTeachers();

        final ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personDaoMock, times(2)).persist(captor.capture());
        verify(personDaoMock).findByRole(Role.TEACHER);

        for (Person person : list) {
            assertEquals(Role.TEACHER, person.getRole());
        }
    }

    @Test
    public void updateTest_ok(){
        final Person p = Generator.generatePerson();
        sut.persist(p);

        p.setEmail("tester@dd.dd");

        when(sut.update(p)).thenReturn(p);
        final Person updatedP = sut.update(p);

        final ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personDaoMock, times(1)).update(captor.capture());
        verify(personDaoMock).persist(captor.capture());

        assertEquals(updatedP.getEmail(),"tester@dd.dd" );
    }

    @Test
    public void deleteByEmailTest_ok(){
        final Person p = Generator.generatePerson();
        sut.persist(p);

        when(personDaoMock.findByEmail(p.getEmail())).thenReturn(Optional.of(p));
        sut.deleteByEmail(p.getEmail());

        final ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personDaoMock).persist(captor.capture());
        verify(personDaoMock).remove(captor.capture());
        verify(personDaoMock,times(1)).findByEmail(p.getEmail());
    }
}
