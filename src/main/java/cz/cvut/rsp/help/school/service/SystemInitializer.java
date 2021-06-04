package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.dao.SchoolDao;
import cz.cvut.rsp.help.school.environment.Generator;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import cz.cvut.rsp.help.school.model.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class SystemInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInitializer.class);

    private static final String ADMIN_USERNAME = "a@ex.com";
    private static final String EMPLOYEE_USERNAME = "e@ex.com";
    private static final String TEACHER_USERNAME = "t@ex.com";
    private static final String STUDENT_USERNAME = "s@ex.com";
    private static final String DEFAULT_PASSWORD = "pass";

    private final PlatformTransactionManager txManager;

    private final PersonService personService;
    private final SchoolDao schoolDao;

    @Autowired
    public SystemInitializer(PlatformTransactionManager txManager, PersonService personService, SchoolDao schoolDao) {
        this.txManager = txManager;
        this.personService = personService;
        this.schoolDao = schoolDao;
    }

    @PostConstruct
    private void initSystem() {
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.execute((status) -> {
            boolean firstStart = false;
            School school = this.schoolDao.findAll().stream().findFirst().orElse(null);
            if (school == null) {
                firstStart = true;
                school = Generator.generateSchool(10, 3);
                this.schoolDao.persist(school);
            }

            // These are test users
            generatePerson(school, Role.ADMIN, ADMIN_USERNAME);
            generatePerson(school, Role.EMPLOYEE, EMPLOYEE_USERNAME);
            generatePerson(school, Role.TEACHER, TEACHER_USERNAME);
            generatePerson(school, Role.USER, STUDENT_USERNAME);

            // These are couple more different users to fillup system
            if (firstStart) {
                int i = 0;
                for (i = 0; i < 5; ++i) {
                    generatePerson(school, Role.USER, Generator.generateEmail());
                }
                for (i = 0; i < 5; ++i) {
                    generatePerson(school, Role.TEACHER, Generator.generateEmail());
                }
            }
            return null;
        });
    }

    private void generatePerson(School school, Role role, String username) {
        if (personService.exists(username)) {
            return;
        }

        final Person p = Generator.generatePerson();
        p.setRole(role);
        Generator.addToSchool(school, Collections.singletonList(p));
        p.setEmail(username);
        p.setRawPassword(DEFAULT_PASSWORD);
        personService.persist(p);
        LOG.info(String.format("generatePerson: Role: %s; Email: %s; Pass: %s", role, username, DEFAULT_PASSWORD));
    }

}
