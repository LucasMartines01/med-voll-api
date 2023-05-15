package med.voll.api.domain.dto;

import med.voll.api.domain.models.Paciente;

public record ListagemPacienteDto(String nome, String email, String cpf) {
    public ListagemPacienteDto(Paciente paciente){
        this(paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
