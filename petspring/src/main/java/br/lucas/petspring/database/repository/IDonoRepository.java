package br.lucas.petspring.database.repository;

import br.lucas.petspring.database.model.DonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDonoRepository extends JpaRepository <DonoEntity, Integer> {
    Optional<DonoEntity> findByCpf(String cpf);
}
