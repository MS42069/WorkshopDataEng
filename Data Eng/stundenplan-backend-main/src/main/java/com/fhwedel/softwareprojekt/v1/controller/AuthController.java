package com.fhwedel.softwareprojekt.v1.controller;

import com.fhwedel.softwareprojekt.v1.converter.UserConverter;
import com.fhwedel.softwareprojekt.v1.dto.CredentialsDTO;
import com.fhwedel.softwareprojekt.v1.dto.UserDTO;
import com.fhwedel.softwareprojekt.v1.model.User;
import com.fhwedel.softwareprojekt.v1.repository.UserRepository;
import com.fhwedel.softwareprojekt.v1.util.AuthUtil;
import java.util.List;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** Controller class for handling authentication-related endpoints. */
/** Controller class for handling authentication-related endpoints. */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private static final List<String> ROLES = List.of("wing", "winf", "inf");
    private static final String BASE_DN = "ou=people,dc=fh-wedel,dc=de";
    private static final String SECRET_KEY = "asdasdasdsadasdasdasdds";

    /** Repository for user data. */
    private final UserRepository userRepository;

    /** Utility class for authentication-related operations. */
    private final AuthUtil authUtil;

    /** Authentication manager for handling user authentication. */
    private final AuthenticationManager authenticationManager;

    /** Converter for converting between User entities and DTOs. */
    private final UserConverter userConverter;

    /**
     * Authenticates a user based on provided credentials and returns user information.
     *
     * @param credentialsDTO The user's credentials.
     * @param response The HTTP response object.
     * @return A DTO representing the authenticated user.
     */
    @PostMapping("/login")
    public UserDTO authenticate(
            @RequestBody CredentialsDTO credentialsDTO, HttpServletResponse response) {
        try {
            LdapContextSource contextSource = new LdapContextSource();
            contextSource.setUrl("ldaps://vsrv-ldap-replica.fh-wedel.de:636/");
            contextSource.afterPropertiesSet();

            LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
            ldapTemplate.afterPropertiesSet();

            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            record LdapResponse(String dn, String cn) {}

            List<LdapResponse> searchResults =
                    ldapTemplate.search(
                            BASE_DN,
                            "(uid=" + credentialsDTO.getUsername() + ")",
                            searchControls,
                            (ContextMapper<LdapResponse>)
                                    ctx ->
                                            new LdapResponse(
                                                    ((DirContextOperations) ctx)
                                                            .getNameInNamespace(),
                                                    ((DirContextOperations) ctx)
                                                            .getStringAttribute("cn")));
            LdapResponse searchResult = searchResults.stream().findFirst().orElse(null);

            if (searchResult != null) {
                contextSource.setUserDn(searchResult.dn);
                contextSource.setPassword(credentialsDTO.getPassword());
                contextSource.afterPropertiesSet();

                // Check if the binding was successful
                try {
                    DirContext ctx =
                            contextSource.getContext(searchResult.dn, credentialsDTO.getPassword());
                    ctx.close();
                } catch (Exception e) {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR, "Benutzername oder Passwort falsch.");
                }
                User user = new User(credentialsDTO.getUsername(), searchResult.cn);

                user.setPassword(credentialsDTO.getPassword());

                response.addCookie(authUtil.createAccessTokenCookie(user));
                return new UserDTO(searchResult.cn, credentialsDTO.getUsername());
            }
        } catch (Exception e) {
            // Log the error
            System.out.println(e.getMessage());
        }
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Benutzername oder Passwort falsch.");
    }

    /**
     * Authenticates a planner user based on provided credentials and returns user information.
     *
     * @param credentialsDTO The planner's credentials.
     * @param responseHttp The HTTP response object.
     * @return A DTO representing the authenticated planner user.
     */
    @PostMapping("/login-planer")
    public UserDTO authenticatePlaner(
            @RequestBody CredentialsDTO credentialsDTO, HttpServletResponse responseHttp) {
        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    credentialsDTO.getUsername(), credentialsDTO.getPassword()));

            var user = (User) authentication.getPrincipal();
            System.out.println("HIER2--->");
            System.out.println(user.toString());

            responseHttp.addCookie(authUtil.createAccessTokenCookie(user));
            return userConverter.convertEntityToDTO(user);

        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Benutzername oder Passwort falsch.");
        }
    }

    /**
     * Retrieves the CSRF token for the current session.
     *
     * @param csrfToken The CSRF token.
     * @return The CSRF token.
     */
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }

    /**
     * Retrieves the details of the currently authenticated user.
     *
     * @param authentication The authentication object.
     * @return A DTO representing the current user.
     */
    @GetMapping("/user")
    public UserDTO getCurrentUser(Authentication authentication) {
        var user = (User) authentication.getPrincipal();

        return userConverter.convertEntityToDTO(user);
    }

    /**
     * Logs the user out and removes the access token cookie.
     *
     * @param response The HTTP response object.
     */
    @DeleteMapping("/logout")
    public void logout(HttpServletResponse response) {
        response.addCookie(authUtil.removeAccessTokenCookie());
    }
}
