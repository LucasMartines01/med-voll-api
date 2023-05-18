package med.voll.api.domain.dto.paciente;

import med.voll.api.domain.dto.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(String nome, String telefone, DadosEndereco endereco) {
}
