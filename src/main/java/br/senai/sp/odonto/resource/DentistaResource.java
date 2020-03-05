package br.senai.sp.odonto.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.odonto.dto.DentistaDTO;
import br.senai.sp.odonto.model.Dentista;
import br.senai.sp.odonto.repository.DentistaRepository;

//@RestController -> Anotação que informa ao Java que essa classe funcionará como o Controller e oferecá ao usuário seus recursos
//@RequestMapping -> Anotação que mapeia o endereço na URL que chamará essa classe
//@CrossOrigin -> Anotação que permite o acesso da classe por várias vias
@RestController
@RequestMapping("/odonto")
@CrossOrigin
public class DentistaResource {
	
	//@Autowired -> Anotação que faz a instância de um objeto caso não tenha sido feita ou recupera a mesma
	@Autowired
	private DentistaRepository dentistaRepository;
	
	//Método para listar todos os dentistas
	//@GetMapping -> Anotação que define o que será executado quando a requisição for GET, também podem ser discriminados diferentes métodos para diferentes caminhos
	@GetMapping("/dentistas")
	@Secured({"ROLE_DENTISTA","ROLE_PACIENTE"})
	public List<Dentista> getDentistas(String nome){	
		return dentistaRepository.findAll();
	}
	
	@GetMapping("/dentistas/nome/{nome}")
	public List<Dentista> getDentistasByName(@PathVariable String nome){
		return dentistaRepository.findByNome(nome);
	}
	
	@GetMapping("/dentistas/cro/{cro}")
	@Secured({"ROLE_DENTISTA","ROLE_PACIENTE"})
	public List<Dentista> getDentistasByCro(@PathVariable String cro){
		return dentistaRepository.findByCro(cro);
	}
	
	@GetMapping("/dentistas/search/{nome}")
	public List<Dentista> getDentistaLikeNome(@PathVariable String nome){
		return dentistaRepository.findByLikeNome(nome);
	}
	
	//Descobrir como selecionar dentistas a partir de uma Especialidade pesquisada
	//ALTERNATIVA 1 -> Montar método findByEspecialidades - ERRO: Parameter value [Implantodontia] did not match expected type
	//ALTERNATIVA 2 -> Montar query customizada com INNER JOIN - Funcionou, utilizando JOIN do HQL
	@GetMapping("/dentistas/especialidades/{especialidade}")
	public List<Dentista> getDentistasByEspecialidade(@PathVariable String especialidade){
		return dentistaRepository.findDentistasByEspec(especialidade);
	}
	
	//Método para retornar um dentista específico
	//O nome da variável utilizada na URI deve ser idêntico ao usado no método
	//O método findById() retorna um Optional, para o caso que não encontrar o objeto 
	//O método isPresent() verifica se existe um dentista no retorno do findById
	//se sim, utiliza o método get() para capturar um Optional, no caso o dentista específico
	//senão responde que não foi encontrado
	//A resposta final é definida por uma ResponseEntity
	@GetMapping("/dentistas/{codigo}")
	public ResponseEntity<?> getDentista(@PathVariable Long codigo){
		Optional dentistaConsultado = dentistaRepository.findById(codigo);
		Dentista dentista = (Dentista) dentistaConsultado.get();
		
		DentistaDTO dentistaDTO = new DentistaDTO();
		
		dentistaDTO.setEmail(dentista.getEmail());
		dentistaDTO.setNome(dentista.getNome());
		dentistaDTO.setTelefone(dentista.getTelefone());
		
		return dentistaConsultado.isPresent() ? ResponseEntity.ok(dentistaConsultado.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/pacientes")
	public String teste(){
		return "Esses são seus pacientes ^^";
	}
	
	//@PostMapping -> Anotação que identifica um post no mapping
	//@RequestBody -> Indica o que relacionar como corpo de informações, no caso ele relaciona com um objeto dentista, que já contém todos os atributos necessários
	//@ResponseStatus(HttpStatus.CREATED) -> Anotação que indica que, caso haja sucesso, ao invés de retornar 200, retornará 201, indicando que houve uma criação
	//@Valid -> checa se ele está validado com os parâmetros definidos na classe, caso contrário retorna uma bad request
	//@Secured -> Define o nome do "tipo de perfil" que poderá ter acesso a esse tipo de requisição. O padrão do protocolo é usar ROLE_nomeDinamico
	@PostMapping("/dentistas")
	@Secured("ROLE_ADMIN")
	@ResponseStatus(HttpStatus.CREATED)
	public Dentista gravar(@Valid @RequestBody Dentista dentista) {
		Dentista novoDentista = dentistaRepository.save(dentista);
		return novoDentista;
	}
	
	//Deletar um determinado dentista
	@DeleteMapping("/dentistas/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		dentistaRepository.deleteById(codigo);
	}
	
	//Atualizar dentista
	@PutMapping("/dentistas/{codigo}")
	public ResponseEntity<Dentista> atualizar(@PathVariable Long codigo, @Valid @RequestBody Dentista dentista){
		
		//Pesquisando se existe um dentista com esse código, e se existir, insere ele na variável dentistaBanco  
		Dentista dentistaBanco = dentistaRepository.findById(codigo).get();
		
		//Confere as mudanças que houveram entre o dentista do banco e o dentista enviado no body
		//e copia as propriedades do dentista enviado no body para o que foi buscado no banco
		//corrigindo as diferenças (atualizações
		//com exceção do código e do CRO
		//Isso evita que dados errados sejam enviados no body, como um código diferente da URI, gerando um novo objeto
		BeanUtils.copyProperties(dentista, dentistaBanco, "codigo", "cro");
		
		Dentista dentistaAtualizado = dentistaRepository.save(dentistaBanco);
		
		return ResponseEntity.ok(dentistaAtualizado);
		
	}
}
