package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(String nome, String telefone, @Valid DadosEndereco endereco) {
}
