package curso.springboot.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementsUserDetailsServices implementsUserDetailsServices;

	@Override // configura as solicitaçoes de acesso por http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // diativa as config padroes do spring
				.authorizeRequests() // permitir restrigir acesso
				.antMatchers(HttpMethod.GET, "/").permitAll() // qualquer user pode acessar a pagina inicial
				.antMatchers(HttpMethod.GET, "/cadastroPessoa").hasAnyRole("ADMIN")
				.anyRequest().authenticated().and().formLogin().permitAll() // permitir qualquer usuario
				.and().logout() // mapea URL de Logout e invalida a cessao autenticada anterior
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}

	@Override // cria autenticaçao do usuario com banco de dados ou em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(implementsUserDetailsServices).passwordEncoder(new BCryptPasswordEncoder());

		
	}

	@Override // ignora URL especiais
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}

}

/*
 * ++ validação em memoria
 * 
 * auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
 * .withUser("luiz")
 * .password("$2a$10$E79MjwtD9uMbCYX6zYi46uEMWSjWKjkKsshpngLlOakoICY5EeX5O")
 * .roles("ADMIN");
 */
