package com.sayilir.coder.springsecurityproj.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(UserRole.STUDENT.name())
//                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails eyyupUser = User.builder()
                .username("eyyup")
                .password(passwordEncoder.encode("pass"))
                // .roles("STUDENT")
                //.roles(UserRole.STUDENT.name())

                .authorities(UserRole.STUDENT.getGrantedAuthorities())
                .build();


        UserDetails semraUser = User.builder()
                .username("semra")
                .password(passwordEncoder.encode("semrapass"))
                //.roles("ADMIN")
                // .roles(UserRole.ADMIN.name())
                .authorities(UserRole.ADMIN.getGrantedAuthorities())
                .build();

        UserDetails zehraUser = User.builder()
                .username("zehra")
                .password(passwordEncoder.encode("zehrapass"))
                //.roles("ADMIN") kendi rolumuzu olusturduk asagıda yeni kodu ekledik
                //  .roles(UserRole.ADMINTRAINEE.name()) roleler authorizatonda var bunun ıcın commente alıp alt kodu ekle
                .authorities(UserRole.ADMINTRAINEE.getGrantedAuthorities())
                .build();


        return new InMemoryUserDetailsManager(
                eyyupUser,
                semraUser, zehraUser);
    }
}
