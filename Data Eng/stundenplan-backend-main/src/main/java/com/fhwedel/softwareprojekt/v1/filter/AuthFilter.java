package com.fhwedel.softwareprojekt.v1.filter;

import com.fhwedel.softwareprojekt.v1.model.User;
import com.fhwedel.softwareprojekt.v1.service.UserService;
import com.fhwedel.softwareprojekt.v1.util.AuthUtil;
import com.fhwedel.softwareprojekt.v1.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This filter intercepts incoming requests to extract user authentication information from JWT
 * tokens and sets up the Spring Security context with the authenticated user.
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    /** Utility for handling JWT tokens. */
    @Autowired private JwtUtil jwtUtils;

    /** Utility for handling authentication. */
    @Autowired private AuthUtil authUtil;

    /** Service for managing user-related operations. */
    @Autowired private UserService userService;

    /**
     * Filters incoming requests to extract user authentication information from JWT tokens and sets
     * up the Spring Security context with the authenticated user.
     *
     * @param request The incoming HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param filterChain The filter chain to continue processing the request.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String accessToken = authUtil.getAccessTokenFromRequest(request);
            String username = jwtUtils.getUsernameFromToken(accessToken);
            String displayname = jwtUtils.getDisplaynameFromToken(accessToken);
            User user = new User(username, displayname);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            log.error("auth filter failed: {}", ex.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
