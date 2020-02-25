package mislibritos;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 @Override
	 protected void configure(HttpSecurity http) throws Exception {

		 // Public pages
		 http.authorizeRequests().antMatchers("/home").permitAll();
		 http.authorizeRequests().antMatchers("/mystyle.css").permitAll();
		 http.authorizeRequests().antMatchers("/login").permitAll();
		 http.authorizeRequests().antMatchers("/books").permitAll();
		 http.authorizeRequests().antMatchers("/usuario").permitAll();
		 http.authorizeRequests().antMatchers("/loginerror").permitAll();
		 //http.authorizeRequests().antMatchers("/logout").permitAll();
		 // Private pages (all other pages)
		 http.authorizeRequests().anyRequest().authenticated();
		 // Login form
		 http.formLogin().loginPage("/login");
		 http.formLogin().usernameParameter("username");
		 http.formLogin().passwordParameter("password");
		 http.formLogin().defaultSuccessUrl("/home");
		 http.formLogin().failureUrl("/loginerror");
		 // Logout
		 //http.logout().logoutUrl("/logout");
		 //http.logout().logoutSuccessUrl("/");

		 // Disable CSRF at the moment
		 http.csrf().disable();
	 }
 
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		 // User
		 auth.inMemoryAuthentication()
		 .withUser("user").password("pass").roles("USER");
	 }
	 
	 
	 
}
