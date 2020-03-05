package br.senai.sp.odonto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.senai.sp.odonto.repository.UserRepository;

//@Service -> assinatura que informa ao String que pode ser utilizado como serviço
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//No caso, é dito o caminho completo da classe 
		//pois abaixo dela será utilizada uma classe do UserDetails que tem o mesmo nome
		//e aí precisará sanar a ambiguidade
		br.senai.sp.odonto.model.User user = userRepository.findByUsername(username);
		UserDetails userDetails = new User(username, user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()));
		
		return userDetails;
		
	}
	
}
