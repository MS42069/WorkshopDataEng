package com.fhwedel.softwareprojekt.v1.util;

import com.fhwedel.softwareprojekt.v1.model.User;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Utility class for handling authentication-related operations. */
@Component
public class AuthUtil {

    /** The name of the CSRF token cookie. */
    private static final String CSRF_TOKEN_COOKIE_NAME = "XSRF-TOKEN";

    /** The name of the access token cookie. */
    private static final String ACCESS_TOKEN_COOKIE_NAME = "ACCESS_TOKEN";

    /** The JWT (JSON Web Token) utility. */
    @Autowired private JwtUtil jwtUtil;

    /** The duration (in minutes) for which an access token is valid. */
    @Value("${custom.auth.access-token.duration}")
    private int accessTokenDuration;

    /**
     * Create an access token cookie for a user.
     *
     * @param user The user for whom the access token is created.
     * @return The created access token cookie.
     */
    public Cookie createAccessTokenCookie(User user) {
        var duration = Duration.ofMinutes(accessTokenDuration);
        var expiresAt = Instant.now().plus(duration);

        return createSecureCookie(
                ACCESS_TOKEN_COOKIE_NAME, jwtUtil.generateJwtToken(user, expiresAt), duration);
    }

    /**
     * Create an access token cookie with a given token.
     *
     * @param token The access token.
     * @return The created access token cookie.
     */
    public Cookie createAccessTokenCookie(String token) {
        var duration = Duration.ofMinutes(accessTokenDuration);
        var expiresAt = Instant.now().plus(duration);

        return createSecureCookie(ACCESS_TOKEN_COOKIE_NAME, token, duration);
    }

    /**
     * Remove the access token cookie.
     *
     * @return The removed access token cookie.
     */
    public Cookie removeAccessTokenCookie() {
        return createSecureCookie(ACCESS_TOKEN_COOKIE_NAME, "", Duration.ZERO);
    }

    /**
     * Remove the CSRF token cookie.
     *
     * @return The removed CSRF token cookie.
     */
    public Cookie removeCSRFCookie() {
        return createSecureCookie(CSRF_TOKEN_COOKIE_NAME, "", Duration.ZERO);
    }

    /**
     * Get the access token from an HTTP request's cookies.
     *
     * @param request The HTTP request.
     * @return The access token as a string.
     */
    public String getAccessTokenFromRequest(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(ACCESS_TOKEN_COOKIE_NAME))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("");
    }

    /**
     * Create a secure cookie with the given properties.
     *
     * @param name The name of the cookie.
     * @param payload The payload (value) of the cookie.
     * @param duration The duration for which the cookie is valid.
     * @return The created secure cookie.
     */
    private Cookie createSecureCookie(String name, String payload, Duration duration) {
        var cookie = new Cookie(name, payload);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/v1");
        cookie.setMaxAge((int) duration.toSeconds());

        return cookie;
    }
}
