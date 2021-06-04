package cz.cvut.rsp.help.school.api.v1;

import cz.cvut.rsp.help.school.dao.PersonDao;
import cz.cvut.rsp.help.school.dto.JwtTokenDto;
import cz.cvut.rsp.help.school.dto.WhoAmIDto;
import cz.cvut.rsp.help.school.dto.Person.AuthDto;
import cz.cvut.rsp.help.school.filters.JwtCookieInterceptor;
import cz.cvut.rsp.help.school.service.JWTAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
public class AuthController {

    private final JWTAuthService jwtAuthService;

    private final PersonDao personDao;


    @Autowired
    public AuthController(JWTAuthService jwtAuthService, PersonDao personDao) {
        this.jwtAuthService = jwtAuthService;
        this.personDao = personDao;
    }


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthDto authDto) {
        String jwtToken = jwtAuthService.auth(authDto);

//        HttpCookie cookie = ResponseCookie.from(JwtCookieInterceptor.tokenCookieName, jwtToken)
//            .path("/")
//            .httpOnly(true)
//            .sameSite("Lax")
//            .build();

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
//            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new JwtTokenDto(jwtToken));
    }


    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_TEACHER','ROLE_EMPLOYEE','ROLE_ADMIN')")
    @GetMapping(value = "/whoami", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> whoami() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(WhoAmIDto.from(personDao.findByEmail(username).orElseThrow()));
    }

}
