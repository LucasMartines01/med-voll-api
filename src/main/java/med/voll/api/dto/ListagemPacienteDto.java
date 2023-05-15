package med.voll.api.dto;

import med.voll.api.models.Paciente;

public record ListagemPacienteDto(String nome, String email, String cpf) {
    public ListagemPacienteDto(Paciente paciente){
        this(paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
