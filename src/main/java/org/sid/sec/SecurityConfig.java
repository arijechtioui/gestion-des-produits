package org.sid.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private DataSource dataSource;
  @Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  BCryptPasswordEncoder bcpe = getBCPE();
	  //System.out.println(bcpe.encode("1234"));
		/*
		 * auth.inMemoryAuthentication().withUser("admin")
		 * .password(bcpe.encode("1234")).roles("ADMIN","USER");
		 * auth.inMemoryAuthentication().withUser("user")
		 * .password(bcpe.encode("1234")).roles("USER");
		 * auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder());
		 */
	  
	auth.jdbcAuthentication().dataSource(dataSource).
	usersByUsernameQuery("select username as principal, password as credentials , active from users where username=?").
	authoritiesByUsernameQuery("select username as principal,role as role from users_role where username =?").
	rolePrefix("ROLE_")
	.passwordEncoder(bcpe);
	  
	  
	  
	  
}
	@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().loginPage("/login");
			http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
			http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
			http.exceptionHandling().accessDeniedPage("/409");
			}
	@Bean
	 BCryptPasswordEncoder getBCPE() {
		 return new BCryptPasswordEncoder();
	 }
}
