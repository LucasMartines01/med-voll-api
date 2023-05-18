package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.dto.medico.DadosCadastroMedico;
import med.voll.api.domain.dto.medico.ListagemMedico;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
       var medico = new Medico(dados);
       repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new ListagemMedico(medico));
    }
    @GetMapping
    public ResponseEntity<Page<ListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
       var page = repository.findAllByAtivoTrue(pageable).map(ListagemMedico::new);
       return ResponseEntity.ok(page);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(id);
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new ListagemMedico(medico));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativarMedico(@PathVariable(value = "id") Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable(value = "id") Long id){
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new ListagemMedico(medico));
    }


}
