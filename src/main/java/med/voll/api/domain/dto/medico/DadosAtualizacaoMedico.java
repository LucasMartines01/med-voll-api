package med.voll.api.domain.dto.medico;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(String nome, String telefone, @Valid DadosEndereco endereco) {
}
