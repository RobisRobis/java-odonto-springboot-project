package br.senai.sp.odonto.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration -> Anotação que classifica essa classe como componente de configuração
//@EnableWebSecurity -> Anotação que ativa a segurança pela web / HTTP
//WebSecurityConfigurerAdapter -> Classe abstrata que implementa questões de segurança para a aplicação
//@EnableGlobalMethodSecurity -> Habilitando segurança em toda a aplicação, alertando que está ativada
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//Método utilizado para a configuração da segurança na requisição HTTP
	//Reescrevendo o método original, retirando o formulário para autenticação que aparece no HTTP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and().httpBasic()
			.and().csrf().disable();
		//CSRF -> cross-site request forgery, desabilitado
	}
	
	//Método utilizado para a configuração da autenticação
	//Definindo a autenticação na memória por inMemoryAuthentication()
	//Definindo usuário e senha por .withUser() e .password()
	//Definindo nome de cargo por .roles(), só pode ter o nome da role, para que consiga conversar com o padrão "ROLE_funcao/cargo" lido na anotação do Resources
	//No caso dois usuários estão sendo definidos com suas funções
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		auth
			.inMemoryAuthentication()
			.passwordEncoder(encoder)
			.withUser("edu")
			.password(encoder.encode("123"))
			.roles("USER")
			.and()
			.withUser("admin")
			.password(encoder.encode("admin"))
			.roles("ADMIN")
			.and()
			.withUser("dentista")
			.password(encoder.encode("dentista"))
			.roles("DENTISTA")
			.and()
			.withUser("paciente")
			.password(encoder.encode("paciente"))
			.roles("PACIENTE");
	}

}
