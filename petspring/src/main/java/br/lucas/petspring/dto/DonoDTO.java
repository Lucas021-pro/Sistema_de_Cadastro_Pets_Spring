package br.lucas.petspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DonoDTO {

    @NotBlank
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$", message = "CPF não pode conter letras ou caracteres especiais. Insira no seguinte formato: 000.000.000-00")
    private String cpf;

    @NotBlank
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome não pode conter caracteres especiais ou números.")
    private String nome;

    @NotBlank
    private String telefone;

    private String cidade;
    private String rua;
    private String numero;
}
