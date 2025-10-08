package com.dp.vstore_orderservice.security;

import com.dp.vstore_orderservice.security.JwtHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;

    public JwtFilter(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        logger.info(">>> JWTFilter invoked for: " + request.getMethod() + " " + request.getRequestURI());
        Enumeration<String> headers = request.getHeaderNames();
        while (headers != null && headers.hasMoreElements()) {
            String name = headers.nextElement();
            logger.info("Header: " + name + " = " + request.getHeader(name));
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("No Bearer token present, continue chain");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        logger.info("Token (first 50 chars): " + (token.length() > 50 ? token.substring(0,50) + "..." : token));

        try {
            boolean valid = jwtHelper.validateToken(token);
            logger.info("jwtHelper.validateToken -> " + valid);
            if (!valid) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtHelper.extractClaims(token);
            logger.info("Claims subject=" + claims.getSubject() + ", claims=" + claims);


            List<String> roleStrings = new ArrayList<>();
            Object rolesObj = claims.get("roles");
            if (rolesObj instanceof List) {
                for (Object o : (List<?>) rolesObj) if (o != null) roleStrings.add(o.toString());
            } else if (rolesObj instanceof String) {
                String s = (String) rolesObj;
                if (!s.isBlank()) roleStrings.addAll(Arrays.stream(s.split(","))
                        .map(String::trim).filter(x->!x.isEmpty()).collect(Collectors.toList()));
            }
            logger.info("Parsed roles: " + roleStrings);

            List<SimpleGrantedAuthority> authorities = roleStrings.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UserPrincipal userPrincipal = new UserPrincipal(claims.get("userid").toString(), claims.getSubject());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userPrincipal, authHeader, authorities);

            authentication.setDetails(claims);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Authentication set: principal=" + authentication.getPrincipal() + ", auths=" + authentication.getAuthorities());
        } catch (Exception ex) {
            logger.error("Exception in JWTFilter:", ex);
            // do NOT short-circuit — continue chain so error handling occurs downstream
        }

        filterChain.doFilter(request, response);
    }

}
