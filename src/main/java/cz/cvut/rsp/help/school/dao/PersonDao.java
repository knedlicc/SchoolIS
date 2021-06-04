package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

import java.util.Collection;
import java.util.Optional;

@Repository
public class PersonDao extends AbstractDao<Person> {

    public PersonDao() {
        super(Person.class);
    }

    public Optional<Person> findByEmail(String email) {
        try {
            return Optional.of(em.createNamedQuery("Person.findByEmail", Person.class).setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Collection<Person> findByRole(Role role) {
        return em.createNamedQuery("Person.findByRole", Person.class).setParameter("role", role).getResultList();
    }

    public Optional<Collection<Person>> findByKeyword(String keyword) {
        try {
            return Optional.of(em.createNamedQuery("Person.findByKeyword", Person.class).setParameter("keyword", keyword)
                .getResultList());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

//    public Optional<Person> updateByEmail(String email){
//        Optional<Person> person = findByEmail(email);
//        if(person.isEmpty()){
//            return Optional.empty();
//        } else {
//            this.update(person.get());
//            return person;
//        }

}
