package med.voll.api.domain.services;

import med.voll.api.config.ValidacaoException;
import med.voll.api.domain.dto.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.dto.consultas.DadosDetalhamentoConsulta;
import med.voll.api.domain.entities.Consulta;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.repositories.ConsultaRepository;
import med.voll.api.domain.repositories.MedicoRepository;
import med.voll.api.domain.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ConsultasService {
    @Autowired
    ConsultaRepository repository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Esse paciente não existe");
        }

        if(dados.idMedico()!= null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Esse médico não existe");
        }
        if(dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                dados.data().getHour() < 7 || dados.data().getHour() > 18){
            throw new ValidacaoException("Consulta fora do horário de funcionamento");
        }
        var diferencaMinutos = Duration.between(LocalDateTime.now(), dados.data()).toMinutes();
        if(diferencaMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }

        var medico = buscaMedico(dados);
        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível para data informada");
        }
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data());
        repository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico buscaMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando não for escolhido o médico!");
        }

        return medicoRepository.escolherMedicoPorEspecialidadeDisponibilidade(dados.especialidade(), dados.data());

    }

}
