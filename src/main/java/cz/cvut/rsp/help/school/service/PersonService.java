package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.dao.PersonDao;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import cz.cvut.rsp.help.school.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonDao dao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void persist(Person person) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(person.getRawPassword());
        person.setPassword(passwordEncoder.encode(person.getRawPassword()));
        person.setRawPassword(null);
        if (person.getRole() == null) {
            person.setRole(Constants.DEFAULT_ROLE);
        }
        dao.persist(person);
    }

    @Transactional(readOnly = true)
    public Optional<Person> find(Long id) {
        return dao.find(id);
    }


    @Transactional(readOnly = true)
    public boolean exists(String email) {
        return dao.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    public Person findByEmailOrThrow(String email) {
        return dao.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(Person.class, "email", email));
    }

    @Transactional(readOnly = true)
    public Person getCurrentPerson() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        return this.findByEmailOrThrow(username);
    }

    @Transactional(readOnly = true)
    public Person getUserById(Long id) {
        return dao.find(id).orElseThrow(() -> new EntityNotFoundException(Person.class, "id", id));
    }

    @Transactional(readOnly = true)
    public Collection<Person> findUsersByKeyword(String keyword) {
        return dao.findByKeyword(keyword).orElseThrow(() -> new EntityNotFoundException(Person.class, "keyword", keyword));
    }

    @Transactional(readOnly = true)
    public Collection<Person> findByRole(Role role) {
        return this.dao.findByRole(role);
    }

    @Transactional(readOnly = true)
    public Collection<Person> findAllTeachers() {
        return this.findByRole(Role.TEACHER);
    }

    @Transactional
    public Person update(Person person){
        return dao.update(person);
    }

    @Transactional
    public void deleteByEmail(String email){
        Person person = findByEmailOrThrow(email);
        dao.remove(person);
    }

}
