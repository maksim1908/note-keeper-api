package org.example.NoteKeeperApi.Config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.NoteKeeperApi.Service.JwtService.JwtSecurityService;
import org.example.NoteKeeperApi.Service.UserService.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String AUTH_HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    private final JwtSecurityService jwtSecurityService;
    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(AUTH_HEADER);

        if (StringUtils.isEmpty(header)
            || !org.apache.commons.lang3.StringUtils.startsWith(header, PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = header.substring(7);
        String username = jwtSecurityService.extractUsername(jwt);

        if (StringUtils.isNotEmpty(username)
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService
                    .getDetailsService()
                    .loadUserByUsername(username);

            if (jwtSecurityService.validateToken(jwt, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(AUTH_HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }
}
