package cz.cvut.rsp.help.school.api.v1;

import cz.cvut.rsp.help.school.dto.SemesterSubjectDto;
import cz.cvut.rsp.help.school.dto.semester.SemesterSubjectStudentDto;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.semester.Semester;
import cz.cvut.rsp.help.school.service.PersonService;
import cz.cvut.rsp.help.school.service.SemesterService;
import cz.cvut.rsp.help.school.service.SemesterSubjectStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/semester_subject_student")
public class SemesterSubjectStudentController {

    private final SemesterSubjectStudentService service;
    private final SemesterService semesterService;
    private final PersonService personService;

    @Autowired
    public SemesterSubjectStudentController(SemesterSubjectStudentService service, SemesterService semesterService,
            PersonService personService) {
        this.service = service;
        this.semesterService = semesterService;
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMarkToStudent(@RequestBody SemesterSubjectStudentDto semesterSubjectStudentDto) {
        service.addMarkToStudent(semesterSubjectStudentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public SemesterSubjectDto[][] getSchedule() {
        Person person = personService.getCurrentPerson();
        Semester semester = semesterService.findLastSemesterInSchool(person.getSchool());
        return service.getSchedule(semester, person);
    }
}
