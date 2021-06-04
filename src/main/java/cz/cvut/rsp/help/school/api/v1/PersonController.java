package cz.cvut.rsp.help.school.api.v1;

import cz.cvut.rsp.help.school.dto.JwtTokenDto;
import cz.cvut.rsp.help.school.dto.Person.*;
import cz.cvut.rsp.help.school.model.Person;
import cz.cvut.rsp.help.school.model.Role;
import cz.cvut.rsp.help.school.service.JWTAuthService;
import cz.cvut.rsp.help.school.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class PersonController {

    private final PersonService personService;

    private final JWTAuthService jwtAuthService;

    @Autowired
    public PersonController(PersonService personService, JWTAuthService jwtAuthService) {
        this.personService = personService;
        this.jwtAuthService = jwtAuthService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody AuthDto authDto) {
        personService.persist(authDto.asPerson());

        String jwtToken = jwtAuthService.auth(authDto);

//        HttpCookie cookie = ResponseCookie.from(JwtCookieInterceptor.tokenCookieName, jwtToken)
//            .path("/")
//            .httpOnly(true)
//            .sameSite("Lax")
//            .build();

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                // .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new JwtTokenDto(jwtToken));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_TEACHER','ROLE_EMPLOYEE','ROLE_ADMIN')")
    @GetMapping(value = "/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listTeachers() {
        return ResponseEntity
                .ok(personService.findAllTeachers().stream().map(ListTeacherDto::from).collect(Collectors.toList()));
    }


    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_TEACHER','ROLE_EMPLOYEE','ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userData(){
        return ResponseEntity.ok(PersonDto.from(this.personService.getCurrentPerson()));
    }

    // @GetMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<?> getUserById(@PathVariable Long id){
    //     return ResponseEntity.ok(PersonDto.from(this.personService.getUserById(id)));
    // }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping(value = "/{email}/" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(PersonMngDto.from(this.personService.findByEmailOrThrow(email)));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_TEACHER','ROLE_EMPLOYEE','ROLE_ADMIN')")
    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@Valid @RequestBody AddUserDto addUserDto) {
        personService.persist(addUserDto.asPerson());
        return ResponseEntity.ok("User with given email: "+ addUserDto.getEmail()+" was added");
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@Valid @RequestBody AddUserDto personDto) {
        Person person = personService.findByEmailOrThrow(personDto.getEmail());
        person.setFirstName(personDto.getName());
        person.setLastName(personDto.getSurname());
        person.setRole(Role.valueOf(personDto.getRole()));
        if(personDto.getPassword() != null){
            person.setRawPassword(personDto.getPassword());
        }

        return ResponseEntity.ok(AddUserDto.from(personService.update(person)));
    }

    @DeleteMapping(value = "/{email}/" , produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> deleteByEmail(@PathVariable String email){
        this.personService.deleteByEmail(email);
        return ResponseEntity.ok("Deleted user with email: " + email);
    }

    @GetMapping(value = "/search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUsersByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok(personService.findUsersByKeyword("%" + keyword + "%").stream().map(ListUsersDto::from)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listStudents() {
        return ResponseEntity.ok(this.personService.findByRole(Role.USER).stream().map(ListStudentsDto::from)
                .collect(Collectors.toList()));
    }
}
