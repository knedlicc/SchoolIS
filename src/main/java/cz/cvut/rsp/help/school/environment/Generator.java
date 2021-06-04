package cz.cvut.rsp.help.school.environment;

import com.github.javafaker.Faker;
import cz.cvut.rsp.help.school.config.ApiSecurityConfig;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import cz.cvut.rsp.help.school.model.subject.Subject;
import cz.cvut.rsp.help.school.model.subject.SubjectCompletion;
import cz.cvut.rsp.help.school.model.subject.SubjectTutor;
import cz.cvut.rsp.help.school.model.subject.SubjectType;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generator {

    public static final Random RANDOM = new Random();
    public static final Faker FAKER = new Faker(RANDOM);

    public static String generateEmail() {
        return FAKER.name().username() + "@ex.com";
    }

    public static Person generatePerson() {
        final Person person = new Person();
        person.setFirstName(FAKER.name().firstName());
        person.setLastName(FAKER.name().lastName());
        person.setEmail(Generator.generateEmail());
        String rawPassword = Strings.join(FAKER.lorem().words(), ' ');
        person.setRawPassword(rawPassword);
        person.setPassword(ApiSecurityConfig.passwordEncoder().encode(rawPassword));
        return person;
    }

    public static SubjectTutor generateSubjectTutor(Subject subject, Person tutor) {
        final SubjectTutor subjectTutor = new SubjectTutor();
        subjectTutor.setSubject(subject);
        subjectTutor.setTutor(tutor);
        return subjectTutor;
    }

    public static Role getRandomRole() {
        final List<Role> values = Arrays.asList(Role.values());
        return values.get(RANDOM.nextInt(values.size()));
    }

    public static SubjectType getRandomSubjectType() {
        final List<SubjectType> values = Arrays.asList(SubjectType.values());
        return values.get(RANDOM.nextInt(values.size()));
    }

    public static SubjectCompletion getRandomSubjectCompletion() {
        final List<SubjectCompletion> values = Arrays.asList(SubjectCompletion.values());
        return values.get(RANDOM.nextInt(values.size()));
    }

    public static Subject generateSubject(School school, Collection<Person> tutors) {
        final Subject subject = new Subject();
        subject.setSchool(school);
        // will generate smth like this: A0B04KF1
        subject.setCode(FAKER.regexify("[A-Z][0-9][A-Z][0-9]{2,2}[A-Z0-9]{3,3}"));
        subject.setName(String.join(" ", FAKER.lorem().words(3)));
        subject.setCredits(RANDOM.nextInt(10) + 1);
        subject.setType(getRandomSubjectType());
        subject.setCompletion(getRandomSubjectCompletion());
        Generator.addToSomeSubjects(Collections.singletonList(subject), tutors);
        return subject;
    }

    public static void addToSomeSubjects(Collection<Subject> subjects, Collection<Person> persons) {
        if (subjects != null && persons != null) {
            for (Subject subject : subjects) {
                for (Person person : persons) {
                    if (RANDOM.nextBoolean()) {
                        if (person.getRole().equals(Role.TEACHER)) {
                            subject.getTutors().add(Generator.generateSubjectTutor(subject, person));
                        }
                    }
                }
            }
        }
    }

    public static Semester generateSemester(School school, int semesterIndex, Collection<Person> persons) {
        final Semester semester = new Semester();
        semester.setYear(semesterIndex / 2);
        semester.setSemester(semesterIndex % 2);
        if (school != null) {
            semester.setSchool(school);
            for (Subject subject : school.getSubjects()) {
                final SemesterSubject semesterSubject = SemesterSubject.from(semester, subject);
                semesterSubject.setNumberOfDayInWeek(RANDOM.nextInt(5));
                semesterSubject.setPeriod(RANDOM.nextInt(10));
                semester.getSubjects().add(semesterSubject);
            }
        }
        Generator.addToSomeSemesters(Collections.singletonList(semester), persons);
        return semester;
    }

    public static void addToSomeSemesters(Collection<Semester> semesters, Collection<Person> persons) {
        if (semesters != null && persons != null) {
            for (Semester semester : semesters) {
                for (SemesterSubject semesterSubject : semester.getSubjects()) {
                    if (persons != null) {
                        for (Person person : persons) {
                            if (RANDOM.nextBoolean()) {
                                if (person.getRole().equals(Role.USER)) {
                                    semesterSubject.getStudents()
                                            .add(SemesterSubjectStudent.from(semesterSubject, person));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static School generateSchool(int nSubjects, int nSemesters) {
        return generateSchoolWithPersons(nSubjects, nSemesters, null);
    }

    public static School generateSchoolWithPersons(int nSubjects, int nSemesters, Collection<Person> persons) {
        final School school = new School();

        if (persons != null) {
            for (Person person : persons) {
                person.setSchool(school);
            }
        }

        int i = 0;
        for (; i < nSubjects; ++i) {
            school.getSubjects().add(generateSubject(school, persons));
        }
        for (i = 1; i <= nSemesters; ++i) {
            school.getSemesters().add(generateSemester(school, i, persons));
        }

        return school;
    }

    public static SemesterSubject generateSemesterSubject(Semester semester) {
        final SemesterSubject semesterSubject = new SemesterSubject();
        final School school = semester.getSchool();
        final Subject subject = generateSubject(school,null);
        semesterSubject.setSubject(subject);
        semesterSubject.setSemester(semester);
        semesterSubject.setNumberOfDayInWeek(RANDOM.nextInt(5));
        semesterSubject.setPeriod(RANDOM.nextInt(10));
        return semesterSubject;
    }

    public static SemesterSubjectStudent generateSemesterSubjectStudent (Person person, SemesterSubject subject){
        final SemesterSubjectStudent student = new SemesterSubjectStudent();
        student.setSemesterSubject(subject);
        student.setStudent(person);
        return student;
    }
    public static void addToSchool(School school, Collection<Person> persons) {
        for (Person person : persons) {
            person.setSchool(school);
        }

        Generator.addToSomeSubjects(school.getSubjects(), persons);
        Generator.addToSomeSemesters(school.getSemesters(), persons);
    }

}
