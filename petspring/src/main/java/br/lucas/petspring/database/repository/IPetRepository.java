package br.lucas.petspring.database.repository;

import br.lucas.petspring.database.model.PetEntity;
import br.lucas.petspring.enums.TipoPet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPetRepository extends JpaRepository<PetEntity, Integer> {

    List<PetEntity> findByDonoDonoId(Integer donoId);

    @Query("SELECT p FROM PetEntity p WHERE " +
            "(:tipo IS NULL OR p.tipo = :tipo) AND " +
            "(:raca IS NULL OR p.raca = :raca) AND " +
            "(:cidade IS NULL OR p.cidade = :cidade)")
    Page<PetEntity> buscarPetsComFiltros(@Param("tipo") TipoPet tipo,
                                         @Param("raca") String raca,
                                         @Param("cidade") String cidade,
                                         Pageable pageable);
}
