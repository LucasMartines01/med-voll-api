package med.voll.api.domain.dto.consultas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.dto.medico.Especialidade;
import med.voll.api.domain.entities.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long idMedico, Long idPaciente, LocalDateTime data, Especialidade especialidade) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData(), consulta.getMedico().getEspecialidade());
    }
}
