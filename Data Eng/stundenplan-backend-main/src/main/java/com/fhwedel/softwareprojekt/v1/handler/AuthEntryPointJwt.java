package com.fhwedel.softwareprojekt.v1.handler;

import com.fhwedel.softwareprojekt.v1.util.AuthUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * This class handles unauthorized access attempts by implementing the Spring Security
 * AuthenticationEntryPoint interface. It sends an unauthorized error response when authentication
 * fails.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private final AuthUtil authUtil;

    /**
     * Commences the authentication entry point logic. When unauthorized access is detected, it logs
     * an error message, removes access and CSRF cookies, and sends an unauthorized error response.
     *
     * @param request The HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param authException The AuthenticationException that occurred.
     * @throws IOException if an I/O error occurs during the response handling.
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());

        response.addCookie(authUtil.removeAccessTokenCookie());
        response.addCookie(authUtil.removeCSRFCookie());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
