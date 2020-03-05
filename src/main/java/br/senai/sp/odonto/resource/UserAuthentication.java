package br.senai.sp.odonto.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.odonto.dto.UserAccountCredential;
import br.senai.sp.odonto.repository.UserRepository;
import br.senai.sp.odonto.security.JwtAuthenticationService;

@RestController
public class UserAuthentication {
	
	//Busca os dados do usuário no banco
	@Autowired
	private UserRepository userRepository;
	
	//Faz a criação do token
	@Autowired
	private JwtAuthenticationService jwtService;
	
	//Faz a autenticação verificando se é um usuário válido
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//Método para logar
	public void signIn(@RequestBody UserAccountCredential credential) {
		
		UsernamePasswordAuthenticationToken userCredential = new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword());
		
		authenticationManager.authenticate(userCredential);
		
	}

}
