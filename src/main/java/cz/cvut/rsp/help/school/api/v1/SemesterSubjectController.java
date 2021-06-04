package cz.cvut.rsp.help.school.api.v1;

import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import cz.cvut.rsp.help.school.model.subject.Subject;
import cz.cvut.rsp.help.school.service.PersonService;
import cz.cvut.rsp.help.school.service.SemesterService;
import cz.cvut.rsp.help.school.service.SemesterSubjectService;
import cz.cvut.rsp.help.school.service.SemesterSubjectStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/semester/subjects")
public class SemesterSubjectController {

    private final SemesterSubjectService semesterSubjectService;
    private final SemesterSubjectStudentService semesterStudentStudentService;
    private final PersonService personService;
    private final SemesterService semesterService;

    @Autowired
    public SemesterSubjectController(SemesterSubjectService service,
            SemesterSubjectStudentService semesterStudentStudentService, PersonService personService,
            SemesterService semesterService) {
        this.semesterSubjectService = service;
        this.semesterStudentStudentService = semesterStudentStudentService;
        this.personService = personService;
        this.semesterService = semesterService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SemesterSubject getById(@PathVariable Long id) {
        final Optional<SemesterSubject> semesterSubject = semesterSubjectService.find(id);
        if (semesterSubject.isPresent()) {
            return semesterSubject.get();
        }
        return null;
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping(value = "/{subjectId}/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> addSemesterStudentToSemesterSubject(@PathVariable Long subjectId,
            @PathVariable Long personId) {
        final Person person = personService.find(personId)
                .orElseThrow(() -> new EntityNotFoundException(Person.class, "id", personId));
        final SemesterSubject subject = semesterSubjectService.find(subjectId)
                .orElseThrow(() -> new EntityNotFoundException(SemesterSubject.class, "id", subjectId));
        semesterSubjectService.addStudent(person, subject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @DeleteMapping(value = "/{subjectId}/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSemesterStudentFromSemesterSubject(@PathVariable Long subjectId, @PathVariable Long personId) {
        final Person person = personService.find(personId)
                .orElseThrow(() -> new EntityNotFoundException(Person.class, "id", personId));
        final SemesterSubject subject = semesterSubjectService.find(subjectId)
                .orElseThrow(() -> new EntityNotFoundException(SemesterSubject.class, "id", subjectId));
        semesterSubjectService.removeStudent(person, subject);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = "/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSemesterStudentFromSemesterSubject(@PathVariable Long subjectId) {
        final Person person = personService.getCurrentPerson();
        final Semester semester = this.semesterService.findLastSemesterInSchool(person.getSchool());
        this.semesterStudentStudentService.deleteSemesterSubjectStudent(semester.getId(), subjectId, person.getId());
    }
}
