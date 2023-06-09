package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.paciente.CadastroPacienteDto;
import med.voll.api.domain.dto.paciente.DadosAtualizacaoPaciente;
import med.voll.api.domain.dto.paciente.ListagemPacienteDto;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroPacienteDto dados, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dados);
        repository.save(paciente);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new ListagemPacienteDto(paciente));
    }
    @GetMapping
    public ResponseEntity<Page<ListagemPacienteDto>> listarPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        var page = repository.findAllByAtivoTrue(pageable).map(ListagemPacienteDto::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity atualizarPaciente(@PathVariable(value = "id") Long id,@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = repository.getById(id);
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new ListagemPacienteDto(paciente));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity desativarPaciente(@PathVariable(value = "id") Long id){
        var paciente = repository.getById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }
    @GetMapping("{id}")
    @Transactional
    public ResponseEntity detalharPaciente(@PathVariable(value = "id") Long id){
        var paciente = repository.getById(id);

        return ResponseEntity.ok(new ListagemPacienteDto(paciente));
    }
}
