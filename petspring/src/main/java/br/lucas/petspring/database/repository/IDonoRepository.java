package br.lucas.petspring.database.repository;

import br.lucas.petspring.database.model.DonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDonoRepository extends JpaRepository <DonoEntity, Integer> {

}
