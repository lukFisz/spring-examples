package luk.fisz.springsecurityjwtauthentication.security;

import luk.fisz.springsecurityjwtauthentication.db.UserRepo;
import luk.fisz.springsecurityjwtauthentication.security.jwt.JwtAuthenticationFilter;
import luk.fisz.springsecurityjwtauthentication.security.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;
    private final UserRepo userRepo;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider, UserRepo userRepo) {
        this.authenticationProvider = authenticationProvider;
        this.userRepo = userRepo;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                disable csrf and session, dont need with JWT
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepo))
                .authorizeRequests()
                    .antMatchers("/login", "/api", "/api/users").permitAll()
                    .antMatchers("/api/user").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/admin").hasRole("ADMIN");

        http.cors();
    }

}
