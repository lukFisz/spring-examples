package luk.fisz.springsecurityjwtauthentication.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import luk.fisz.springsecurityjwtauthentication.db.User;
import luk.fisz.springsecurityjwtauthentication.db.UserRepo;
import luk.fisz.springsecurityjwtauthentication.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static luk.fisz.springsecurityjwtauthentication.security.jwt.JwtProperties.*;
import static luk.fisz.springsecurityjwtauthentication.security.jwt.JwtProperties.PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepo userRepo;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepo userRepo) {
        super(authenticationManager);
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_NAME);

        if (header == null || !header.startsWith(PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication auth = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_NAME);
        if (token != null) {
            String username =
                    JWT.require(HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(PREFIX, ""))
                    .getSubject();
            if (username != null) {
                User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                CustomUserDetails userDetails = new CustomUserDetails(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, userDetails.getAuthorities());
                return auth;
            }
        }
        return null;
    }
}
