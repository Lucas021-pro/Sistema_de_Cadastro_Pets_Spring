package br.lucas.petspring.database.model;

import br.lucas.petspring.enums.SexoPet;
import br.lucas.petspring.enums.TipoPet;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "tb_pets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Column(nullable = false, unique = true)
    private String protocolo;

    private String rua;
    private String numero;
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private DonoEntity dono;
}
