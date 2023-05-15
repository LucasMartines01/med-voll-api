package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.CadastroPacienteDto;
import med.voll.api.domain.dto.DadosAtualizacaoPaciente;
import med.voll.api.domain.dto.ListagemMedico;
import med.voll.api.domain.dto.ListagemPacienteDto;
import med.voll.api.domain.models.Paciente;
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
    public void atualizarPaciente(@PathVariable(value = "id") Long id,@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = repository.getById(id);
        paciente.atualizarInformacoes(dados);

    }

    @DeleteMapping("{id}")
    @Transactional
    public void desativarPaciente(@PathVariable(value = "id") Long id){
        var paciente = repository.getById(id);
        paciente.excluir();
    }
}
