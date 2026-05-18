package br.lucas.petspring.database.model;

import br.lucas.petspring.enums.SexoPet;
import br.lucas.petspring.enums.TipoPet;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table (name = "tb_pets")
@Data
@Builder
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer petId;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String sobrenome;
    @Column(nullable = false)
    private String raca;
    @Column(nullable = false)
    private Double peso;
    @Column(nullable = false)
    private Double idade;

    @Enumerated(EnumType.STRING)
    private TipoPet tipo;

    @Enumerated(EnumType.STRING)
    private SexoPet sexo;

    private String rua;
    private String numero;
    private String cidade;
}
