package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.dto.Person.AuthDto;
import cz.cvut.rsp.help.school.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class JWTAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthService.class);

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;


    @Autowired
    public JWTAuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }


    public String auth(AuthDto authDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
        } catch (BadCredentialsException e) {
            LOGGER.debug(String.format("Invalid credentials: email: %s, password: %s", authDto.getEmail(), authDto.getPassword()));
            throw new BadCredentialsException("Email or password is incorrect");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.getEmail());
        return jwtUtil.generateToken(userDetails);
    }

}
