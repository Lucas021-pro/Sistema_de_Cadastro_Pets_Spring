package br.lucas.petspring.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonoResponseDTO {

    private Integer donoId;
    private String cpf;
    private String nome;
    private String telefone;
    private String cidade;
    private String rua;
    private String numero;
}
