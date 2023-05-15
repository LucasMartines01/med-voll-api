package med.voll.api.domain.dto;

import jakarta.validation.Valid;

public record DadosAtualizacaoMedico(String nome, String telefone, @Valid DadosEndereco endereco) {
}
