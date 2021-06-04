package cz.cvut.rsp.help.school.api.v1;

import cz.cvut.rsp.help.school.dao.SemesterDao;
import cz.cvut.rsp.help.school.dto.Person.ListTeacherSubjectDto;
import cz.cvut.rsp.help.school.dto.Subject.GetSubjectDto;
import cz.cvut.rsp.help.school.dto.exam.NewExamDto;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.model.subject.Subject;
import cz.cvut.rsp.help.school.service.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import cz.cvut.rsp.help.school.dto.ListSubjectDto;
import cz.cvut.rsp.help.school.dto.SubjectStudentListDto;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.SemesterSubject;
import cz.cvut.rsp.help.school.model.semester.SemesterSubjectStudent;
import cz.cvut.rsp.help.school.service.PersonService;
import cz.cvut.rsp.help.school.service.SemesterService;
import cz.cvut.rsp.help.school.service.SemesterSubjectService;
import cz.cvut.rsp.help.school.service.SemesterSubjectStudentService;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    private final SemesterSubjectService semesterSubjectService;

    private final PersonService personService;

    private final SemesterDao semesterDao;

    private final SemesterService semesterService;

    private final SemesterSubjectStudentService semesterSubjectStudentService;

    @Autowired
    public SubjectController(SubjectService subjectService, SemesterSubjectService semesterSubjectService,
            PersonService personService, SemesterService semesterService,
            SemesterSubjectStudentService semesterSubjectStudentService, SemesterDao semesterDao) {
        this.subjectService = subjectService;
        this.semesterSubjectService = semesterSubjectService;
        this.personService = personService;
        this.semesterService = semesterService;
        this.semesterSubjectStudentService = semesterSubjectStudentService;
        this.semesterDao = semesterDao;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_TEACHER','ROLE_EMPLOYEE','ROLE_ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        final Person person = this.personService.getCurrentPerson();
        final Semester semester = this.semesterService.findLastSemesterInSchool(person.getSchool());
        final Long semesterSubjectId = semester.getSubjects().stream().filter(s -> s.getSubject().getId().equals(id))
                .map(s -> s.getId()).findFirst()
                .orElseThrow(() -> new EntityNotFoundException(SemesterSubject.class, "id", id));
        return ResponseEntity.ok(GetSubjectDto.from(this.subjectService.find(id)).setSemesterId(semester.getId())
                .setSemesterSubjectId(semesterSubjectId));
    }

    @GetMapping(value = "/{id}/exams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NewExamDto> getExams(@PathVariable Long id) {
        Long schoolId = personService.getCurrentPerson().getSchool().getId();
        Long semesterId = semesterDao.findLastIdInSchool(schoolId)
                .orElseThrow((() -> new EntityNotFoundException(Semester.class, "school id", schoolId)));
        Semester sem = semesterDao.find(semesterId)
                .orElseThrow((() -> new EntityNotFoundException(Semester.class, "id", semesterId)));
        SemesterSubject ss = sem.getSubjects().stream().filter((s) -> s.getSubject().getId().equals(id)).findFirst()
                .orElseThrow((() -> new EntityNotFoundException(SemesterSubject.class, "subject id", id)));
        return ss.getExams().stream().map(NewExamDto::from).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_TEACHER','ROLE_EMPLOYEE','ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list() {
        final Person person = this.personService.getCurrentPerson();

        Role personRole = person.getRole();
        switch (personRole) {
            case TEACHER:
                final List<Subject> subjects = this.subjectService.listSubjects(person.getId());

                return ResponseEntity
                        .ok(subjects.stream().map(ListTeacherSubjectDto::from).collect(Collectors.toList()));

            case USER:
                final School school = person.getSchool();
                if (school == null) {
                    throw new EntityNotFoundException(School.class, "person", person.getId());
                }

                final List<SemesterSubjectStudent> semesterSubjectStudents = this.semesterSubjectService
                        .getPersonSemesterSubjects(person.getSchool().getId(), person.getId());

                return ResponseEntity
                        .ok(semesterSubjectStudents.stream().map(ListSubjectDto::from).collect(Collectors.toList()));

            default:
                Semester semester = this.semesterService.findLastSemesterInSchool(person.getSchool());
                return ResponseEntity
                        .ok(semester.getSubjects().stream().map(ListSubjectDto::from).collect(Collectors.toList()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_TEACHER','ROLE_EMPLOYEE','ROLE_ADMIN')")
    @GetMapping(value = "/teachingList/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showTeachingSubjectsList(@PathVariable Long id) {
        final List<Subject> subjects = this.subjectService.listSubjects(id);

        return ResponseEntity.ok(subjects.stream().map(ListTeacherSubjectDto::from).collect(Collectors.toList()));
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/{id}/exams", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createExam(@PathVariable Long id, @Valid @RequestBody NewExamDto newExamDto) {
        subjectService.createExam(id, newExamDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_TEACHER','ROLE_EMPLOYEE','ROLE_ADMIN')")
    @GetMapping(value = "/semester", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listSemesterSubjects() {
        final Person person = this.personService.getCurrentPerson();

        final Semester semester = this.semesterService.findLastSemesterInSchool(person.getSchool());
        return ResponseEntity
                .ok(semester.getSubjects().stream().map(ListSubjectDto::from).collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping(value = "/{id}/enroll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enrollIntoSubject(@PathVariable Long id) {
        final Person person = this.personService.getCurrentPerson();

        final Semester semester = this.semesterService.findLastSemesterInSchool(person.getSchool());
        final SemesterSubject semesterSubject = semester.getSubjects().stream()
                .filter(s -> s.getSubject().getId() == id).findFirst()
                .orElseThrow(() -> new EntityNotFoundException(Subject.class, "id", id));

        final SemesterSubjectStudent semesterSubjectStudent = SemesterSubjectStudent.from(semesterSubject, person);

        this.semesterSubjectStudentService.persist(semesterSubjectStudent);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/{id}/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> studentsListBySubject(@PathVariable Long id) {
        Long schoolId = personService.getCurrentPerson().getSchool().getId();
        Long semesterId = semesterDao.findLastIdInSchool(schoolId)
                .orElseThrow((() -> new EntityNotFoundException(Semester.class, "school id", schoolId)));
        return ResponseEntity.ok(semesterSubjectService.getSubjectStudents(semesterId, id).stream()
                .map(SubjectStudentListDto::from).collect(Collectors.toList()));
    }

}
