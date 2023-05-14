package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DadosAtualizacaoMedico;
import med.voll.api.dto.DadosCadastroMedico;
import med.voll.api.dto.ListagemMedico;
import med.voll.api.models.Medico;
import med.voll.api.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }
    @GetMapping
    public Page<ListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
       return repository.findAll(pageable).map(ListagemMedico::new);
    }
    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(id);
        medico.atualizarInformacoes(dados);
    }
}
