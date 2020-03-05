package br.senai.sp.odonto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.senai.sp.odonto.model.Dentista;

//A criação dessa interface irá definir toda a parte de comunicação com banco de dados
//utilizando o Hibernate
public interface DentistaRepository extends JpaRepository<Dentista, Long> {
	
	//O nome no findBy.... deve ser compatível com o nome do atributo do objeto (no caso um Dentista)
	List<Dentista> findByNome(String nomeDentista);
	
	List<Dentista> findByCro(String cro);
	
	//Exemplo de SELECT comum com LIKE
	//SELECT * FROM tbl_dentista WHERE NOME LIKE '%JOSÉ%'
	
	//***JPQL/HQL
	//Exemplo de SELECT com LIKE utilizando HQL, onde o objeto é encarado como entidade
	//SELECT dentista FROM Dentista dentista WHERE dentista.nome LIKE '%JOSÉ%'
	
	//@Param -> anotação que informa qual variável representa o argumento do LIKE
	//O uso do Param é válido para deixar a consulta explícita, relacionando os dois, mas não é necessário para o funcionamento SE eles tiverem o mesmo nome
	//O argumento do método pode ter qualquer nome, mas o argumento da Query e o Param devem ter o mesmo nome para relacionar a variável
	@Query(value="SELECT d FROM Dentista d WHERE d.nome LIKE %:nomeProcurado%")
	List<Dentista> findByLikeNome(@Param("nomeProcurado") String nome);
	
	//Versão de Query na maneira "SQL" de pensar, não funcionou
	/*@Query(value="SELECT d FROM Dentista d INNER JOIN tbl_dentista_especialidades AS tde ON "
		+ "d.codigo = tde.dentista_codigo INNER JOIN Especialidade especialidade ON tde.especialidades_codigo = especialidade.codigo WHERE especialidade.nome = :nomeEspecialidade")*/
	
	//Versão de Query na maneira "HQL" de pensar, funcionou, pensando nos atributos das entidades também como entidades
	//Fonte para consulta -> https://docs.jboss.org/hibernate/core/3.3/reference/en/html/queryhql.html#queryhql-joins
	@Query(value="SELECT d FROM Dentista d INNER JOIN d.especialidades esp WHERE esp.nome = :nomeEspecialidade")
	List<Dentista> findDentistasByEspec(@Param("nomeEspecialidade") String especialidade);

}
