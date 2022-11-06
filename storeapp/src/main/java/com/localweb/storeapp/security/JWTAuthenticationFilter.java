package com.localweb.storeapp.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTProvider provider;
    private MyUserDetailsService userDetailsService;

    @Autowired
    public JWTAuthenticationFilter(JWTProvider provider, MyUserDetailsService userDetailsService) {
        this.provider = provider;
        this.userDetailsService = userDetailsService;
    }

    public JWTAuthenticationFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {


            String token = getJWTFromRequest(request);
            if (StringUtils.hasText(token) && provider.validateToken(token)) {
                String email = provider.getEmailFromJWT(token);
                UserDetails details = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        details, null, details.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (ExpiredJwtException ex) {

            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
                allowForRefreshToken(ex, request);
            } else
                request.setAttribute("exception", ex);
        } catch (BadCredentialsException ex) {
            request.setAttribute("exception", ex);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        filterChain.doFilter(request, response);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        request.setAttribute("claims", ex.getClaims());

    }

    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
