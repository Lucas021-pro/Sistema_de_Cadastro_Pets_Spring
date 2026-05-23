package br.lucas.petspring.dto;

import br.lucas.petspring.enums.SexoPet;
import br.lucas.petspring.enums.TipoPet;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetResponseDTO {

    private Integer petId;
    private String protocolo;
    private String nome;
    private String sobrenome;
    private TipoPet tipo;
    private SexoPet sexo;
    private String raca;
    private Double peso;
    private Double idade;
    private String cidade;
    private String rua;
    private String numero;
}
