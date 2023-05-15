package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.CadastroPacienteDto;
import med.voll.api.domain.dto.DadosAtualizacaoPaciente;
import med.voll.api.domain.dto.ListagemPacienteDto;
import med.voll.api.domain.models.Paciente;
import med.voll.api.domain.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid CadastroPacienteDto dados){
        repository.save(new Paciente(dados));
    }
    @GetMapping
    public Page<ListagemPacienteDto> listarPacientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        return repository.findAllByAtivoTrue(pageable).map(ListagemPacienteDto::new);
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
