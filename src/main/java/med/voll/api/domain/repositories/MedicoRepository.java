package med.voll.api.domain.repositories;

import med.voll.api.domain.dto.medico.Especialidade;
import med.voll.api.domain.entities.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    @Query("""
           select m from Medico m where m.ativo = 1 and m.especialidade = :especialidade
           and m.id not in(
           select c.medico.id from Consulta c where c.data = :data
           )
           order by rand() limit 1
            """)
    Medico escolherMedicoPorEspecialidadeDisponibilidade(Especialidade especialidade, LocalDateTime data);
}
