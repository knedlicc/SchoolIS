package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.App;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import cz.cvut.rsp.help.school.service.SystemInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = App.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class) })
public class PersonDaoTest {

    @Autowired
    private PersonDao personDao;

    @Test
    public void findByEmail_personExists_expectedToReturnFoundPerson() {
        final Person p = Generator.generatePerson();
        personDao.persist(p);

        final Person p2 = personDao.findByEmail(p.getEmail()).orElseThrow();

        assertEquals(p, p2);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByEmail_personDoesNotExist_expectedToReturnEmptyOptional() {
        final String email = Generator.generateEmail();
        personDao.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(Person.class, "email", email));
    }

    @Test
    public void findByRole_personExists_expectedToReturnFoundPerson() {
        final Person p = Generator.generatePerson();
        final Role role = Generator.getRandomRole();
        p.setRole(role);
        personDao.persist(p);

        final Person p2 = personDao.findByRole(role).stream().findFirst().orElseThrow();

        assertEquals(p, p2);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByRole_personDoesNotExist_expectedToReturnEmptyOptional() {
        final Role role = Generator.getRandomRole();
        personDao.findByRole(role).stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException(Person.class, "role", role));
    }

}
