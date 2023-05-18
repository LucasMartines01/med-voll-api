package med.voll.api.domain.repositories;

import med.voll.api.domain.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}
