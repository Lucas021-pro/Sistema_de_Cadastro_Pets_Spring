package br.lucas.petspring.dto;

import br.lucas.petspring.enums.SexoPet;
import br.lucas.petspring.enums.TipoPet;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PetDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    private String raca;
    private Double peso;
    private Double idade;
    private TipoPet tipo;
    private SexoPet sexo;
    private String rua;
    private String numero;
    private String cidade;
}
