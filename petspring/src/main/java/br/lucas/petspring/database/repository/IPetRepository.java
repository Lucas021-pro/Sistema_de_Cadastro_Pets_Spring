package br.lucas.petspring.database.repository;

import br.lucas.petspring.database.model.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPetRepository extends JpaRepository<PetEntity, Integer> {

    List<PetEntity> findByDonoDonoId(Integer donoId);
}
