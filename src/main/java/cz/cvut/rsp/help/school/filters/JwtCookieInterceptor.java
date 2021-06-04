package cz.cvut.rsp.help.school.filters;

import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.exception.JwtExpiredException;
import cz.cvut.rsp.help.school.service.UserDetailsService;
import cz.cvut.rsp.help.school.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


@Component
public class JwtCookieInterceptor extends OncePerRequestFilter {

    public static final String tokenCookieName = "token";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            final Optional<Cookie> tokenCookieOptional = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(tokenCookieName)).findFirst();

            if (tokenCookieOptional.isPresent()) {
                Cookie tokenCookie = tokenCookieOptional.get();
                String token = tokenCookie.getValue();

                String email = jwtUtil.extractUsername(token);
                if (email == null) {
                    throw new JwtException("Malformed token: Subject is empty or not set");
                }

                try {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
                        UserDetails userDetails;
                        try {
                            userDetails = this.userDetailsService.loadUserByUsername(email);
                        } catch (EntityNotFoundException e) {
                            filterChain.doFilter(request, response);
                            return;
                        }

                        if (jwtUtil.validateToken(token, userDetails)) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                            usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        } else {
                            throw new JwtExpiredException();
                        }
                    }
                } catch (ExpiredJwtException e) {
                    throw new JwtExpiredException();
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
