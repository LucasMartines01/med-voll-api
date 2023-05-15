package med.voll.api.domain.dto;

import med.voll.api.domain.models.Medico;

public record ListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {
    public ListagemMedico (Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
