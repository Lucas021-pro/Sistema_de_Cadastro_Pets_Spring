package br.lucas.petspring.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DonoUpdateDto {

    @NotBlank(message = "O nome é obrigatório.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome não pode conter caracteres especiais ou números.")
    private String nome;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    private String cidade;
    private String rua;
    private String numero;
}
