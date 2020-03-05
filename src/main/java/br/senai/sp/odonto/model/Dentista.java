package br.senai.sp.odonto.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ManyToAny;

//As anotações são reconhecidas pelo Spring e relacionam a classe com seus significados
//@Entity -> relaciona a classe como entidade do banco
//@Table(name="tbl_dentista") -> cria uma tabela tbl_dentista caso não existe
//A recomendação do Hibernate é que os elementos do banco sejam criados manualmente, apesar de ser possível criar por aqui 
@Entity
@Table(name = "tbl_dentista")
public class Dentista {

	//@Id -> indica que um campo é a chave primária
	//@GeneratedValue -> indica que um campo é gerado automaticamente, precisa de uma strategy pra dizer que é auto increment 
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long codigo;

	//@NotNull -> Anotação que indica que o campo não pode ser nulo
	//@Size(min=3, max=100) -> Anotação que indica um tamanho mínimo e máximo para a inserção de um campo
	@NotNull
	@Size(min=3, max=100, message="Por favor, digite um nome com no mínimo 3 caracteres e no máximo 100 caracteres.")
	private String nome;
	private String cro;
	private String telefone;
	private String email;
	
	@ManyToMany
	private List<Especialidade> especialidades;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCro() {
		return cro;
	}

	public void setCro(String cro) {
		this.cro = cro;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
	//Se houver campos de dados sensíveis como senhas, sempre verificar se eles estão sendo retornando no toString()
	//isso pode ser modificado pelo Generate
	@Override
	public String toString() {
		return "Dentista [codigo=" + codigo + ", nome=" + nome + ", cro=" + cro + ", telefone=" + telefone + ", email="
				+ email + "]";
	}
	
	

}
