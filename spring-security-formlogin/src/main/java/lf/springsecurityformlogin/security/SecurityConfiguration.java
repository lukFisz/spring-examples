package lf.springsecurityformlogin.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .failureForwardUrl("/login")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/details").authenticated()
                    .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/admin").hasRole("ADMIN");

        /** uncomment below in order to enable h2 web console */
//        http
//                .csrf().disable()
//                .headers().frameOptions().sameOrigin()
//                .and()
//                .authorizeRequests().antMatchers("/h2/**").permitAll();
    }

}
