package cz.cvut.rsp.help.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cz.cvut.rsp.help.school.model.subject.SubjectTutor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NamedQueries({
    @NamedQuery(name = "Person.findByEmail", query = "SELECT u FROM Person u WHERE u.email = :email"),
    @NamedQuery(name = "Person.findSubjects",query = "SELECT u.subject FROM SubjectTutor u WHERE  u.tutor.id= :tutor"),
    @NamedQuery(name = "Person.findByRole",query = "SELECT u FROM Person u WHERE u.role = :role"),
    @NamedQuery(name = "Person.findByKeyword",query = "SELECT u FROM Person u WHERE LOWER(CONCAT(u.firstName, u.lastName, u.email)) LIKE LOWER(:keyword)"),
})
public class Person extends AbstractEntity {

    @Basic(optional = false)
    private String firstName;

    @Basic(optional = false)
    private String lastName;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String email;

    private Date birthDate;

    private String phoneNumber;

    @JsonIgnore
    @Basic(optional = false)
    private String password;

    @Transient
    @JsonIgnore
    private String rawPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "person_id")
    private Set<SubjectTutor> SubjectTutor;

    public Person() {
        this.role = Role.USER;
        this.rawPassword = null;
    }


    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Person setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Person setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Person setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Person setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public Person setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public Person setRole(Role role) {
        this.role = role;
        return this;
    }

    public School getSchool() {
        return school;
    }

    public Person setSchool(School school) {
        this.school = school;
        return this;
    }

    public Set<SubjectTutor> getSubjectTutor() {
      return SubjectTutor;
    }

    public void setSubjectTutor(Set<SubjectTutor> subjectTutor) {
      SubjectTutor = subjectTutor;
    }

    @PrePersist
    private void prePersist() {
        this.setRawPassword(null);
    }

}
