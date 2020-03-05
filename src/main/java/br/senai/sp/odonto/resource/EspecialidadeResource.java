package br.senai.sp.odonto.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.odonto.model.Especialidade;
import br.senai.sp.odonto.repository.EspecialidadeRepository;

@RestController
@RequestMapping("/odonto")
@CrossOrigin
public class EspecialidadeResource {
	
	@Autowired
	private EspecialidadeRepository especialidadeRepository;
	
	@GetMapping("/especialidades")
	public List<Especialidade> getEspecialidade(){
		return especialidadeRepository.findAll();
	}

}
