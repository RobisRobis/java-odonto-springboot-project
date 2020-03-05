package br.senai.sp.odonto.repository;

import org.springframework.data.repository.CrudRepository;

import br.senai.sp.odonto.model.User;

//Dessa vez criando repository extendendo CrudRepository
//A diferença do JPA para o CrudRepository é que o JPA contém vários métodos do Hibernate
//enquanto o CrudRepository possui o básico de CRUD
public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
	
}
