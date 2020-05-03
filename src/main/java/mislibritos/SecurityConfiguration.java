package mislibritos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;
	 @Bean
	    public PasswordEncoder encoder() {
	        return (PasswordEncoder) new BCryptPasswordEncoder();
	    }
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		 // Public pages
		 http.authorizeRequests().antMatchers("/home").permitAll();
		 http.authorizeRequests().antMatchers("/cache").permitAll();
		 http.authorizeRequests().antMatchers("/mystyle.css").permitAll();
		 http.authorizeRequests().antMatchers("/*.jpeg").permitAll();		 
		 http.authorizeRequests().antMatchers("/login").permitAll();
		 http.authorizeRequests().antMatchers("/books/*").permitAll();
		 http.authorizeRequests().antMatchers("/usuario/*").permitAll();
		 http.authorizeRequests().antMatchers("/busqueda").permitAll();
		 http.authorizeRequests().antMatchers("/crearusuario").permitAll();
		 http.authorizeRequests().antMatchers("/addBook?").hasAnyRole("ROLE_AUTHOR", "ROLE_PUBLISHER", "ROLE_ADMIN");
		 http.authorizeRequests().antMatchers("/logout").permitAll();
		 // Private pages (all other pages)
		 http.authorizeRequests().anyRequest().authenticated();
		 
		 // Login form
		 http.formLogin().loginPage("/login");
		 http.formLogin().usernameParameter("username");
		 http.formLogin().passwordParameter("password");
		 http.formLogin().defaultSuccessUrl("/home");
		 http.formLogin().failureUrl("/login");
		 // Logout
		 http.logout().logoutUrl("/logout");
		 http.logout().logoutSuccessUrl("/home");

		 // Disable CSRF at the moment
		// http.csrf().
	 }
 
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		 
		 auth.authenticationProvider(authenticationProvider);

		 
	 }
	 

	
	 
}
