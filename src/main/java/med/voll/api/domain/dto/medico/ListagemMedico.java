package med.voll.api.domain.dto.medico;

import med.voll.api.domain.entities.Medico;

public record ListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {
    public ListagemMedico (Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
