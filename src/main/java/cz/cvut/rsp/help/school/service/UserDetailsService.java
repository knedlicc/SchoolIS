package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.dao.PersonDao;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private PersonDao userDao;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = userDao.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(Person.class, "email", email));
        return User
            .withUsername(person.getUsername())
            .password(person.getPassword())
            .roles(person.getRole().getName().replace("ROLE_", ""))
            .build();
    }

}
