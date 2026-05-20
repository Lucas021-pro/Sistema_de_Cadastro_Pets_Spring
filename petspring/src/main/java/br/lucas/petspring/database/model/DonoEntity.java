package br.lucas.petspring.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "tb_donos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DonoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer donoId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    private String telefone;

    private String rua;
    private String numero;
    private String cidade;

    @OneToMany(mappedBy = "dono")
    private List<PetEntity> pets = new ArrayList<>();

}
